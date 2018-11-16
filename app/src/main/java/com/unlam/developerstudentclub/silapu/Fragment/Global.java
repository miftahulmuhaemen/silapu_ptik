package com.unlam.developerstudentclub.silapu.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.unlam.developerstudentclub.silapu.API.ApiGenerator;
import com.unlam.developerstudentclub.silapu.API.ApiInterface;
import com.unlam.developerstudentclub.silapu.API.ApiResponseData;
import com.unlam.developerstudentclub.silapu.Adapter.RecyclerViewAdapter;
import com.unlam.developerstudentclub.silapu.AddActivity;
import com.unlam.developerstudentclub.silapu.Box.App;
import com.unlam.developerstudentclub.silapu.BuildConfig;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.Entity.UserData;
import com.unlam.developerstudentclub.silapu.R;
import com.unlam.developerstudentclub.silapu.Utils.Implictly;
import com.unlam.developerstudentclub.silapu.Utils.NpaLiniearLayoutManager;
import com.unlam.developerstudentclub.silapu.Utils.UserPreference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.objectbox.Box;
import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_CODE;
import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_PERDATA;
import static com.unlam.developerstudentclub.silapu.Utils.Util.COMPOSE_PERDATA_PRIBADI;
import static com.unlam.developerstudentclub.silapu.Utils.Util.ERROR_FIELD_EMAIL_NOTVALID;
import static com.unlam.developerstudentclub.silapu.Utils.Util.ERROR_FIELD_KOSONG;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGEMENT_IDENTITY;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_PENGADUAN;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_PERDATA;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_REGISTER_FIRST;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_REGISTER_FORTH;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_REGISTER_SECOND;
import static com.unlam.developerstudentclub.silapu.Utils.Util.FRAGMENT_REGISTER_THIRD;
import static com.unlam.developerstudentclub.silapu.Utils.Util.REQUEST_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class  Global extends Fragment implements Implictly {

    /*Fragment Regist*/
    @Nullable @BindView(R.id.edt_email) ExtendedEditText edt_email;
    @Nullable @BindView(R.id.edt_alamat) ExtendedEditText edt_alamat;
    @Nullable @BindView(R.id.edt_nama) ExtendedEditText edt_nama;
    @Nullable @BindView(R.id.edt_password) ExtendedEditText edt_password;
    @Nullable @BindView(R.id.edt_numberPhone) ExtendedEditText edt_phoneNumber;
    @Nullable @BindView(R.id.edt_kotaLahir) ExtendedEditText edt_kotaLahir;
    @Nullable @BindView(R.id.edt_tanggalLahir) ExtendedEditText edt_tanggalLahir;
    @Nullable @BindView(R.id.edt_nomorIdentitas) ExtendedEditText edt_nomorIdentitas;

    @Nullable @BindView(R.id.ti_password) TextFieldBoxes ti_password;
    @Nullable @BindView(R.id.ti_email)  TextFieldBoxes ti_email;
    @Nullable @BindView(R.id.ti_nama)  TextFieldBoxes ti_nama;
    @Nullable @BindView(R.id.ti_phone)  TextFieldBoxes ti_phone;
    @Nullable @BindView(R.id.ti_alamat)  TextFieldBoxes ti_alamat;
    @Nullable @BindView(R.id.ti_kotaLahir)  TextFieldBoxes ti_kotaLahir;
    @Nullable @BindView(R.id.ti_tanggalLahir)  TextFieldBoxes ti_tanggalLahir;
    @Nullable @BindView(R.id.ti_idt) TextFieldBoxes ti_noIdentitas;

    @Nullable @BindView(R.id.ti_jenisKelamin)   MaterialSpinner spinner_jenisKelamin;
    @Nullable @BindView(R.id.ti_identity)   MaterialSpinner spinner_identityCard;

    @Nullable @BindView(R.id.btn_galeri)    Button btn_galeri;
    @Nullable @BindView(R.id.plate_img)  CircleImageView plate_img;

    /*Fragment Main*/
    @Nullable @BindView(R.id.btn_add_item) FloatingActionButton btn_add;
    @Nullable @BindView(R.id.recylerview) RecyclerView recyclerView;
    RecyclerViewAdapter rvAdapter;
    final Calendar myCalendar = Calendar.getInstance();

    private UserPreference userPreference;
    ApiInterface api = ApiGenerator.createService(ApiInterface.class);
    private Box<PengaduanItem> pengaduanItemBox;
    private Box<PerdataItem> perdataItemBox;

    String filepath = "";

    @Getter @Setter
    onCompleteResponse Responses;

    public interface onCompleteResponse {
        void onCompleteFormResponse(UserData data, int FragmentIdentifier);
        void onErrorFieldResponses(int FragmentIdentifier);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            try {
                Responses = (onCompleteResponse) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement onCompleteResponse");
            }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Responses = null;
    }

    public Global() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        final View view;
        final int CHECK = getArguments().getInt(FRAGEMENT_IDENTITY,0);

        switch (CHECK){
            case FRAGMENT_REGISTER_FIRST :
                        view = inflater.inflate(R.layout.frag_regist1st, container, false);
                        ButterKnife.bind(this,view);
                        ti_password.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                edt_password.setInputType(InputType.TYPE_CLASS_TEXT);
                            }
                        });
                        break;
            case FRAGMENT_REGISTER_SECOND :
                        view = inflater.inflate(R.layout.frag_regist2nd,container,false);
                        ButterKnife.bind(this,view);
                        edt_tanggalLahir.setEnabled(false);
                        ti_tanggalLahir.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                          int dayOfMonth) {
                                        myCalendar.set(Calendar.YEAR, year);
                                        myCalendar.set(Calendar.MONTH, monthOfYear);
                                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                        updateLabel();
                                    }
                                };
                                new DatePickerDialog(getActivity(), date, myCalendar
                                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                            }
                        });
                        spinner_jenisKelamin.setItems(getResources().getStringArray(R.array.jeniskelamin));
                        spinner_identityCard.setItems(getResources().getStringArray(R.array.identitas));
                        break;
            case FRAGMENT_REGISTER_THIRD :
                        view = inflater.inflate(R.layout.frag_regist3rd, container, false);
                        ButterKnife.bind(this,view);
                        btn_galeri.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new ChooserDialog(getActivity())
                                        .withFilter(false, false, "jpg", "jpeg", "png")
                                        .withChosenListener(new ChooserDialog.Result() {
                                            @Override
                                            public void onChoosePath(String path, File pathFile) {
                                                filepath = path;
                                                Glide.with(getContext()).load(pathFile).into(plate_img);
                                                Toast.makeText(getContext(), "FILE: " + path, Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .build()
                                        .show();
                            }
                        });
                        break;
            case FRAGMENT_REGISTER_FORTH :
                        view = inflater.inflate(R.layout.frag_regist4th, container, false);
                        ButterKnife.bind(this,view);
                        break;
            case FRAGMENT_PENGADUAN :
            case FRAGMENT_PERDATA :
                        view =  inflater.inflate(R.layout.recylerview,container,false);
                        ButterKnife.bind(this,view);
                        userPreference = new UserPreference(getContext());
                        pengaduanItemBox = ((App) getActivity().getApplication()).getBoxStore().boxFor(PengaduanItem.class);
                        perdataItemBox = ((App) getActivity().getApplication()).getBoxStore().boxFor(PerdataItem.class);
                        FragmentPengaduanAndPerdataMethod(CHECK);
                        break;

            default: view =  null;
                        break;
        }
        return view;
    }

    /**
     *  Register Activity FRAGMENT
     *  Use EVENT BUS next time.
     * @param text
     */

    @Override
    public void onRegisterActivityResponse(Boolean text) {
        final int Fragment = getArguments().getInt(FRAGEMENT_IDENTITY,0);
        boolean isComplete = true;
        UserData data = new UserData();

        switch (Fragment){
            case FRAGMENT_REGISTER_FIRST :
                String email = edt_email.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                String alamat = edt_alamat.getText().toString().trim();
                String nama = edt_nama.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    isComplete = false;
                    ti_email.setError(ERROR_FIELD_KOSONG,false);
                } else if(!isValidEmail(email)){
                    isComplete = false;
                    ti_email.setError(ERROR_FIELD_EMAIL_NOTVALID,true);
                }

                if(TextUtils.isEmpty(password)){
                    isComplete = false;
                }

                if(TextUtils.isEmpty(alamat)){
                    isComplete = false;
                    ti_alamat.setError(ERROR_FIELD_KOSONG,false);
                }

                if(TextUtils.isEmpty(nama)){
                    isComplete = false;
                    ti_nama.setError(ERROR_FIELD_KOSONG,false);
                }

                if(isComplete){
                    data.setEmail(email);
                    data.setPassword(password);
                    data.setAlamat(alamat);
                    data.setNama(nama);
                }

                break;

            case FRAGMENT_REGISTER_SECOND :
                String kotaLahir = edt_kotaLahir.getText().toString().trim();
                String tanggalLahir = edt_tanggalLahir.getText().toString().trim();
                String jenisKelamin = spinner_jenisKelamin.getText().toString().trim();
                String identityCard = spinner_identityCard.getText().toString().trim();
                String noIdentity = edt_nomorIdentitas.getText().toString().trim();
                String phone = edt_phoneNumber.getText().toString().trim();

                if(TextUtils.isEmpty(kotaLahir)){
                    isComplete = false;
                    ti_kotaLahir.setError(ERROR_FIELD_KOSONG,true);
                }

                if(TextUtils.isEmpty(phone)){
                    isComplete = false;
                }

                if(TextUtils.isEmpty(tanggalLahir)){
                    isComplete = false;
                    ti_tanggalLahir.setError(ERROR_FIELD_KOSONG, false);
                }

                if(TextUtils.isEmpty(noIdentity)){
                    isComplete = false;
                    ti_noIdentitas.setError(ERROR_FIELD_KOSONG,false);
                }
                if(isComplete){
                    data.setTmptLhr(kotaLahir);
                    data.setTglLhr(tanggalLahir);
                    data.setNoIdentitas(noIdentity);
                    data.setTelp(phone);
                    data.setJk(jenisKelamin);
                    data.setIdentitas(identityCard);
                }
                break;
            case FRAGMENT_REGISTER_THIRD :
                if(filepath.isEmpty()){
                    isComplete = false;
                }
                else
                    data.setFilepath(filepath);
                break;
        }

        if(isComplete){
            getResponses().onCompleteFormResponse(data, Fragment);
        }

        getResponses().onErrorFieldResponses(Fragment);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        edt_tanggalLahir.setText(sdf.format(myCalendar.getTime()));
    }

    /*Double Method in LoginActivity, needs to be simplified later*/
    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     *
     * MainActivity.class Fragment Transaction
     *
     * @param Fragment
     */

    public void onThrowToBox(int Fragment){
        if(Fragment == FRAGMENT_PENGADUAN){
            Call<ApiResponseData<PengaduanItem>> call = api.getPengaduan(BuildConfig.API_KEY, userPreference.getID());
            call.enqueue(new Callback<ApiResponseData<PengaduanItem>>() {
                @Override
                public void onResponse(Call<ApiResponseData<PengaduanItem>> call, Response<ApiResponseData<PengaduanItem>> response) {
                    pengaduanItemBox.put(response.body().getData());
                    List<PengaduanItem> item = pengaduanItemBox.getAll();

                    if(!item.isEmpty()){
                        rvAdapter.setFilteredPengaduanItem((ArrayList<PengaduanItem>) item);
                        rvAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponseData<PengaduanItem>> call, Throwable t) {
                    if (t instanceof IOException) {
                        Toast.makeText(getActivity(), "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                        List<PengaduanItem> item = pengaduanItemBox.getAll();
                        if(!item.isEmpty()){
                            rvAdapter.setFilteredPengaduanItem((ArrayList<PengaduanItem>) item);
                            rvAdapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                        List<PengaduanItem> item = pengaduanItemBox.getAll();
                        if(!item.isEmpty()){
                            rvAdapter.setFilteredPengaduanItem((ArrayList<PengaduanItem>) item);
                            rvAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });

        } else if(Fragment == FRAGMENT_PERDATA){

            Call<ApiResponseData<PerdataItem>> call = api.getPerdata(BuildConfig.API_KEY,userPreference.getID());
            call.enqueue(new Callback<ApiResponseData<PerdataItem>>() {
                @Override
                public void onResponse(Call<ApiResponseData<PerdataItem>> call, Response<ApiResponseData<PerdataItem>> response) {
                    perdataItemBox.put(response.body().getData());
                    List<PerdataItem> item = perdataItemBox.getAll();

                    if(!item.isEmpty()) {
                        rvAdapter.setFilteredPerdataItem((ArrayList<PerdataItem>) item);
                        rvAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponseData<PerdataItem>> call, Throwable t) {
                    if (t instanceof IOException) {
                        Toast.makeText(getActivity(), "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                        List<PerdataItem> item = perdataItemBox.getAll();
                        if(!item.isEmpty()) {
                            rvAdapter.setFilteredPerdataItem((ArrayList<PerdataItem>) item);
                            rvAdapter.notifyDataSetChanged();
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                        List<PerdataItem> item = perdataItemBox.getAll();
                        if(!item.isEmpty()) {
                            rvAdapter.setFilteredPerdataItem((ArrayList<PerdataItem>) item);
                            rvAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onAddActivityResponse() {
        onThrowToBox(getArguments().getInt(FRAGEMENT_IDENTITY,0));
    }

    @Override
    public void onUpdateSharedPreference() {
        Log.d("NOTIFY", "EN");
        rvAdapter.notifyDataSetChanged();
    }

    private void RecyclerViewAdapterConnect(int Fragment){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new NpaLiniearLayoutManager(getActivity()));
        rvAdapter = new RecyclerViewAdapter(getContext(),Fragment);

        if(Fragment == FRAGMENT_PENGADUAN){
            ArrayList<PengaduanItem> items = new ArrayList<>();
            rvAdapter.setFilteredPengaduanItem(items);
            onThrowToBox(Fragment);
        } else if(Fragment == FRAGMENT_PERDATA){
            ArrayList<PerdataItem> items = new ArrayList<>();
            rvAdapter.setFilteredPerdataItem(items);
            onThrowToBox(Fragment);
        }

        recyclerView.setAdapter(rvAdapter);
    }

    private void FragmentPengaduanAndPerdataMethod(final int Fragment){
        RecyclerViewAdapterConnect(Fragment);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Intent intent = new Intent(getActivity(),AddActivity.class);

                if(Fragment == FRAGMENT_PENGADUAN) {
                    intent.putExtra(COMPOSE_CODE, COMPOSE_PENGADUAN);
                    getActivity().startActivityForResult(intent,REQUEST_CODE);
                }

                else if(Fragment == FRAGMENT_PERDATA) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder .setTitle(R.string.title_dialog_perdata)
                            .setItems(R.array.add_perdata_array, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(i == 0)
                                intent.putExtra(COMPOSE_CODE,COMPOSE_PERDATA_PRIBADI);
                            else if(i == 1)
                                intent.putExtra(COMPOSE_CODE,COMPOSE_PERDATA);

                            getActivity().startActivityForResult(intent,REQUEST_CODE);
                        }
                    }).show();
                }

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if (!recyclerView.canScrollVertically(1))
//                    btn_add.setVisibility(View.INVISIBLE);
//                else
//                    btn_add.setVisibility(View.VISIBLE);
            }
        });
    }

}
