package com.unlam.developerstudentclub.silapu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rd.PageIndicatorView;
import com.unlam.developerstudentclub.silapu.API.ApiGenerator;
import com.unlam.developerstudentclub.silapu.API.ApiInterface;
import com.unlam.developerstudentclub.silapu.API.ApiResponseUser;
import com.unlam.developerstudentclub.silapu.Adapter.FragementAdapter;
import com.unlam.developerstudentclub.silapu.Entity.UserData;
import com.unlam.developerstudentclub.silapu.Fragment.Confirmation;
import com.unlam.developerstudentclub.silapu.Fragment.Global;
import com.unlam.developerstudentclub.silapu.Utils.ImplicitlyListenerComposite;
import com.unlam.developerstudentclub.silapu.Utils.Implictly;
import com.unlam.developerstudentclub.silapu.Utils.LockableViewPager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.unlam.developerstudentclub.silapu.Fragment.Global.FRAGMENT_REGISTER_FIRST;
import static com.unlam.developerstudentclub.silapu.Fragment.Global.FRAGMENT_REGISTER_FORTH;
import static com.unlam.developerstudentclub.silapu.Fragment.Global.FRAGMENT_REGISTER_SECOND;
import static com.unlam.developerstudentclub.silapu.Fragment.Global.FRAGMENT_REGISTER_THIRD;

public class RegisterActivity extends AppCompatActivity implements Implictly, Global.onCompleteResponse {

    public static Integer REQUEST_CODE_REGISTER = 110;

    @BindView(R.id.viewpager)
    LockableViewPager viewPager;

    @BindView(R.id.fab_left)
    FloatingActionButton fab_left;

    @BindView(R.id.fab_right)
    FloatingActionButton fab_right;

    @BindView(R.id.btn_done)
    Button btn_done;

    @BindView(R.id.btn_masuk)
    Button btn_masuk;

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;


    /* Validation of Each Global Fragment to the Upload Session */
    boolean FRAGMENT_firstSeal = false;
    boolean FRAGMENT_secondSeal = false;
    boolean FRAGMENT_thirdSeal = false;

    UserData form = new UserData();
    Bitmap bitmap; //File Image Upload and Plate Image Source Value
    Implictly implictly; // Interface Composite GLOBAL_FRAGMENT
    ImplicitlyListenerComposite implicitlyListenerComposite = new ImplicitlyListenerComposite(); // Composite Listener
    ApiInterface api = ApiGenerator.createService(ApiInterface.class); // Interface Retrofit


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setupViewPager(viewPager);

