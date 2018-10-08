package com.unlam.developerstudentclub.silapu;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.unlam.developerstudentclub.silapu.Adapter.FragementAdapter;
import com.unlam.developerstudentclub.silapu.Fragment.Global;
import com.unlam.developerstudentclub.silapu.Utils.LockableViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    LockableViewPager viewPager;

    @BindView(R.id.ntb)
    NavigationTabBar navigationTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupViewPager(viewPager);
        navigationTabBar.setViewPager(viewPager);
        navigationTabBar.setModels(tabModel());
    }

    public ArrayList<NavigationTabBar.Model> tabModel () {

        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        models.add(new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_record_voice_over_black_24dp),
                        Color.BLACK
                ).build());

        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_pan_tool_black_24dp),
                Color.BLACK
        ).build());

        models.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_person_black_24dp),
                Color.BLACK
        ).build());

        return models;
    }

    private void setupViewPager(ViewPager viewPager) {
        FragementAdapter adapter = new FragementAdapter(getSupportFragmentManager());
        Global mFragment = new Global();
        Bundle bundle = new Bundle();

        bundle.putInt(Global.FRAGEMENT_IDENTITY,5);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part4");

        bundle = new Bundle();
        mFragment = new Global();
        bundle.putInt(Global.FRAGEMENT_IDENTITY,6);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part5");

        bundle = new Bundle();
        mFragment = new Global();
        bundle.putInt(Global.FRAGEMENT_IDENTITY,7);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part6");

        viewPager.setAdapter(adapter);
    }
}
