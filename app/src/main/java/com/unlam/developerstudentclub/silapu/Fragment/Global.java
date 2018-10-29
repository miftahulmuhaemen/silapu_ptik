package com.unlam.developerstudentclub.silapu.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.unlam.developerstudentclub.silapu.Adapter.RecyclerViewAdapter;
import com.unlam.developerstudentclub.silapu.AddActivity;
import com.unlam.developerstudentclub.silapu.Entity.DummyDataPengaduan;
import com.unlam.developerstudentclub.silapu.Entity.DummyDataPerdata;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.R;
import com.unlam.developerstudentclub.silapu.RegisterActivity;
import com.unlam.developerstudentclub.silapu.Utils.ImplicitlyListenerComposite;
import com.unlam.developerstudentclub.silapu.Utils.Implictly;
import com.unlam.developerstudentclub.silapu.Utils.NpaLiniearLayoutManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import lombok.Getter;
import lombok.Setter;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

import static android.support.constraint.Constraints.TAG;
import static com.unlam.developerstudentclub.silapu.LoginActivity.ERROR_FIELD_EMAIL_NOTVALID;
import static com.unlam.developerstudentclub.silapu.LoginActivity.ERROR_FIELD_KOSONG;

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

    @Nullable @BindView(R.id.btn_galeri) ImageView btn_galeri;
    @Nullable @BindView(R.id.plate_img)  CircleImageView plate_img;

    @Nullable @BindView(R.id.ti_jenisKelamin)   MaterialSpinner spinner_jenisKelamin;
    @Nullable @BindView(R.id.ti_identity)   MaterialSpinner spinner_identityCard;

    /*Fragment Main*/
    @Nullable @BindView(R.id.btn_add_item) FloatingActionButton btn_add;
    @Nullable @BindView(R.id.searchview) SearchView searchView;
    @Nullable @BindView(R.id.recylerview) RecyclerView recyclerView;
    @Nullable @BindView(R.id.setting) ImageView btn_setting;

    RecyclerViewAdapter rvAdapter;

    final public static int FRAGMENT_REGISTER_FIRST = 1;
    final public static int FRAGMENT_REGISTER_SECOND = 2;
    final public static int FRAGMENT_REGISTER_THIRD = 3;
    final public static int FRAGMENT_REGISTER_FORTH = 4;
    final public static int FRAGMENT_PENGADUAN = 5;
    final public static int FRAGMENT_PERDATA = 6;
    final public static int FRAGMENT_PROFIL = 7;

    public static String FRAGEMENT_IDENTITY = "identity";

    @Getter @Setter
    onCompleteResponse Responses;

    public interface onCompleteResponse {
        void onCompleteFormResponse(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            Activity register = new RegisterActivity();
            register.getComponentName().toString();
        } catch (Exception ex){
            try {
                Responses = (onCompleteResponse) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement onViewSelected");
            }
        }
    }

    public Global() {
        // Required empty public constructor
    }

    @Override
    public void onRegisterActivityResponse(Boolean text) {
        final int CHECK = getArguments().getInt(FRAGEMENT_IDENTITY,0);
        boolean isComplete = true;

        switch (CHECK){
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
                    ti_phone.setError(ERROR_FIELD_KOSONG, true);
                }

                break;
            case FRAGMENT_REGISTER_THIRD :
                break;
        }

        getResponses().onCompleteFormResponse(CHECK + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        final View view;
        final int CHECK = getArguments().getInt(FRAGEMENT_IDENTITY,0);

        switch (CHECK){
            case FRAGMENT_REGISTER_FIRST :
                        view = inflater.inflate(R.layout.frag_regist1st, container, false);
                        ButterKnife.bind(this,view);
                        break;
            case FRAGMENT_REGISTER_SECOND :
                        view = inflater.inflate(R.layout.frag_regist2nd,container,false);
                        ButterKnife.bind(this,view);
                        spinner_jenisKelamin.setItems(getResources().getStringArray(R.array.jeniskelamin));
                        spinner_identityCard.setItems(getResources().getStringArray(R.array.identitas));
                        break;
            case FRAGMENT_REGISTER_THIRD :
                        view = inflater.inflate(R.layout.frag_regist3rd, container, false);
                        ButterKnife.bind(this,view);
                        break;
            case FRAGMENT_REGISTER_FORTH :
                        view = inflater.inflate(R.layout.frag_regist4th, container, false);
                        ButterKnife.bind(this,view);
                        break;
            case FRAGMENT_PENGADUAN :
            case FRAGMENT_PERDATA :
                        view =  inflater.inflate(R.layout.recylerview,container,false);
                        ButterKnife.bind(this,view);
                        FragmentPengaduanAndPerdataMethod(CHECK);
                        break;
            case FRAGMENT_PROFIL :
                        view = inflater.inflate(R.layout.frag_profile,container,false);
                        ButterKnife.bind(this,view);
                        PopupMenu(view,btn_setting,R.menu.setting);
                        /*Fragement Pop Up*/
                        break;

            default: view =  null;
                        break;
        }
        return view;
    }

    /*Double Method in LoginActivity, needs to be simplified later*/
    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void RecyclerViewAdapterConnect(int Fragment){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new NpaLiniearLayoutManager(getActivity()));
        rvAdapter = new RecyclerViewAdapter(getContext(),Fragment);

        if(Fragment == FRAGMENT_PENGADUAN){
            ArrayList<PengaduanItem> items = new ArrayList<>();
            items.addAll(DummyDataPengaduan.getListData());
            rvAdapter.setFilteredPengaduanItem(items);
        } else if(Fragment == FRAGMENT_PERDATA){
            ArrayList<PerdataItem> items = new ArrayList<>();
            items.addAll(DummyDataPerdata.getListData());
            rvAdapter.setFilteredPerdataItem(items);
        }

        recyclerView.setAdapter(rvAdapter);
    }

    private void FragmentPengaduanAndPerdataMethod(final int Fragment){

        RecyclerViewAdapterConnect(Fragment);

        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                rvAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                rvAdapter.getFilter().filter(query);
                return false;
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                if(Fragment == FRAGMENT_PENGADUAN){
                    intent = new Intent(getActivity(),AddActivity.class);
                    intent.putExtra(AddActivity.COMPOSE_CODE, AddActivity.COMPOSE_PENGADUAN);
                } else if(Fragment == FRAGMENT_PERDATA) {
                    intent = new Intent(getActivity(),AddActivity.class);
                    intent.putExtra(AddActivity.COMPOSE_CODE,AddActivity.COMPOSE_PERDATA);
                }

                getActivity().startActivityForResult(intent,AddActivity.REQUEST_CODE);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1))
                    btn_add.setVisibility(View.INVISIBLE);
                else
                    btn_add.setVisibility(View.VISIBLE);
            }
        });
    }

    private void PopupMenu(final View view, final View button, final int res){

        final PopupMenu popupMenu = new PopupMenu(view.getContext(),button,Gravity.CENTER);
        popupMenu.getMenuInflater().inflate(res,popupMenu.getMenu());

        Object menuHelper;
        Class[] argTypes;

        try{
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popupMenu);
            argTypes = new Class[] { boolean.class };
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper,true);
        } catch (Exception e) {
            Log.d(TAG, "error");
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.profil:
                        break;
                    case R.id.bahasa:
                        break;
                }
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });
    }

}
