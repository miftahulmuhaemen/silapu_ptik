package com.unlam.developerstudentclub.silapu;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.obsez.android.lib.filechooser.ChooserDialog;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.Fragment.ModifyUser;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_ATTACHMENT;
import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_CODE;
import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_MESSAGE;
import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_PERDATA;
import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_PERDATA_PRIBADI;
import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_SPINNER;
import static com.unlam.developerstudentclub.silapu.Utils.Util.PARCELABLE_GANTI_ORANG;
import static com.unlam.developerstudentclub.silapu.Utils.Util.RESULT_CODE_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.Utils.Util.RESULT_CODE_PERDATA;

public class AddActivity extends AppCompatActivity implements ModifyUser.NoticeDialogListener {

    @Nullable @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable @BindView(R.id.spinner_aduan)
    EditText spinner_aduan;
    @Nullable @BindView(R.id.spinner_cara)
    EditText spinner_cara;
    @Nullable @BindView(R.id.edt_message)
    EditText edt_message;
    @Nullable @BindView(R.id.tujuan)
    EditText edt_tujuan;


    PerdataItem perdataItem;
    private String filepath = "";
    private boolean isComplete = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getStringExtra(COMPOSE_CODE).equals(COMPOSE_PENGADUAN)){
            setContentView(R.layout.activity_add_pengaduan);
            ButterKnife.bind(this);
            setTitle(getResources().getString(R.string.compose_pengaduan));

            spinner_aduan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ArrayList <Integer> mSelectedItems = new ArrayList();
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                    builder.setMultiChoiceItems(R.array.aduan, null,
                            new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    if (isChecked) {
                                        mSelectedItems.add(which);
                                    } else if (mSelectedItems.contains(which)) {
                                        mSelectedItems.remove(Integer.valueOf(which));
                                    }
                                }
                            })
                            .setPositiveButton(R.string.pilih, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String [] temp = {};
                                    String text = "";
                                    temp = getResources().getStringArray(R.array.aduan);
                                    for(Integer i : mSelectedItems)
                                        text = text + temp[i] + ", ";
                                    if(!text.isEmpty())
                                        text = text.substring(0, text.length()-2);
                                    spinner_aduan.setText(text);
                                }
                            })
                            .setNegativeButton(R.string.Tidak, null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        } else {
            setContentView(R.layout.activity_add_perdata);
            ButterKnife.bind(this);
            perdataItem = new PerdataItem();

            setTitle(getResources().getString(R.string.compose_perdata));
            spinner_cara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                    builder.setItems(R.array.informasi, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String [] temp;
                            temp = getResources().getStringArray(R.array.informasi);
                            spinner_cara.setText(temp[i]);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
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
        else if(getIntent().getStringExtra(COMPOSE_CODE).equals(COMPOSE_PERDATA_PRIBADI))
            menuInflater.inflate(R.menu.add_perdata_pribadi,menu);
        else
            menuInflater.inflate(R.menu.add_pengaduan,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent resultIntent = new Intent();
        Boolean isValid = true;
        switch (item.getItemId()){
            case R.id.send_perdata :

                if(edt_message.getText().toString().isEmpty()){
                    isValid = false;
                    Snackbar.make(getCurrentFocus(), "Pesan masih kosong.", Snackbar.LENGTH_SHORT).show();
                }

                else if(edt_tujuan.getText().toString().isEmpty()){
                    isValid = false;
                    Snackbar.make(getCurrentFocus(), "Tujuan masih kosong.", Snackbar.LENGTH_SHORT).show();
                }

                if(getIntent().getStringExtra(COMPOSE_CODE).equals(COMPOSE_PERDATA)){
                    if(!isComplete){
                        isValid = isComplete;
                        Snackbar.make(getCurrentFocus(), "Data perwakilan masih kosong.", Snackbar.LENGTH_SHORT).show();
                    }
                }

                if(isValid) {
                    perdataItem.setPermintaan(edt_message.getText().toString());
                    perdataItem.setTujuan(edt_tujuan.getText().toString());
                    perdataItem.setCara(spinner_cara.getText().toString());
                    resultIntent.putExtra(PARCELABLE_GANTI_ORANG,perdataItem);
                    setResult(RESULT_CODE_PERDATA,resultIntent);
                    finish();
                }

                break;

            case R.id.send_pengaduan :
                if(spinner_aduan.getText().toString().isEmpty()){
                    isValid = false;
                    Snackbar.make(getCurrentFocus(), "Aduan masih kosong.", Snackbar.LENGTH_SHORT).show();
                }
                else if(edt_message.getText().toString().isEmpty()) {
                    isValid = false;
                    Snackbar.make(getCurrentFocus(), "Pesan masih kosong.", Snackbar.LENGTH_SHORT).show();
                }

                if(isValid) {
                    resultIntent.putExtra(COMPOSE_ATTACHMENT, filepath);
                    resultIntent.putExtra(COMPOSE_SPINNER, spinner_aduan.getText().toString());
                    resultIntent.putExtra(COMPOSE_MESSAGE, edt_message.getText().toString());
                    setResult(RESULT_CODE_PENGADUAN, resultIntent);
                    finish();
                }
                break;

            case R.id.attachment :

                new ChooserDialog(this)
                .withChosenListener(new ChooserDialog.Result() {
                @Override
                public void onChoosePath(String path, File pathFile) {
                    Toast.makeText(AddActivity.this, "FILE: " + path, Toast.LENGTH_SHORT).show();
                    filepath = path;
                }})
                    .enableOptions(false)
                    .build()
                    .show();
                break;

            case R.id.add_person :
                DialogFragment dialog = new ModifyUser();
                dialog.show(getFragmentManager(), ModifyUser.class.getSimpleName());
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    /**
     *
     *  ModifyUser Callback Listener
     * @param dialog
     */

    @Override
    public void onAddGuestConfirmed(PerdataItem dialog) {
        perdataItem = dialog;
        isComplete = true;
    }


}
