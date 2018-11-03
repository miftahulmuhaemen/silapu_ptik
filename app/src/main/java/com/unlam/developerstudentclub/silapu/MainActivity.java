package com.unlam.developerstudentclub.silapu;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.unlam.developerstudentclub.silapu.API.ApiDefaultResponse;
import com.unlam.developerstudentclub.silapu.API.ApiGenerator;
import com.unlam.developerstudentclub.silapu.API.ApiInterface;
import com.unlam.developerstudentclub.silapu.Adapter.FragementAdapter;
import com.unlam.developerstudentclub.silapu.Box.App;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.Entity.UserData;
import com.unlam.developerstudentclub.silapu.Fragment.Confirmation;
import com.unlam.developerstudentclub.silapu.Fragment.Global;
import com.unlam.developerstudentclub.silapu.Utils.ImplicitlyListenerComposite;
import com.unlam.developerstudentclub.silapu.Utils.Implictly;
import com.unlam.developerstudentclub.silapu.Utils.LockableViewPager;
import com.unlam.developerstudentclub.silapu.Utils.UserPreference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;
import io.objectbox.Box;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_ATTACHMENT;
import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_MESSAGE;
import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_SPINNER;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGEMENT_IDENTITY;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_CLOSE;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_CONFIRM;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_PERDATA;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_PROFIL;
import static com.unlam.developerstudentclub.silapu.Utils.Util.REQUEST_CODE;
import static com.unlam.developerstudentclub.silapu.Utils.Util.RESULT_CODE_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.Utils.Util.RESULT_CODE_PERDATA;


public class MainActivity extends AppCompatActivity implements Global.onCompleteResponse {

    @Nullable
    @BindView(R.id.viewpager)
    LockableViewPager viewPager;

    @Nullable
    @BindView(R.id.ntb)
    NavigationTabBar navigationTabBar;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    ApiInterface api = ApiGenerator.createService(ApiInterface.class); // Interface Retrofit
    private UserPreference userPreference;
    private Box<PengaduanItem> pengaduanItemBox;
    private Box<PerdataItem> perdataItemBox;

    Implictly implictly; // Interface Composite GLOBAL_FRAGMENT
    ImplicitlyListenerComposite implicitlyListenerComposite = new ImplicitlyListenerComposite(); // Composite Listener


//    @Override
//    public void onAttachFragment(Fragment fragment) {
//        super.onAttachFragment(fragment);
//        try {
//            response = (onAddActivityResponse) fragment;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(this.toString()
//                    + " Needs to implement the methods");
//        }
//    }

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
        menuInflater.inflate(R.menu.logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout :
                Confirmation confirmationDialog = new Confirmation();
                Bundle bundle = new Bundle();
                bundle.putInt(FRAGMENT_CONFIRM,FRAGMENT_CLOSE);
                confirmationDialog.setArguments(bundle);
                confirmationDialog.setOnOptionDialogListener(new Confirmation.OnOptionDialogListener() {
                    @Override
                    public void onOptionChoosen(Boolean text) {
                        if(text){
                            userPreference.clearePreference();
                            pengaduanItemBox.removeAll();
                            perdataItemBox.removeAll();
                            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                FragmentManager fragmentManager = getSupportFragmentManager();
                confirmationDialog.show(fragmentManager,Confirmation.class.getSimpleName());
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
                getResources().getDrawable(R.drawable.ic_pan_tool_black_24dp),
                R.color.White)
                .title("Pengaduan")
                .build());

//        models.add(new NavigationTabBar.Model.Builder(
//                getResources().getDrawable(R.drawable.ic_person_outline_black_24dp),
//                R.color.White)
//                .selectedIcon(getResources().getDrawable(R.drawable.ic_person_black_24dp))
//                .title("Profil")
//                .build());

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

//        bundle = new Bundle();
//        mFragment = new Global();
//        bundle.putInt(FRAGEMENT_IDENTITY,FRAGMENT_PROFIL);
//        mFragment.setArguments(bundle);
//        adapter.addFragment(mFragment, "Part3");

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_CODE_PENGADUAN){

                File file = new File(data.getStringExtra(COMPOSE_ATTACHMENT));
                final RequestBody reqFile = RequestBody.create(MediaType.parse("*/*"), file);
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
                            implicitlyListenerComposite.onAddActivityResponse();
                            Snackbar.make(getCurrentFocus(), response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ApiDefaultResponse> call, Throwable t) {
                        if (t instanceof IOException) {
                            implicitlyListenerComposite.onAddActivityResponse();
                            Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else if(resultCode == RESULT_CODE_PERDATA){
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
