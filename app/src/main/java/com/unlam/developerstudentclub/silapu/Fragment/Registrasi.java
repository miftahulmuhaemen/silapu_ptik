package com.unlam.developerstudentclub.silapu.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.unlam.developerstudentclub.silapu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Registrasi extends Fragment {

    @Nullable @BindView(R.id.edt_email) EditText edt_email;
    @Nullable @BindView(R.id.edt_alamat) EditText edt_alamat;
    @Nullable @BindView(R.id.edt_nama) EditText edt_nama;
    @Nullable @BindView(R.id.edt_password) EditText edt_password;

    @Nullable @BindView(R.id.spin_jk)  Spinner spn_JenisKelamin;
    @Nullable @BindView(R.id.spin_idt) Spinner spn_Identitas;
    @Nullable @BindView(R.id.edt_nomberPhone) EditText edt_phoneNumber;

    public static String FRAGEMENT_IDENTITY = "identity";

    public Registrasi() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        View view;
        int check = getArguments().getInt(FRAGEMENT_IDENTITY,0);

        if( check == 1)
            view = inflater.inflate(R.layout.frag_regist1st,container,false);
        else if(check == 2)
            view = inflater.inflate(R.layout.frag_regist2nd,container,false);
        else if(check == 3)
            view = inflater.inflate(R.layout.frag_regist3rd,container,false);
        else if(check == 4)
            view = inflater.inflate(R.layout.frag_regist4th,container,false);
        else
            view = inflater.inflate(R.layout.frag_regist4th,container,false);

        ButterKnife.bind(view);

        return view;
    }

}
