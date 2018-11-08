package com.unlam.developerstudentclub.silapu.Fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.unlam.developerstudentclub.silapu.Utils.Util.ERROR_FIELD_EMAIL_NOTVALID;
import static com.unlam.developerstudentclub.silapu.Utils.Util.ERROR_FIELD_KOSONG;


public class ModifyUser extends DialogFragment {

    @Nullable @BindView(R.id.edt_email)  ExtendedEditText edt_email;
    @Nullable @BindView(R.id.edt_alamat) ExtendedEditText edt_alamat;
    @Nullable @BindView(R.id.edt_nama) ExtendedEditText edt_nama;
    @Nullable @BindView(R.id.edt_numberPhone) ExtendedEditText edt_phoneNumber;
    @Nullable @BindView(R.id.edt_kotaLahir) ExtendedEditText edt_kotaLahir;
    @Nullable @BindView(R.id.edt_tanggalLahir) ExtendedEditText edt_tanggalLahir;
    @Nullable @BindView(R.id.edt_nomorIdentitas) ExtendedEditText edt_nomorIdentitas;
    @Nullable @BindView(R.id.ti_password) TextFieldBoxes ti_password;
    @Nullable @BindView(R.id.ti_email)  TextFieldBoxes ti_email;
    @Nullable @BindView(R.id.ti_nama)  TextFieldBoxes ti_nama;
    @Nullable @BindView(R.id.ti_phone)  TextFieldBoxes ti_phone;
    @Nullable @BindView(R.id.ti_alamat)  TextFieldBoxes ti_alamat;
    @Nullable @BindView(R.id.ti_kotaLahir)  TextFieldBoxes ti_kotaLahir;
    @Nullable @BindView(R.id.ti_tanggalLahir)  TextFieldBoxes ti_tanggalLahir;
    @Nullable @BindView(R.id.ti_idt) TextFieldBoxes ti_noIdentitas;
    @Nullable @BindView(R.id.ti_jenisKelamin) MaterialSpinner spinner_jenisKelamin;
    @Nullable @BindView(R.id.ti_identity)   MaterialSpinner spinner_identityCard;
    final Calendar myCalendar = Calendar.getInstance();

    public ModifyUser() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = inflater.inflate(R.layout.frag_add_person,null);
        ButterKnife.bind(this,view);

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
        spinner_jenisKelamin.setItems(getResources().getStringArray(R.array.jeniskelamin));
        spinner_identityCard.setItems(getResources().getStringArray(R.array.identitas));

        builder.setView(view)
                .setPositiveButton("TAMBAHKAN", null)
                .setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDialog().dismiss();
                    }
                });

        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        final AlertDialog alertDialog = (AlertDialog) getDialog();
        if(alertDialog != null){
            Button positiveButton = alertDialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean isComplete = true;

                    String email = edt_email.getText().toString().trim();
                    String alamat = edt_alamat.getText().toString().trim();
                    String nama = edt_nama.getText().toString().trim();
                    String kotaLahir = edt_kotaLahir.getText().toString().trim();
                    String tanggalLahir = edt_tanggalLahir.getText().toString().trim();
                    String jenisKelamin = spinner_jenisKelamin.getText().toString().trim();
                    String identityCard = spinner_identityCard.getText().toString().trim();
                    String noIdentity = edt_nomorIdentitas.getText().toString().trim();
                    String phone = edt_phoneNumber.getText().toString().trim();

                    if(TextUtils.isEmpty(email)){
                        isComplete = false;
                        ti_email.setError(ERROR_FIELD_KOSONG,false);
                    } else if(!isValidEmail(email)){
                        isComplete = false;
                        ti_email.setError(ERROR_FIELD_EMAIL_NOTVALID,true);
                    }

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
                        ti_phone.setError(ERROR_FIELD_KOSONG, true);
                    }

                    if(TextUtils.isEmpty(tanggalLahir)){
                        isComplete = false;
                        ti_tanggalLahir.setError(ERROR_FIELD_KOSONG, false);
                    }

                    if(TextUtils.isEmpty(noIdentity)){
                        isComplete = false;
                        ti_noIdentitas.setError(ERROR_FIELD_KOSONG,false);
                    }

                    if(isComplete){
                        PerdataItem perdataItem = new PerdataItem();
                        perdataItem.setEmail2(email);
                        perdataItem.setAlamat2(alamat);
                        perdataItem.setNama2(nama);
                        perdataItem.setTmptlhr2(kotaLahir);
                        perdataItem.setTgllhr2(tanggalLahir);
                        perdataItem.setTelp2(phone);
                        perdataItem.setGender2(jenisKelamin);
                        perdataItem.setIdentitas2(identityCard);
                        perdataItem.setNoIdentitas2(noIdentity);
                        mListener.onAddGuestConfirmed(perdataItem);
                        alertDialog.dismiss();
                    }
                }
            });
        }
    }

    /*Triple Method in LoginActivity, needs to be simplified later*/
    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /*Double Method in Global, needs to be simplified later*/
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        edt_tanggalLahir.setText(sdf.format(myCalendar.getTime()));
    }

    NoticeDialogListener mListener;
    public interface NoticeDialogListener {
         void onAddGuestConfirmed(PerdataItem data);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }


}
