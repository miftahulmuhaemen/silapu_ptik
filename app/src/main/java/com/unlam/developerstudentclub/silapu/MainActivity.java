package com.unlam.developerstudentclub.silapu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.unlam.developerstudentclub.silapu.API.ApiDefaultResponse;
import com.unlam.developerstudentclub.silapu.API.ApiGenerator;
import com.unlam.developerstudentclub.silapu.API.ApiInterface;
import com.unlam.developerstudentclub.silapu.API.ApiResponseUser;
import com.unlam.developerstudentclub.silapu.Adapter.FragementAdapter;
import com.unlam.developerstudentclub.silapu.Entity.UserData;
import com.unlam.developerstudentclub.silapu.Fragment.Global;
import com.unlam.developerstudentclub.silapu.Utils.LockableViewPager;
import com.unlam.developerstudentclub.silapu.Utils.UserPreference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;
import lombok.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.unlam.developerstudentclub.silapu.AddActivity.COMPOSE_ATTACHMENT;
import static com.unlam.developerstudentclub.silapu.AddActivity.COMPOSE_MESSAGE;
import static com.unlam.developerstudentclub.silapu.AddActivity.COMPOSE_SPINNER;
import static com.unlam.developerstudentclub.silapu.AddActivity.REQUEST_CODE;
import static com.unlam.developerstudentclub.silapu.Fragment.Global.FRAGMENT_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.Fragment.Global.FRAGMENT_PERDATA;
import static com.unlam.developerstudentclub.silapu.Fragment.Global.FRAGMENT_PROFIL;

public class MainActivity extends AppCompatActivity implements Global.onCompleteResponse {

    @BindView(R.id.viewpager)
    LockableViewPager viewPager;

    @BindView(R.id.ntb)
    NavigationTabBar navigationTabBar;

    public static final String EXT_PDF = "pdf";
    public static final String EXT_DOC = "doc";
    public static final String EXT_DOCX = "docx";
    public static final String EXT_PNG = "png";
    public static final String EXT_JPG = "jpg";
    public static final String EXT_BMP = "bmp";

    ApiInterface api = ApiGenerator.createService(ApiInterface.class); // Interface Retrofit
    private UserPreference userPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        userPreference =  new UserPreference(this);
        if(userPreference.getNama().isEmpty()){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        setupViewPager(viewPager);
        navigationTabBar.setBehaviorEnabled(true);
        navigationTabBar.setViewPager(viewPager);
        navigationTabBar.setModels(tabModel());
    }

    private ArrayList<NavigationTabBar.Model> tabModel () {

        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        models.add(new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_content_paste_black_24dp),
                        R.color.White)
                .selectedIcon(getResources().getDrawable(R.drawable.ic_assignment_black_24dp))
                .title("Permohonan")
                .build());

        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_pan_tool_black_24dp),
                R.color.White)
                .title("Pengaduan")
                .build());

        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_person_outline_black_24dp),
                R.color.White)
                .selectedIcon(getResources().getDrawable(R.drawable.ic_person_black_24dp))
                .title("Profil")
                .build());

        return models;
    }

    private void setupViewPager(ViewPager viewPager) {
        FragementAdapter adapter = new FragementAdapter(getSupportFragmentManager());
        Global mFragment = new Global();
        Bundle bundle = new Bundle();

        bundle.putInt(Global.FRAGEMENT_IDENTITY,FRAGMENT_PERDATA);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part5");

        bundle = new Bundle();
        mFragment = new Global();
        bundle.putInt(Global.FRAGEMENT_IDENTITY,FRAGMENT_PENGADUAN);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part6");

        bundle = new Bundle();
        mFragment = new Global();
        bundle.putInt(Global.FRAGEMENT_IDENTITY,FRAGMENT_PROFIL);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part3");

        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == AddActivity.RESULT_CODE_PENGADUAN){

                File file = new File(data.getStringExtra(COMPOSE_ATTACHMENT));
                final RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);

                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("key",createPartFromString(BuildConfig.API_KEY));
                map.put("id",createPartFromString(String.valueOf(userPreference.getID())));
                map.put("email",createPartFromString(userPreference.getEmail()));
                map.put("perihal",createPartFromString(data.getStringExtra(COMPOSE_SPINNER)));
                map.put("aduan",createPartFromString(data.getStringExtra(COMPOSE_MESSAGE)));

                Call<ApiDefaultResponse> call = api.postPengaduan(map,body);
                call.enqueue(new Callback<ApiDefaultResponse>() {
                    @Override
                    public void onResponse(Call<ApiDefaultResponse> call, Response<ApiDefaultResponse> response) {
                        if(response.isSuccessful()){
                            Snackbar.make(getCurrentFocus(), response.body().getMsg(), Snackbar.LENGTH_LONG).show();
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<ApiDefaultResponse> call, Throwable t) {
                        if (t instanceof IOException) {
                            Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else if(resultCode == AddActivity.RESULT_CODE_PERDATA){
                Toast.makeText(this,"Perdata",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }

    @Override
    public void onCompleteFormResponse(UserData text, int Fragment) {
        //empty needed
    }
}
