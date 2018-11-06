package com.unlam.developerstudentclub.silapu;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.unlam.developerstudentclub.silapu.API.ApiGenerator;
import com.unlam.developerstudentclub.silapu.API.ApiInterface;
import com.unlam.developerstudentclub.silapu.API.ApiResponseUser;
import com.unlam.developerstudentclub.silapu.Box.App;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.Entity.UserData;
import com.unlam.developerstudentclub.silapu.Utils.UserPreference;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.unlam.developerstudentclub.silapu.Utils.Util.ERROR_FIELD_EMAIL_NOTVALID;
import static com.unlam.developerstudentclub.silapu.Utils.Util.ERROR_FIELD_KOSONG;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.btn_daftar)
    Button btn_daftar;

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.progressbarLogin)
    ProgressBar progressBar;

    @BindView(R.id.edt_email)
    ExtendedEditText edt_email;

    @BindView(R.id.edt_password)
    ExtendedEditText edt_password;

    @BindView(R.id.ti_password)
    TextFieldBoxes ti_password;

    @BindView(R.id.ti_email)
    TextFieldBoxes ti_email;

    ApiInterface api = ApiGenerator.createService(ApiInterface.class);
    private UserPreference userPreference;
    private Box<PengaduanItem> pengaduanItemBox;
    private Box<PerdataItem> perdataItemBox;


    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        userPreference =  new UserPreference(this);
        pengaduanItemBox = ((App) getApplication()).getBoxStore().boxFor(PengaduanItem.class);
        perdataItemBox = ((App) getApplication()).getBoxStore().boxFor(PerdataItem.class);

        if(!userPreference.getNama().isEmpty()){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        ti_password.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = edt_password.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                boolean isEmpty = false;

                if(TextUtils.isEmpty(email)){
                    isEmpty = true;
                    ti_email.setError(ERROR_FIELD_KOSONG,true);
                } else if(!isValidEmail(email)){
                    isEmpty = true;
                    ti_email.setError(ERROR_FIELD_EMAIL_NOTVALID, true);
                }

                if(TextUtils.isEmpty(password)){
                    isEmpty = true;
                    ti_password.setError(ERROR_FIELD_KOSONG,false);
                }

                if(!isEmpty) {
                    progressBar.setVisibility(View.VISIBLE);

                    Call<ApiResponseUser<UserData>> call = api.getLogin(BuildConfig.API_KEY, email, password);
                    call.enqueue(new Callback<ApiResponseUser<UserData>>() {
                        @Override
                        public void onResponse(Call<ApiResponseUser<UserData>> call, Response<ApiResponseUser<UserData>> response) {
                            if (response.isSuccessful()) {
                                 userPreference.setPreference(response.body().getAccount());
                                 pengaduanItemBox.removeAll();
                                 perdataItemBox.removeAll();
                                 Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                 startActivity(intent);
                                 finish();
                            } else {
                                /*it seems you need to give the same JSON structure in order to catch the error message*/
                                Snackbar.make(getCurrentFocus(), "Email dan Password Salah", Snackbar.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponseUser<UserData>> call, Throwable t) {
                            if (t instanceof IOException) {
                                Toast.makeText(LoginActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



}
