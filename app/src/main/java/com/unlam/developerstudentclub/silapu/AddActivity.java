package com.unlam.developerstudentclub.silapu;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.mediastore.MediaStoreUtil;
import com.bumptech.glide.load.model.MediaStoreFileLoader;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.unlam.developerstudentclub.silapu.Adapter.SpinnerCostumeAdapter;
import com.unlam.developerstudentclub.silapu.Entity.SpinnerCostumeItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.unlam.developerstudentclub.silapu.RegisterActivity.REQUEST_CODE_REGISTER;

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
    public static int REQUEST_CODE_FILE = 234;

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
    private String filepath = "";
    String _path = "";

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

            ArrayList<SpinnerCostumeItem> listVOs = new ArrayList<>();
            for (int i = 0; i < getResources().getStringArray(R.array.aduan).length; i++) {
                SpinnerCostumeItem spinnerCostumeItem = new SpinnerCostumeItem();
                spinnerCostumeItem.setTitle(getResources().getStringArray(R.array.aduan)[i]);
                spinnerCostumeItem.setSelected(false);
                listVOs.add(spinnerCostumeItem);
            }

            myAdapter = new SpinnerCostumeAdapter(this, 0,  listVOs);
            spinner_aduan.setAdapter(myAdapter);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_FILE){
            if(resultCode == RESULT_OK){
                filepath = getRealPathFromURI(data.getData());
                Snackbar.make(getCurrentFocus(), "File Attached!" + filepath, Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(getCurrentFocus(), "File Failed Attached!", Snackbar.LENGTH_SHORT).show();
            }
        }
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
        Intent resultIntent = new Intent();
        Boolean isValid = true;
        switch (item.getItemId()){
            case R.id.send_perdata :
                resultIntent.putExtra(COMPOSE_SPINNER,spinner_cara.getText());
                resultIntent.putExtra(COMPOSE_GOAL,edt_tujuan.getText());
                resultIntent.putExtra(COMPOSE_MESSAGE,edt_message.getText());
                setResult(RESULT_CODE_PERDATA,resultIntent);
                finish();
                break;

            case R.id.send_pengaduan :

                if(myAdapter.GetData().isEmpty()){
                    isValid = false;
                    Snackbar.make(getCurrentFocus(), "Aduan masih kosong.", Snackbar.LENGTH_SHORT).show();
                }
                else if(filepath.isEmpty()) {
                    isValid = false;
                    Snackbar.make(getCurrentFocus(), "Bukti masih kosong.", Snackbar.LENGTH_SHORT).show();
                }
                else if(edt_message.getText().toString().isEmpty()) {
                    isValid = false;
                    Snackbar.make(getCurrentFocus(), "Pesan masih kosong.", Snackbar.LENGTH_SHORT).show();
                }

                if(isValid) {
                    resultIntent.putExtra(COMPOSE_ATTACHMENT, filepath);
                    resultIntent.putExtra(COMPOSE_SPINNER, myAdapter.GetData());
                    resultIntent.putExtra(COMPOSE_MESSAGE, edt_message.getText().toString());
                    setResult(RESULT_CODE_PENGADUAN, resultIntent);
                    finish();
                }
                break;

            case R.id.attachment :

                new ChooserDialog(this)
                        .withStartFile(_path)
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {
                                Toast.makeText(AddActivity.this, "FOLDER: " + path, Toast.LENGTH_SHORT).show();
                                filepath = path;
                            }
                        })
                        .enableOptions(true)
                        .build()
                        .show();

                /*Seems work on Any Device, but Only Accept Image*/
//                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, REQUEST_CODE_FILE);

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
