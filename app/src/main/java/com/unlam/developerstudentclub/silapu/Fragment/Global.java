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

    FloatingActionButton btn_add;
    SearchView searchView;
    RecyclerView recyclerView;
    RecyclerViewAdapter rvAdapterPengaduan;
    RecyclerViewAdapter tvAdapterPerdata;

    public static String FRAGEMENT_IDENTITY = "identity";

    public Global() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {



        View view;

        final int check = getArguments().getInt(FRAGEMENT_IDENTITY,0);

        switch (check){
            case 1 : view = inflater.inflate(R.layout.frag_regist1st, container, false);
                        break;
            case 2 : view = inflater.inflate(R.layout.frag_regist2nd,container,false);
                        break;
            case 3 : view = inflater.inflate(R.layout.frag_regist3rd, container, false);
                        break;
            case 4 : view = inflater.inflate(R.layout.frag_regist4th, container, false);
                        break;
            case 5 : view = inflater.inflate(R.layout.recylerview,container,false);
                recyclerView = view.findViewById(R.id.recylerview);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new NpaLiniearLayoutManager(getActivity()));
                rvAdapterPengaduan = new RecyclerViewAdapter(getContext(),RecyclerViewAdapter.FRAGMENT_PENGADUAN);

                ArrayList<PengaduanItem> items = new ArrayList<>();
                items.addAll(DummyDataPengaduan.getListData());
                rvAdapterPengaduan.setFilteredPengaduanItem(items);
                recyclerView.setAdapter(rvAdapterPengaduan);
                        break;
            case 6 : view =  inflater.inflate(R.layout.recylerview,container,false);
                recyclerView = view.findViewById(R.id.recylerview);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new NpaLiniearLayoutManager(getActivity()));
                tvAdapterPerdata = new RecyclerViewAdapter(getContext(),RecyclerViewAdapter.FRAGMENT_PERDATA);

                ArrayList<PerdataItem> itemses = new ArrayList<>();
                itemses.addAll(DummyDataPerdata.getListData());
                tvAdapterPerdata.setFilteredPerdataItem(itemses);
                recyclerView.setAdapter(tvAdapterPerdata);
                        break;
            default: view =  inflater.inflate(R.layout.recylerview,container,false);
                        break;
        }


        ButterKnife.bind(view);

        if(check == 5 || check == 6){
            searchView = view.findViewById(R.id.searchview);
            btn_add = view.findViewById(R.id.btn_add_item);

            searchView.setIconified(false);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if(check == 5)
                        rvAdapterPengaduan.getFilter().filter(query);
                    else
                        tvAdapterPerdata.getFilter().filter(query);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    if(check == 5)
                        rvAdapterPengaduan.getFilter().filter(query);
                    else
                        tvAdapterPerdata.getFilter().filter(query);
                    return false;
                }
            });

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("PASS", "KANCUT");
                }
            });
        }
        return view;
    }

}
