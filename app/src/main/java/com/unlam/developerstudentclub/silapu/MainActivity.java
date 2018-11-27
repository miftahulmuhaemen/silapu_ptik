package com.unlam.developerstudentclub.silapu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.unlam.developerstudentclub.silapu.API.ApiDefaultResponse;
import com.unlam.developerstudentclub.silapu.API.ApiGenerator;
import com.unlam.developerstudentclub.silapu.API.ApiInterface;
import com.unlam.developerstudentclub.silapu.API.ApiResponseUser;
import com.unlam.developerstudentclub.silapu.Adapter.FragementAdapter;
import com.unlam.developerstudentclub.silapu.Box.App;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.Entity.UserData;
import com.unlam.developerstudentclub.silapu.Fragment.DialogDetail;
import com.unlam.developerstudentclub.silapu.Fragment.Global;
import com.unlam.developerstudentclub.silapu.Utils.ImplicitlyListenerComposite;
import com.unlam.developerstudentclub.silapu.Utils.Implictly;
import com.unlam.developerstudentclub.silapu.Utils.LockableViewPager;
import com.unlam.developerstudentclub.silapu.Utils.UserPreference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;
import io.objectbox.Box;
import lombok.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_ATTACHMENT;
import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_MESSAGE;
import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_SPINNER;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_DETIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_GANTI_IDENTITAS;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_GANTI_PASSWORD;
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_GANTI_PROFIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGEMENT_IDENTITY;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_PERDATA;
import static com.unlam.developerstudentclub.silapu.Utils.Util.PARCELABLE_GANTI_ORANG;
import static com.unlam.developerstudentclub.silapu.Utils.Util.REQUEST_CODE;
import static com.unlam.developerstudentclub.silapu.Utils.Util.REQUEST_CODE_REGISTER;
import static com.unlam.developerstudentclub.silapu.Utils.Util.RESULT_CODE_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.Utils.Util.RESULT_CODE_PERDATA;


public class MainActivity extends AppCompatActivity implements Global.onCompleteResponse, DialogDetail.NoticeDialogListener {

    @Nullable
    @BindView(R.id.viewpager)
    LockableViewPager viewPager;

