package com.unlam.developerstudentclub.silapu.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
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

import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.R;
import com.unlam.developerstudentclub.silapu.Utils.UserPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_DETIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_GANTI_PASSWORD;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_PARCABLE;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_PENGADUAN_DETAIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_PERDATA_DETAIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.ERROR_FIELD_EMAIL_NOTVALID;
import static com.unlam.developerstudentclub.silapu.Utils.Util.ERROR_FIELD_KOSONG;
import static com.unlam.developerstudentclub.silapu.Utils.Util.ERROR_NOT_MATCH_PASSWORD;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailItem extends DialogFragment {

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

    private UserPreference userPreference;

    NoticeDialogListenerGantiPassword mListener;
    public interface NoticeDialogListenerGantiPassword {
        void onDialogPositiveClick(String password);
    }

    public DetailItem() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getArguments().getString(DIALOG_DETIL).equals(DIALOG_GANTI_PASSWORD)){
            final AlertDialog alertDialog = (AlertDialog) getDialog();
            if(alertDialog != null){
                Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Boolean isComplete = true;
                        String password_baru = edt_password_baru.getText().toString().trim();
                        String password_lama = edt_password_lama.getText().toString().trim();

                        if(TextUtils.isEmpty(password_lama)){
                            isComplete = false;
                            ti_password_lama.setError(ERROR_FIELD_KOSONG,false);
                        } else if(!userPreference.getPassword().equals(password_lama)) {
                            isComplete = false;
                            ti_password_lama.setError(ERROR_NOT_MATCH_PASSWORD,false);
                        }

                        if(TextUtils.isEmpty(password_baru)){
                            isComplete = false;
                            ti_password_baru.setError(ERROR_FIELD_KOSONG,false);
                        }

                        if(isComplete){
                            mListener.onDialogPositiveClick(password_baru);
                            alertDialog.dismiss();
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


        } else {
            view = inflater.inflate(R.layout._ganti_password,null);
            ButterKnife.bind(this,view);
            builder.setPositiveButton("GANTI", null);
        }

        builder.setView(view).setNegativeButton(R.string.Keluar, null);
        return  builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        if(getArguments().getString(DIALOG_DETIL).equals(DIALOG_GANTI_PASSWORD)){
            try {
                // Instantiate the NoticeDialogListener so we can send events to the host
                mListener = (NoticeDialogListenerGantiPassword) activity;
            } catch (ClassCastException e) {
                // The activity doesn't implement the interface, throw exception
                throw new ClassCastException(activity.toString()
                        + " must implement NoticeDialogListener");
            }
        }
    }


}