        viewPager.setSwipeable(false);
        viewPager.setOffscreenPageLimit(4);
        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fab_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() == 1)
                    fab_left.setVisibility(View.INVISIBLE);

                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        fab_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentPage = viewPager.getCurrentItem();
                int totalPage = viewPager.getAdapter().getCount();

                if(currentPage == totalPage - 2){
                    Confirmation confirmationDialog = new Confirmation();
                    confirmationDialog.setOnOptionDialogListener(new Confirmation.OnOptionDialogListener() {
                        @Override
                        public void onOptionChoosen(Boolean text) {
                            if(text){
                                implicitlyListenerComposite.onRegisterActivityResponse(true);
                            }
                        }
                    });
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    confirmationDialog.show(fragmentManager,Confirmation.class.getSimpleName());
                }
                else{
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    fab_left.setVisibility(View.VISIBLE);
                }

            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_REGISTER){
            if(resultCode == RESULT_OK){
                bitmap = getDecodedImageFromUri(data.getData());
                ImageView imageView = findViewById(R.id.plate_img);
                Glide.with(this)
                        .load(bitmap)
                        .into(imageView);
            }
        }
    }

    private Bitmap getDecodedImageFromUri(Uri uri) {
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Rect rect = new Rect(0, 0, 0, 0);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, rect, options); //HERE IS PROBLEM - bitmap = null.
        return bitmap;
    }

    private void setupViewPager(ViewPager viewPager) {
        FragementAdapter adapter = new FragementAdapter(getSupportFragmentManager());
        Global mFragment = new Global();
        Bundle bundle = new Bundle();

        bundle.putInt(Global.FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_FIRST);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part1");
        attachListenerOnFragment(mFragment);
        implicitlyListenerComposite.registerListener(implictly);

        bundle = new Bundle();
        mFragment = new Global();
        bundle.putInt(Global.FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_SECOND);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part2");
        attachListenerOnFragment(mFragment);
        implicitlyListenerComposite.registerListener(implictly);

        bundle = new Bundle();
        mFragment = new Global();
        bundle.putInt(Global.FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_THIRD);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part3");
        attachListenerOnFragment(mFragment);
        implicitlyListenerComposite.registerListener(implictly);

        bundle = new Bundle();
        mFragment = new Global();
        bundle.putInt(Global.FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_FORTH);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part4");

        viewPager.setAdapter(adapter);
    }

    void attachListenerOnFragment(Global fragment){
        try {
            implictly = (Implictly) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(this.toString()
                    + " Needs to implement the methods");
        }
    }

    @Override
    public void onRegisterActivityResponse(Boolean text) {
        //needs to be empty
    }

    @Override
    public void onCompleteFormResponse(UserData data, int Fragment) {

        if(Fragment == FRAGMENT_REGISTER_FIRST){
            form.setEmail(data.getEmail());
            form.setPassword(data.getPassword());
            form.setAlamat(data.getAlamat());
            form.setNama(data.getNama());
            FRAGMENT_firstSeal = true;
        }

        if(Fragment == FRAGMENT_REGISTER_SECOND){
            form.setTglLhr(data.getTglLhr());
            form.setTmptLhr(data.getTmptLhr());
            form.setIdentitas(data.getIdentitas());
            form.setNoIdentitas(data.getNoIdentitas());
            form.setTelp(data.getTelp());
            form.setJk(data.getJk());
            FRAGMENT_secondSeal = true;
        }

        if(Fragment == FRAGMENT_REGISTER_THIRD){
            FRAGMENT_thirdSeal = true;
        }

        if(FRAGMENT_firstSeal && FRAGMENT_secondSeal && FRAGMENT_thirdSeal){
            File file = createTempFile(bitmap);
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);

            RequestBody key = createPartFromString(BuildConfig.API_KEY);
            RequestBody email = createPartFromString(form.getEmail());
            RequestBody password = createPartFromString(form.getPassword());
            RequestBody alamat = createPartFromString(form.getAlamat());
            RequestBody nama = createPartFromString(form.getNama());
            RequestBody tanggal_lahir = createPartFromString(form.getTglLhr());
            RequestBody tempat_lahir = createPartFromString(form.getTmptLhr());
            RequestBody identitas = createPartFromString(form.getIdentitas());
            RequestBody no_identitas = createPartFromString(form.getNoIdentitas());
            RequestBody telp = createPartFromString(form.getTelp());
            RequestBody jk = createPartFromString(form.getJk());

            HashMap<String, RequestBody> map = new HashMap<>();
            map.put("email",email);
            map.put("password",password);
            map.put("alamat", alamat);
            map.put("nama", nama);
            map.put("identitas", identitas);
            map.put("no_identitas", no_identitas);
            map.put("jk", jk);
            map.put("tanggal_lahir", tanggal_lahir);
            map.put("tempat_lahir", tempat_lahir);
            map.put("telp", telp);
            map.put("key", key);

            Call<ApiResponseUser<UserData>> call = api.postRegister(map,body);
            call.enqueue(new Callback<ApiResponseUser<UserData>>() {
                @Override
                public void onResponse(Call<ApiResponseUser<UserData>> call, Response<ApiResponseUser<UserData>> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus() == true) {
                            fab_left.setVisibility(View.INVISIBLE);
                            fab_right.setVisibility(View.INVISIBLE);
                            btn_done.setVisibility(View.VISIBLE);
                            btn_masuk.setVisibility(View.INVISIBLE);
                            pageIndicatorView.setVisibility(View.INVISIBLE);
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else {
                            Snackbar.make(getCurrentFocus(), response.body().getMsg(), Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(getCurrentFocus(), response.code() + "- Terjadi Masalah.", Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponseUser<UserData>> call, Throwable t) {
                    Snackbar.make(getCurrentFocus(), "Koneksi kemungkinan bermasalah", Snackbar.LENGTH_LONG).show();
                }
            });
        } else {
//            Snackbar.make(getCurrentFocus(), "Data Tidak Lengkap.", Snackbar.LENGTH_LONG).show();
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
    protected void onDestroy() {
        super.onDestroy();
        implicitlyListenerComposite.removeListener();
    }
}
