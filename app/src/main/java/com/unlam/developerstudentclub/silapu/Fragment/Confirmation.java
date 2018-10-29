package com.unlam.developerstudentclub.silapu.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.unlam.developerstudentclub.silapu.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lombok.Getter;
import lombok.Setter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Confirmation  extends DialogFragment {

    @Getter @Setter
    OnOptionDialogListener onOptionDialogListener;

    @OnClick({R.id.btn_tidak, R.id.btn_kirim}) void OnClick(Button view){
        switch (view.getId()){
            case R.id.btn_tidak :
                getDialog().cancel();
                break;
            case R.id.btn_kirim :
                getOnOptionDialogListener().onOptionChoosen(true);
                getDialog().cancel();
        }
    }

    public interface OnOptionDialogListener{
        void onOptionChoosen(Boolean text);
    }

    public Confirmation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.frag_confirm,container);
        ButterKnife.bind(this,view);
        return view;
    }


}
