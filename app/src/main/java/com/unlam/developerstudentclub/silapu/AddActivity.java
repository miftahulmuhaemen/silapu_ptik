package com.unlam.developerstudentclub.silapu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.unlam.developerstudentclub.silapu.Utils.SpinnerCostume.SpinnerCostumeAdapter;
import com.unlam.developerstudentclub.silapu.Utils.SpinnerCostume.StateVO;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity {

    @Nullable @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable @BindView(R.id.spinner_aduan)
    Spinner spinner_aduan;

    @Nullable @BindView(R.id.spinner_cara)
    MaterialSpinner spinner_cara;

    @Nullable @BindView(R.id.edt_message)
    EditText edt_message;

    @Nullable @BindView(R.id.tujuan)
    EditText edt_tujuan;

    public static int REQUEST_CODE = 113;
    public static String COMPOSE_CODE = "COMPOSECODE";
    public static String COMPOSE_MESSAGE = "compose_message";
    public static String COMPOSE_GOAL = "compose_goal";
    public static String COMPOSE_ATTACHMENT = "compose_attachment";
    public static String COMPOSE_SPINNER = "compose_spinner";

    public static String COMPOSE_PENGADUAN = "COMPOSE_PENGADUAN";
    public static String COMPOSE_PERDATA = "COMPOSE_PERDATA";
    public static int RESULT_CODE_PENGADUAN = 111;
    public static int RESULT_CODE_PERDATA = 222;

    SpinnerCostumeAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getStringExtra(COMPOSE_CODE).equals(COMPOSE_PERDATA)){
            setContentView(R.layout.activity_add_perdata);
            ButterKnife.bind(this);
            setTitle(getResources().getString(R.string.compose_perdata));
            spinner_cara.setItems(getResources().getStringArray(R.array.informasi));
        } else {
            setContentView(R.layout.activity_add_pengaduan);
            ButterKnife.bind(this);
            setTitle(getResources().getString(R.string.compose_pengaduan));

            ArrayList<StateVO> listVOs = new ArrayList<>();
            for (int i = 0; i < getResources().getStringArray(R.array.aduan).length; i++) {
                StateVO stateVO = new StateVO();
                stateVO.setTitle(getResources().getStringArray(R.array.aduan)[i]);
                stateVO.setSelected(false);
                listVOs.add(stateVO);
            }
            myAdapter = new SpinnerCostumeAdapter(this, 0,
                    listVOs);
            spinner_aduan.setAdapter(myAdapter);
//            spinner_aduan.setItems(getResources().getStringArray(R.array.aduan));
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if(getIntent().getStringExtra(COMPOSE_CODE).equals(COMPOSE_PERDATA))
            menuInflater.inflate(R.menu.add_perdata,menu);
        else
            menuInflater.inflate(R.menu.add_pengaduan,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resulIntent = new Intent();
        switch (item.getItemId()){
            case R.id.send_perdata :
                resulIntent.putExtra(COMPOSE_SPINNER,spinner_cara.getText());
                resulIntent.putExtra(COMPOSE_GOAL,edt_tujuan.getText());
                resulIntent.putExtra(COMPOSE_MESSAGE,edt_message.getText());
                setResult(RESULT_CODE_PERDATA,resulIntent);
                finish();
                break;

            case R.id.send_pengaduan :
                resulIntent.putExtra(COMPOSE_SPINNER,myAdapter.GetData());
                resulIntent.putExtra(COMPOSE_MESSAGE,edt_message.getText());
                setResult(RESULT_CODE_PENGADUAN,resulIntent);
                finish();
                break;
            case R.id.attachment :
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
