package com.unlam.developerstudentclub.silapu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.unlam.developerstudentclub.silapu.Fragment.DetailItem;
import com.unlam.developerstudentclub.silapu.Fragment.GantiOrang;
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
import static com.unlam.developerstudentclub.silapu.Utils.Util.DIALOG_GANTI_PASSWORD;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGEMENT_IDENTITY;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_PERDATA;
import static com.unlam.developerstudentclub.silapu.Utils.Util.PARCELABLE_GANTI_ORANG;
import static com.unlam.developerstudentclub.silapu.Utils.Util.REQUEST_CODE;
import static com.unlam.developerstudentclub.silapu.Utils.Util.RESULT_CODE_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.Utils.Util.RESULT_CODE_PERDATA;


public class MainActivity extends AppCompatActivity implements Global.onCompleteResponse, DetailItem.NoticeDialogListenerGantiPassword {

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
        switch (item.getItemId()){
            case R.id.menu :
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setItems(R.array.overflowmenu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :

                                break;
                            case 1 :
                                DetailItem detailItem = new DetailItem();
                                Bundle bundle = new Bundle();
                                bundle.putString(DIALOG_DETIL, DIALOG_GANTI_PASSWORD);
                                detailItem.setArguments(bundle);
                                detailItem.show(getFragmentManager(),DetailItem.class.getSimpleName());
                                break;
                            case 2 :
                                userPreference.clearePreference();
                                pengaduanItemBox.removeAll();
                                perdataItemBox.removeAll();
                                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                            }});
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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
                            Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else if(resultCode == RESULT_CODE_PERDATA){

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
                            Snackbar.make(getCurrentFocus(), response.body().getMsg(), Snackbar.LENGTH_LONG).show();
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

    /**
     *
     *  Ganti Password Response
     *
     * @param password
     */

    @Override
    public void onDialogPositiveClick(String password) {

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("key", createPartFromString(BuildConfig.API_KEY));
        map.put("id", createPartFromString(String.valueOf(userPreference.getID())));
        map.put("password_baru", createPartFromString(password));

        Call<ApiDefaultResponse> call = api.postGantiPassword(map);
        call.enqueue(new Callback<ApiDefaultResponse>() {
            @Override
            public void onResponse(Call<ApiDefaultResponse> call, Response<ApiDefaultResponse> response) {
                if(response.isSuccessful()){
                    Snackbar.make(getCurrentFocus(), response.body().getMsg(), Snackbar.LENGTH_LONG).show();
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

//
//    private void openDownloadedAttachment(final Context context, final long downloadId) {
//        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//        DownloadManager.Query query = new DownloadManager.Query();
//        query.setFilterById(downloadId);
//        Cursor cursor = downloadManager.query(query);
//        if (cursor.moveToFirst()) {
//            int downloadStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
//            String downloadLocalUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
//            String downloadMimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
//            if ((downloadStatus == DownloadManager.STATUS_SUCCESSFUL) && downloadLocalUri != null) {
//                openDownloadedAttachment(context, Uri.parse(downloadLocalUri), downloadMimeType);
//            }
//        }
//        cursor.close();
//    }
//
//    /**
//     * Used to open the downloaded attachment.
//     * <p/>
//     * 1. Fire intent to open download file using external application.
//     *
//     * 2. Note:
//     * 2.a. We can't share fileUri directly to other application (because we will get FileUriExposedException from Android7.0).
//     * 2.b. Hence we can only share content uri with other application.
//     * 2.c. We must have declared FileProvider in manifest.
//     * 2.c. Refer - https://developer.android.com/reference/android/support/v4/content/FileProvider.html
//     *
//     * @param context            Context.
//     * @param attachmentUri      Uri of the downloaded attachment to be opened.
//     * @param attachmentMimeType MimeType of the downloaded attachment.
//     */
//    private void openDownloadedAttachment(final Context context, Uri attachmentUri, final String attachmentMimeType) {
//        if(attachmentUri!=null) {
//            // Get Content Uri.
//            if (ContentResolver.SCHEME_FILE.equals(attachmentUri.getScheme())) {
//                // FileUri - Convert it to contentUri.
//                File file = new File(attachmentUri.getPath());
//                attachmentUri = FileProvider.getUriForFile(this, "com.freshdesk.helpdesk.provider", file);;
//            }
//
//            Intent openAttachmentIntent = new Intent(Intent.ACTION_VIEW);
//            openAttachmentIntent.setDataAndType(attachmentUri, attachmentMimeType);
//            openAttachmentIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            try {
//                context.startActivity(openAttachmentIntent);
//            } catch (ActivityNotFoundException e) {
//                Toast.makeText(context, "Ga Bisa Buka", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

//    BroadcastReceiver onComplete = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
//                long downloadId = intent.getLongExtra(
//                        DownloadManager.EXTRA_DOWNLOAD_ID, 0);
//                openDownloadedAttachment(context, downloadId);
//            }
//        }
//    };

}
