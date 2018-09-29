package com.unlam.developerstudentclub.silapu;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.fab_left)
    FloatingActionButton fab_left;

    @BindView(R.id.fab_right)
    FloatingActionButton fab_right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_register);
        ButterKnife.bind(this);
        setupViewPager(viewPager);


        fab_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() > 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        fab_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() < viewPager.getOffscreenPageLimit())
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    int currentPage = viewPager.getCurrentItem();

                    if(currentPage > 0)
                        fab_left.setVisibility(View.VISIBLE);
                    else
                        fab_left.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        FragementAdapter adapter = new FragementAdapter(getSupportFragmentManager());
        FragmentRegistrasi mFragment = new FragmentRegistrasi();
        Bundle bundle = new Bundle();

        bundle.putInt(FragmentRegistrasi.FRAGEMENT_IDENTITY,1);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "2ss2");

        bundle = new Bundle();
        mFragment = new FragmentRegistrasi();
        bundle.putInt(FragmentRegistrasi.FRAGEMENT_IDENTITY,2);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "2edasd");

        viewPager.setAdapter(adapter);
    }
}
