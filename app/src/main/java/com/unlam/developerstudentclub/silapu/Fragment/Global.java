package com.unlam.developerstudentclub.silapu.Fragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.unlam.developerstudentclub.silapu.Adapter.RecyclerViewAdapter;
import com.unlam.developerstudentclub.silapu.Entity.DummyDataPengaduan;
import com.unlam.developerstudentclub.silapu.Entity.DummyDataPerdata;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.MainActivity;
import com.unlam.developerstudentclub.silapu.R;
import com.unlam.developerstudentclub.silapu.Utils.NpaLiniearLayoutManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class Global extends Fragment {

    @Nullable @BindView(R.id.edt_email) EditText edt_email;
    @Nullable @BindView(R.id.edt_alamat) EditText edt_alamat;
    @Nullable @BindView(R.id.edt_nama) EditText edt_nama;
    @Nullable @BindView(R.id.edt_password) EditText edt_password;

    @Nullable @BindView(R.id.spin_jk)  Spinner spn_JenisKelamin;
    @Nullable @BindView(R.id.spin_idt) Spinner spn_Identitas;
    @Nullable @BindView(R.id.edt_nomberPhone) EditText edt_phoneNumber;

    @Nullable @BindView(R.id.btn_add_item) FloatingActionButton btn_add;
    @Nullable @BindView(R.id.searchview) SearchView searchView;
    @Nullable @BindView(R.id.recylerview) RecyclerView recyclerView;

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
            case FRAGMENT_REGISTER_FIRST : view = inflater.inflate(R.layout.frag_regist1st, container, false);
                        break;
            case FRAGMENT_REGISTER_SECOND : view = inflater.inflate(R.layout.frag_regist2nd,container,false);
                        break;
            case FRAGMENT_REGISTER_THIRD : view = inflater.inflate(R.layout.frag_regist3rd, container, false);
                        break;
            case FRAGMENT_REGISTER_FORTH : view = inflater.inflate(R.layout.frag_regist4th, container, false);
                        break;
            case FRAGMENT_PENGADUAN : view = inflater.inflate(R.layout.recylerview,container,false);
                        break;
            case FRAGMENT_PERDATA : view =  inflater.inflate(R.layout.recylerview,container,false);
                        break;
            case FRAGMENT_PROFIL : view = inflater.inflate(R.layout.frag_profile,container,false);
                        break;
            default: view =  null;
                        break;
        }

        ButterKnife.bind(this,view);

        if(CHECK == FRAGMENT_PENGADUAN || CHECK == FRAGMENT_PERDATA){

            RecylerViewAdapterConnect(CHECK);

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

                    view.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                        @Override
                        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

                        }
                    });

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

        return view;
    }

    private void RecylerViewAdapterConnect(int Fragment){

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

}
