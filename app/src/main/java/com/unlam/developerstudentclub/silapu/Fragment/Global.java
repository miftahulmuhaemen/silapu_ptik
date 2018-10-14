package com.unlam.developerstudentclub.silapu.Fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.widget.PopupMenu;
import android.widget.PopupWindow;

import com.unlam.developerstudentclub.silapu.Adapter.RecyclerViewAdapter;
import com.unlam.developerstudentclub.silapu.Entity.DummyDataPengaduan;
import com.unlam.developerstudentclub.silapu.Entity.DummyDataPerdata;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.R;
import com.unlam.developerstudentclub.silapu.Utils.NpaLiniearLayoutManager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;
import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Global extends Fragment {

    @Nullable @BindView(R.id.edt_email) EditText edt_email;
    @Nullable @BindView(R.id.edt_alamat) EditText edt_alamat;
    @Nullable @BindView(R.id.edt_nama) EditText edt_nama;
    @Nullable @BindView(R.id.edt_password) EditText edt_password;

    @Nullable @BindView(R.id.ti_jenisKelamin) TextFieldBoxes ti_jenisKelamin;
    @Nullable @BindView(R.id.edt_jenisKelamin) ExtendedEditText edt_jenisKelamin;
    @Nullable @BindView(R.id.edt_numberPhone) EditText edt_phoneNumber;

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

    public Global() {
        // Required empty public constructor
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
                        edt_jenisKelamin.setKeyListener(null);
                        PopupMenu(view,ti_jenisKelamin,R.layout.menu_jeniskelamin);
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
                        FragmentPengaduanAndPerdataMethod(view,CHECK);
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

    private void FragmentPengaduanAndPerdataMethod(View view, int Fragment){

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

    private void PopupMenu(final View views, final View button, final int res){


//        final PopupMenu popupMenu = new PopupMenu(view.getContext(),button,Gravity.CENTER);
//        popupMenu.getMenuInflater().inflate(res,popupMenu.getMenu());
//
//        Object menuHelper;
//        Class[] argTypes;
//
//        try{
//            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
//            fMenuHelper.setAccessible(true);
//            menuHelper = fMenuHelper.get(popupMenu);
//            argTypes = new Class[] { boolean.class };
//            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper,true);
//        } catch (Exception e) {
//            Log.d(TAG, "error");
//        }
//
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.profil:
//                        break;
//                    case R.id.bahasa:
//                        break;
//                    case R.id.lakilaki:
//                        break;
//                    case R.id.perempuan:
//                        break;
//                }
//                return true;
//            }
//        });
//
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final PopupWindow popupWindow = new PopupWindow(views.getContext());
                View layout = getLayoutInflater().inflate(res,null);

                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setContentView(layout);

                popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);

                popupWindow.setTouchable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAsDropDown(button);
            }
        });
    }

}