    @Nullable
    @BindView(R.id.ntb)
    NavigationTabBar navigationTabBar;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.progressbar_MainActivity)
    ProgressBar progressbar;

    ApiInterface api = ApiGenerator.createService(ApiInterface.class); // Interface Retrofit
    private UserPreference userPreference;
    private Box<PengaduanItem> pengaduanItemBox;
    private Box<PerdataItem> perdataItemBox;
    Bitmap bitmap;

    Implictly implictly; // Interface Composite GLOBAL_FRAGMENT
    ImplicitlyListenerComposite implicitlyListenerComposite = new ImplicitlyListenerComposite(); // Composite Listener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        userPreference =  new UserPreference(this);
        pengaduanItemBox = ((App) getApplication()).getBoxStore().boxFor(PengaduanItem.class);
        perdataItemBox = ((App) getApplication()).getBoxStore().boxFor(PerdataItem.class);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.overflowmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final DialogDetail dialogMainActivity = new DialogDetail();
        final Bundle bundle = new Bundle();

        switch (item.getItemId()){
            case R.id.menu :
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setItems(R.array.overflowmenu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                bundle.putString(DIALOG_DETIL, DIALOG_GANTI_PROFIL);
                                dialogMainActivity.setArguments(bundle);
                                dialogMainActivity.show(getFragmentManager(),DialogDetail.class.getSimpleName());
                                break;
                            case 1 :
                                bundle.putString(DIALOG_DETIL, DIALOG_GANTI_IDENTITAS);
                                dialogMainActivity.setArguments(bundle);
                                dialogMainActivity.show(getFragmentManager(),DialogDetail.class.getSimpleName());
                                break;
                            case 2 :
                                bundle.putString(DIALOG_DETIL, DIALOG_GANTI_PASSWORD);
                                dialogMainActivity.setArguments(bundle);
                                dialogMainActivity.show(getFragmentManager(),DialogDetail.class.getSimpleName());
                                break;
                            case 3 :
                                AlertDialog.Builder builder_ = new AlertDialog.Builder(MainActivity.this);
                                builder_.setTitle(R.string.Keluar)
                                        .setPositiveButton(R.string.Keluar, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                userPreference.clearePreference();
                                                pengaduanItemBox.removeAll();
                                                perdataItemBox.removeAll();
                                                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .setNegativeButton(R.string.Tidak, null)
                                        .create()
                                        .show();
                                break;
                        }
                            }}).create().show();
                break;
        }

        return super.onOptionsItemSelected(item);
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
                getResources().getDrawable(R.drawable.ic_chat_bubble_outline_white_24dp),
                R.color.White)
                .selectedIcon(getResources().getDrawable(R.drawable.ic_chat_black_24dp))
                .title("Pengaduan")
                .build());

        return models;
    }

    private void setupViewPager(ViewPager viewPager) {
        FragementAdapter adapter = new FragementAdapter(getSupportFragmentManager());
        Global mFragment = new Global();
        Bundle bundle = new Bundle();

        bundle.putInt(FRAGEMENT_IDENTITY,FRAGMENT_PERDATA);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part5");
        attachListenerOnFragment(mFragment);
        implicitlyListenerComposite.registerListener(implictly);

        bundle = new Bundle();
        mFragment = new Global();
        bundle.putInt(FRAGEMENT_IDENTITY,FRAGMENT_PENGADUAN);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part6");
        attachListenerOnFragment(mFragment);
        implicitlyListenerComposite.registerListener(implictly);
        viewPager.setAdapter(adapter);
    }

    void attachListenerOnFragment(Global fragment){
        try {
            implictly = fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(this.toString()
                    + " Needs to implement the methods");
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_CODE_PENGADUAN){

                progressbar.setVisibility(View.VISIBLE);
                String filepath = data.getStringExtra(COMPOSE_ATTACHMENT);
                MultipartBody.Part body = null;

                if(!filepath.isEmpty()) {
                    File file = new File(filepath);
                    final RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), file);
                    body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
                }

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
                            implicitlyListenerComposite.onAddActivityResponse();
                            Snackbar.make(getCurrentFocus(), response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                            progressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ApiDefaultResponse> call, Throwable t) {
                        if (t instanceof IOException) {
                            Log.d("CALLOF", call.toString() + "/" + call.request());
                            Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressbar.setVisibility(View.GONE);
                    }
                });
            }
            else if(resultCode == RESULT_CODE_PERDATA){

                progressbar.setVisibility(View.VISIBLE);
                PerdataItem perdataItem = data.getParcelableExtra(PARCELABLE_GANTI_ORANG);

                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("key", createPartFromString(BuildConfig.API_KEY));
                map.put("id", createPartFromString(String.valueOf(userPreference.getID())));
                map.put("email", createPartFromString(userPreference.getEmail()));
                map.put("permintaan", createPartFromString(perdataItem.getPermintaan()));
                map.put("tujuan", createPartFromString(perdataItem.getTujuan()));
                map.put("cara", createPartFromString(perdataItem.getCara()));

                /* Using Catch Error for Perdata Pribadi*/

                try{
                    map.put("nama2", createPartFromString(perdataItem.getNama2()));
                    map.put("gender2", createPartFromString(perdataItem.getGender2()));
                    map.put("tmptlhr2", createPartFromString(perdataItem.getTmptlhr2()));
                    map.put("tgllhr2", createPartFromString(perdataItem.getTgllhr2()));
                    map.put("alamat2", createPartFromString(perdataItem.getAlamat2()));
                    map.put("identitas2", createPartFromString(perdataItem.getIdentitas2()));
                    map.put("noIdentitas2", createPartFromString(perdataItem.getNoIdentitas2()));
                    map.put("telp2", createPartFromString(perdataItem.getTelp2()));
                    map.put("email2", createPartFromString(perdataItem.getEmail2()));
                } catch (Exception e){
                    map.put("nama2", createPartFromString(userPreference.getNama()));
                    map.put("gender2", createPartFromString(userPreference.getJenisKelamin()));
                    map.put("tmptlhr2", createPartFromString(userPreference.getTempatLahir()));
                    map.put("tgllhr2", createPartFromString(userPreference.getTanggalLahir()));
                    map.put("alamat2", createPartFromString(userPreference.getAlamat()));
                    map.put("identitas2", createPartFromString(userPreference.getIdentitas()));
                    map.put("noIdentitas2", createPartFromString(userPreference.getNoIdentitas()));
                    map.put("telp2", createPartFromString(userPreference.getTelp()));
                    map.put("email2", createPartFromString(userPreference.getEmail()));
                }

                Call<ApiDefaultResponse> call = api.postPerdata(map);
                call.enqueue(new Callback<ApiDefaultResponse>() {
                    @Override
                    public void onResponse(Call<ApiDefaultResponse> call, Response<ApiDefaultResponse> response) {
                        if(response.isSuccessful()){
                            implicitlyListenerComposite.onAddActivityResponse();
                            Snackbar.make(getCurrentFocus(), response.body().getMsg(), Snackbar.LENGTH_SHORT).show();
                        }
                        progressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ApiDefaultResponse> call, Throwable t) {
                        if (t instanceof IOException) {
                            Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressbar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }


    private File createTempFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() +"_image.webp");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP,0, bos);
        byte[] bitmapdata = bos.toByteArray();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
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

    @Override
    public void onErrorFieldResponses(int FragmentIdentifier) {
        //empty needed
    }

    /**
     *
     *  Listener Dialog Ganti
     *
     * @param password
     */

    @Override
    public void onPasswordConfirmed(String password) {

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("key", createPartFromString(BuildConfig.API_KEY));
        map.put("id", createPartFromString(String.valueOf(userPreference.getID())));
        map.put("password_baru", createPartFromString(password));

        Call<ApiDefaultResponse> call = api.postGantiPassword(map);
        progressbar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ApiDefaultResponse>() {
            @Override
            public void onResponse(Call<ApiDefaultResponse> call, Response<ApiDefaultResponse> response) {
                if(response.isSuccessful()){
                    Snackbar.make(getCurrentFocus(), response.body().getMsg(), Snackbar.LENGTH_SHORT).show();
                }
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ApiDefaultResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressbar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onIdentityUpdateConfirmed(UserData data) {

        MultipartBody.Part body = null;

        if(data.getBitmap()!=null) {
            File file = createTempFile(data.getBitmap());
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        }

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("key", createPartFromString(BuildConfig.API_KEY));
        map.put("id", createPartFromString(String.valueOf(userPreference.getID())));
        map.put("identitas_baru", createPartFromString(data.getIdentitas()));
        map.put("no_identitas_baru", createPartFromString(data.getNoIdentitas()));

        Call<ApiDefaultResponse> call = api.postGantiIdentitas(map,body);
        progressbar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ApiDefaultResponse>() {
            @Override
            public void onResponse(Call<ApiDefaultResponse> call, Response<ApiDefaultResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus()) {
                        onSharedPreferenceUpdate();
                        Snackbar.make(getCurrentFocus(), response.body().getMsg(), Snackbar.LENGTH_SHORT).show();
                    }
                }
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ApiDefaultResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onProfileUpdateConfirmed(UserData data){
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("key", createPartFromString(BuildConfig.API_KEY));
        map.put("id", createPartFromString(String.valueOf(userPreference.getID())));
        map.put("alamat_baru", createPartFromString(data.getAlamat()));
        map.put("nama_baru", createPartFromString(data.getNama()));
        map.put("jk_baru", createPartFromString(data.getJk()));
        map.put("tmptLhr_baru", createPartFromString(data.getTmptLhr()));
        map.put("tglLhr_baru", createPartFromString(data.getTglLhr()));
        map.put("telp_baru", createPartFromString(data.getTelp()));

        Call<ApiDefaultResponse> call = api.postGantiProfile(map);
        progressbar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ApiDefaultResponse>() {
            @Override
            public void onResponse(Call<ApiDefaultResponse> call, Response<ApiDefaultResponse> response) {
                if (response.isSuccessful()) {
                    if(response.body().getStatus()) {
                        onSharedPreferenceUpdate();
                        Snackbar.make(getCurrentFocus(), response.body().getMsg(), Snackbar.LENGTH_SHORT).show();
                    }
                }
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ApiDefaultResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    public void onSharedPreferenceUpdate(){
        Call<ApiResponseUser<UserData>> call = api.getLogin(BuildConfig.API_KEY, userPreference.getEmail(), userPreference.getPassword());
        progressbar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ApiResponseUser<UserData>>() {
            @Override
            public void onResponse(Call<ApiResponseUser<UserData>> call, Response<ApiResponseUser<UserData>> response) {
                if (response.isSuccessful()) {
                    userPreference.setPreference(response.body().getAccount());
                    implicitlyListenerComposite.onUpdateSharedPreference();
                }
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ApiResponseUser<UserData>> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressbar.setVisibility(View.GONE);
            }
        });
    }
}
