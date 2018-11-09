package com.unlam.developerstudentclub.silapu.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.Entity.UserData;
import com.unlam.developerstudentclub.silapu.R;
import com.unlam.developerstudentclub.silapu.Utils.UserPreference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.unlam.developerstudentclub.silapu.API.ApiGenerator.BASE_URL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_DETIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_GANTI_IDENTITAS;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_GANTI_PASSWORD;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_GANTI_PROFIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_PARCABLE;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_PENGADUAN_DETAIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_PERDATA_DETAIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.ERROR_FIELD_EMAIL_NOTVALID;
import static com.unlam.developerstudentclub.silapu.Utils.Util.ERROR_FIELD_KOSONG;
import static com.unlam.developerstudentclub.silapu.Utils.Util.ERROR_NOT_MATCH_PASSWORD;
import static com.unlam.developerstudentclub.silapu.Utils.Util.URL_File;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogDetail extends DialogFragment {

    @Nullable  @BindView(R.id.ti_aduan)    TextFieldBoxes ti_aduan;
    @Nullable  @BindView(R.id.ti_perihal)    TextFieldBoxes ti_perihal;
    @Nullable  @BindView(R.id.edt_aduan)    ExtendedEditText edt_aduan;
    @Nullable  @BindView(R.id.edt_perihal)    ExtendedEditText edt_perihal;
    @Nullable @BindView(R.id.edt_email)  ExtendedEditText edt_email;
    @Nullable @BindView(R.id.edt_alamat) ExtendedEditText edt_alamat;
    @Nullable @BindView(R.id.edt_nama) ExtendedEditText edt_nama;
    @Nullable @BindView(R.id.edt_numberPhone) ExtendedEditText edt_phoneNumber;
    @Nullable @BindView(R.id.edt_kotaLahir) ExtendedEditText edt_kotaLahir;
    @Nullable @BindView(R.id.edt_tanggalLahir) ExtendedEditText edt_tanggalLahir;
    @Nullable @BindView(R.id.edt_nomorIdentitas) ExtendedEditText edt_nomorIdentitas;
    @Nullable @BindView(R.id.edt_permintaan) ExtendedEditText edt_permintaan;
    @Nullable @BindView(R.id.edt_tujuan) ExtendedEditText edt_tujuan;
    @Nullable @BindView(R.id.edt_cara) ExtendedEditText edt_cara;
    @Nullable @BindView(R.id.edt_jk) ExtendedEditText edt_jk;
    @Nullable @BindView(R.id.edt_identitas) ExtendedEditText edt_identitas;
    @Nullable @BindView(R.id.edt_password_lama) ExtendedEditText edt_password_lama;
    @Nullable @BindView(R.id.edt_password_baru) ExtendedEditText edt_password_baru;
    @Nullable @BindView(R.id.ti_password_lama) TextFieldBoxes ti_password_lama;
    @Nullable @BindView(R.id.ti_password_baru) TextFieldBoxes ti_password_baru;
    @Nullable @BindView(R.id.ti_nama)  TextFieldBoxes ti_nama;
    @Nullable @BindView(R.id.ti_phone)  TextFieldBoxes ti_phone;
    @Nullable @BindView(R.id.ti_alamat)  TextFieldBoxes ti_alamat;
    @Nullable @BindView(R.id.ti_kotaLahir)  TextFieldBoxes ti_kotaLahir;
    @Nullable @BindView(R.id.ti_tanggalLahir)  TextFieldBoxes ti_tanggalLahir;
    @Nullable @BindView(R.id.ti_nomorIdentitas) TextFieldBoxes ti_nomorIdentitas;
    @Nullable @BindView(R.id.btn_galeri)    Button btn_galeri;
    @Nullable @BindView(R.id.plate_img)    CircleImageView plate_img;
    @Nullable @BindView(R.id.ti_jenisKelamin)  MaterialSpinner spinner_jenisKelamin;
    @Nullable @BindView(R.id.ti_identity)   MaterialSpinner spinner_identityCard;

    private UserPreference userPreference;
    private String filepath = "";
    final Calendar myCalendar = Calendar.getInstance();

    NoticeDialogListener mListener;
    public interface NoticeDialogListener {
        void onPasswordConfirmed(String password);
        void onIdentityUpdateConfirmed(UserData data);
        void onProfileUpdateConfirmed(UserData data);
    }

    public DialogDetail() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        final String whichOne = getArguments().getString(DIALOG_DETIL);
        if(!whichOne.equals(DIALOG_PENGADUAN_DETAIL) || !whichOne.equals(DIALOG_PERDATA_DETAIL)) {

            final AlertDialog alertDialog = (AlertDialog) getDialog();
            if (alertDialog != null) {
                Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (whichOne.equals(DIALOG_GANTI_PASSWORD)) {
                                Boolean isComplete = true;
                                String password_baru = edt_password_baru.getText().toString().trim();
                                String password_lama = edt_password_lama.getText().toString().trim();

                                if (TextUtils.isEmpty(password_lama)) {
                                    isComplete = false;
                                    ti_password_lama.setError(ERROR_FIELD_KOSONG, false);
                                } else if (!userPreference.getPassword().equals(password_lama)) {
                                    isComplete = false;
                                    ti_password_lama.setError(ERROR_NOT_MATCH_PASSWORD, false);
                                }

                                if (TextUtils.isEmpty(password_baru)) {
                                    isComplete = false;
                                    ti_password_baru.setError(ERROR_FIELD_KOSONG, false);
                                }

                                if (isComplete) {
                                    mListener.onPasswordConfirmed(password_baru);
                                    alertDialog.dismiss();
                                }
                            } else if (getArguments().getString(DIALOG_DETIL).equals(DIALOG_GANTI_IDENTITAS)) {
                                Boolean isComplete = true;
                                String nomorIdentitas = edt_nomorIdentitas.getText().toString();

                                if(nomorIdentitas.isEmpty()){
                                    isComplete = false;
                                    ti_nomorIdentitas.setError(ERROR_FIELD_KOSONG, false);
                                }

                                if(isComplete) {
                                    UserData userData = new UserData();
                                    userData.setNoIdentitas(nomorIdentitas);
                                    userData.setFilepath(filepath);
                                    userData.setIdentitas(spinner_identityCard.getText().toString());
                                    mListener.onIdentityUpdateConfirmed(userData);
                                    alertDialog.dismiss();
                                }

                            } else if (getArguments().getString(DIALOG_DETIL).equals(DIALOG_GANTI_PROFIL)) {

                                Boolean isComplete = true;
                                String alamat = edt_alamat.getText().toString().trim();
                                String nama = edt_nama.getText().toString().trim();
                                String phone = edt_phoneNumber.getText().toString().trim();
                                String kotaLahir = edt_kotaLahir.getText().toString().trim();
                                String tanggalLahir = edt_tanggalLahir.getText().toString().trim();

                                if(TextUtils.isEmpty(alamat)){
                                    isComplete = false;
                                    ti_alamat.setError(ERROR_FIELD_KOSONG,false);
                                }

                                if(TextUtils.isEmpty(nama)){
                                    isComplete = false;
                                    ti_nama.setError(ERROR_FIELD_KOSONG,false);
                                }

                                if(TextUtils.isEmpty(kotaLahir)){
                                    isComplete = false;
                                    ti_kotaLahir.setError(ERROR_FIELD_KOSONG,true);
                                }

                                if(TextUtils.isEmpty(phone)){
                                    isComplete = false;
                                    ti_phone.setError(ERROR_FIELD_KOSONG,false);
                                }

                                if(TextUtils.isEmpty(tanggalLahir)){
                                    isComplete = false;
                                    ti_tanggalLahir.setError(ERROR_FIELD_KOSONG, false);
                                }

                                if(isComplete){
                                    UserData userData = new UserData();
                                    userData.setAlamat(alamat);
                                    userData.setNama(nama);
                                    userData.setTelp(phone);
                                    userData.setTmptLhr(kotaLahir);
                                    userData.setTglLhr(tanggalLahir);
                                    userData.setJk(spinner_jenisKelamin.getText().toString());
                                    mListener.onProfileUpdateConfirmed(userData);
                                    alertDialog.dismiss();
                                }
                            }
                        }
                    });
            }

        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        userPreference =  new UserPreference(getActivity());

        View view;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String whichDialog = getArguments().getString(DIALOG_DETIL);

        if(whichDialog.equals(DIALOG_PENGADUAN_DETAIL)){
            view = inflater.inflate(R.layout._detil_pengaduan,null);
            ButterKnife.bind(this,view);
            PengaduanItem pengaduanItem = getArguments().getParcelable(DIALOG_PARCABLE);
            edt_aduan.setText(pengaduanItem.getAduan());
            edt_perihal.setText(pengaduanItem.getPerihal());

        } else if (whichDialog.equals(DIALOG_PERDATA_DETAIL)) {
            view = inflater.inflate(R.layout._detil_perdata,null);
            ButterKnife.bind(this,view);
            PerdataItem perdataItem = getArguments().getParcelable(DIALOG_PARCABLE);

            edt_email.setText(perdataItem.getEmail());
            edt_alamat.setText(perdataItem.getAlamat2());
            edt_nama.setText(perdataItem.getNama2());
            edt_phoneNumber.setText(perdataItem.getTelp2());
            edt_kotaLahir.setText(perdataItem.getTmptlhr2());
            edt_tanggalLahir.setText(perdataItem.getTgllhr2());
            edt_nomorIdentitas.setText(perdataItem.getNoIdentitas2());
            edt_permintaan.setText(perdataItem.getPermintaan());
            edt_tujuan.setText(perdataItem.getTujuan());
            edt_cara.setText(perdataItem.getCara());
            edt_jk.setText(perdataItem.getGender2());
            edt_identitas.setText(perdataItem.getIdentitas2());

        } else if(whichDialog.equals(DIALOG_GANTI_PASSWORD)) {

            view = inflater.inflate(R.layout._ganti_password,null);
            ButterKnife.bind(this,view);
            builder.setPositiveButton("GANTI", null);

        } else if(whichDialog.equals(DIALOG_GANTI_IDENTITAS)) {

            view = inflater.inflate(R.layout._ganti_identitas,null);
            ButterKnife.bind(this,view);

            spinner_identityCard.setItems(getResources().getStringArray(R.array.identitas));
            Glide.with(this).load(URL_File + userPreference.getFile()).into(plate_img);
            btn_galeri.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new ChooserDialog(getActivity())
                            .withFilter(false, false, "jpg", "jpeg", "png")
                            .withChosenListener(new ChooserDialog.Result() {
                                @Override
                                public void onChoosePath(String path, File pathFile) {
                                    filepath = path;
                                    Glide.with(getActivity()).load(pathFile).into(plate_img);
                                    Toast.makeText(getActivity(), "FILE: " + path, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .build()
                            .show();
                }
            });
            spinner_identityCard.setSelectedIndex(itemSpinner(R.array.identitas,userPreference.getIdentitas()));
            edt_nomorIdentitas.setText(userPreference.getNoIdentitas());
            builder.setPositiveButton("GANTI", null);

        } else {

            view = inflater.inflate(R.layout._ganti_profil,null);
            ButterKnife.bind(this,view);

            spinner_jenisKelamin.setItems(getResources().getStringArray(R.array.jeniskelamin));
            edt_tanggalLahir.setEnabled(false);
            ti_tanggalLahir.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            updateLabel();
                        }
                    };
                    new DatePickerDialog(getActivity(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            edt_tanggalLahir.setText(userPreference.getTanggalLahir());
            edt_nama.setText(userPreference.getNama());
            edt_alamat.setText(userPreference.getAlamat());
            spinner_jenisKelamin.setSelectedIndex(itemSpinner(R.array.jeniskelamin,userPreference.getJenisKelamin()));
            edt_kotaLahir.setText(userPreference.getTempatLahir());
            edt_phoneNumber.setText(userPreference.getTelp());
            builder.setPositiveButton("GANTI", null);

        }
        builder.setView(view).setNegativeButton(R.string.Keluar, null);
        return  builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
            try {
                mListener = (NoticeDialogListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement NoticeDialogListener");
            }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        edt_tanggalLahir.setText(sdf.format(myCalendar.getTime()));

    }

    public int itemSpinner(int resource, String match){

        int value = 0;
        String [] temp = getResources().getStringArray(resource);

        for(int i = 0; i < temp.length ; i++)
            if (temp[i].substring(0,1).equals(match.substring(0,1)))
                value = i;

        return  value;
    }

}
