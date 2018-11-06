package com.unlam.developerstudentclub.silapu.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_DETIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_PARCABLE;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_PENGADUAN_DETAIL;

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
    public DetailItem() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

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

        } else {
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

        }
        builder.setView(view).setNegativeButton(R.string.Keluar, null);
        return  builder.create();
    }
}
