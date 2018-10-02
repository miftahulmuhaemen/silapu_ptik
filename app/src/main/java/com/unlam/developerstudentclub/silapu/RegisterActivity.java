package com.unlam.developerstudentclub.silapu;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.unlam.developerstudentclub.silapu.Adapter.FragementAdapter;
import com.unlam.developerstudentclub.silapu.Fragment.Confirmation;
import com.unlam.developerstudentclub.silapu.Fragment.Registrasi;
import com.unlam.developerstudentclub.silapu.Utils.LockableViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    LockableViewPager viewPager;

    @BindView(R.id.fab_left)
    FloatingActionButton fab_left;

    @BindView(R.id.fab_right)
    FloatingActionButton fab_right;

    @BindView(R.id.btn_done)
    Button btn_done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setupViewPager(viewPager);

        viewPager.setSwipeable(false);

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

                int currentPage = viewPager.getCurrentItem();
                int totalPage = viewPager.getAdapter().getCount();

                if(currentPage < viewPager.getOffscreenPageLimit())
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

                if(currentPage < totalPage - 1){
                    Confirmation confirmationDialog = new Confirmation();
                    confirmationDialog.setOnOptionDialogListener(new Confirmation.OnOptionDialogListener() {
                        @Override
                        public void onOptionChoosen(String code) {
                            if(Confirmation.CONFIRM_CODE == code){
                                fab_left.setVisibility(View.INVISIBLE);
                                fab_right.setVisibility(View.INVISIBLE);
                                btn_done.setVisibility(View.VISIBLE);
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                            }
                        }
                    });

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    confirmationDialog.show(fragmentManager,Confirmation.class.getSimpleName());
                }
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        Registrasi mFragment = new Registrasi();
        Bundle bundle = new Bundle();

        bundle.putInt(Registrasi.FRAGEMENT_IDENTITY,1);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part1");

        bundle = new Bundle();
        mFragment = new Registrasi();
        bundle.putInt(Registrasi.FRAGEMENT_IDENTITY,2);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part2");

        bundle = new Bundle();
        mFragment = new Registrasi();
        bundle.putInt(Registrasi.FRAGEMENT_IDENTITY,3);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part3");

        bundle = new Bundle();
        mFragment = new Registrasi();
        bundle.putInt(Registrasi.FRAGEMENT_IDENTITY,4);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part4");

        viewPager.setAdapter(adapter);
    }
}
