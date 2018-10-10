package com.unlam.developerstudentclub.silapu.Fragment;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.unlam.developerstudentclub.silapu.Adapter.RecyclerViewAdapter;
import com.unlam.developerstudentclub.silapu.Entity.DummyDataPengaduan;
import com.unlam.developerstudentclub.silapu.Entity.DummyDataPerdata;
import com.unlam.developerstudentclub.silapu.Entity.PengaduanItem;
import com.unlam.developerstudentclub.silapu.Entity.PerdataItem;
import com.unlam.developerstudentclub.silapu.MainActivity;
import com.unlam.developerstudentclub.silapu.R;

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

    RecyclerView recyclerView;
    @Nullable @BindView(R.id.toolbar_search) Toolbar toolbar;
    RecyclerViewAdapter rvAdapterPengaduan;
    RecyclerViewAdapter tvAdapterPerdata;

    public static String FRAGEMENT_IDENTITY = "identity";

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private SearchView searchView2 = null;
    private SearchView.OnQueryTextListener queryTextListener2;

    public Global() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view;
        int check = getArguments().getInt(FRAGEMENT_IDENTITY,0);

        if( check == 1) {
            view = inflater.inflate(R.layout.frag_regist1st, container, false);
            ButterKnife.bind(view);
        }
        else if(check == 2){
            view = inflater.inflate(R.layout.frag_regist2nd,container,false);
            ButterKnife.bind(view);
        }
        else if(check == 3) {
            view = inflater.inflate(R.layout.frag_regist3rd, container, false);
            ButterKnife.bind(view);
        }
        else if(check == 4) {
            view = inflater.inflate(R.layout.frag_regist4th, container, false);
            ButterKnife.bind(view);
        }
        else if(check == 5){
            view = inflater.inflate(R.layout.recylerview,container,false);
            recyclerView = view.findViewById(R.id.recylerview);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvAdapterPengaduan = new RecyclerViewAdapter(getContext(),RecyclerViewAdapter.FRAGMENT_PENGADUAN);
            ArrayList<PengaduanItem> items = new ArrayList<>();
            items.addAll(DummyDataPengaduan.getListData());
            rvAdapterPengaduan.setFilteredPengaduanItem(items);
            recyclerView.setAdapter(rvAdapterPengaduan);
            ButterKnife.bind(this,view);
        }
        else if(check == 6){
            view =  inflater.inflate(R.layout.recylerview,container,false);
            recyclerView = view.findViewById(R.id.recylerview);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            tvAdapterPerdata = new RecyclerViewAdapter(getContext(),RecyclerViewAdapter.FRAGMENT_PERDATA);
            ArrayList<PerdataItem> items = new ArrayList<>();
            items.addAll(DummyDataPerdata.getListData());
            tvAdapterPerdata.setFilteredPerdataItem(items);
            recyclerView.setAdapter(tvAdapterPerdata);

            ButterKnife.bind(this,view);
        }
        else{
            view = inflater.inflate(R.layout.recylerview,container,false);
            ButterKnife.bind(view);
        }


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        final int check = getArguments().getInt(FRAGEMENT_IDENTITY, 0);
        if(check==5){
            inflater.inflate(R.menu.searchbar,menu);
            SearchView searchView1 = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView1.setIconified(false);
            searchView1.setMaxWidth(Integer.MAX_VALUE);

            searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                        rvAdapterPengaduan.getFilter().filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                        rvAdapterPengaduan.getFilter().filter(query);
                    return false;
                }
            });
        }
        Log.d("PASS", check + " PASS");
        if(check==6){
            Log.d("PASS", check+"");
            inflater.inflate(R.menu.searchbar,menu);
            SearchView searchView2 = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView2.setIconified(false);
            searchView2.setMaxWidth(Integer.MAX_VALUE);

            searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                        tvAdapterPerdata.getFilter().filter(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                        tvAdapterPerdata.getFilter().filter(query);
                    return true;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }
}
