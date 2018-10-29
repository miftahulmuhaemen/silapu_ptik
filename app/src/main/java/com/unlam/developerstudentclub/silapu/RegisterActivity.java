package com.unlam.developerstudentclub.silapu;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rd.PageIndicatorView;
import com.unlam.developerstudentclub.silapu.Adapter.FragementAdapter;
import com.unlam.developerstudentclub.silapu.Fragment.Confirmation;
import com.unlam.developerstudentclub.silapu.Fragment.Global;
import com.unlam.developerstudentclub.silapu.Utils.ImplicitlyListenerComposite;
import com.unlam.developerstudentclub.silapu.Utils.Implictly;
import com.unlam.developerstudentclub.silapu.Utils.LockableViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

import static com.unlam.developerstudentclub.silapu.Fragment.Global.FRAGMENT_REGISTER_FIRST;
import static com.unlam.developerstudentclub.silapu.Fragment.Global.FRAGMENT_REGISTER_FORTH;
import static com.unlam.developerstudentclub.silapu.Fragment.Global.FRAGMENT_REGISTER_SECOND;
import static com.unlam.developerstudentclub.silapu.Fragment.Global.FRAGMENT_REGISTER_THIRD;

public class RegisterActivity extends AppCompatActivity implements Implictly, Global.onCompleteResponse {

    @BindView(R.id.viewpager)
    LockableViewPager viewPager;

    @BindView(R.id.fab_left)
    FloatingActionButton fab_left;

    @BindView(R.id.fab_right)
    FloatingActionButton fab_right;

    @BindView(R.id.btn_done)
    Button btn_done;

    @BindView(R.id.btn_masuk)
    Button btn_masuk;

    @BindView(R.id.pageIndicatorView)
    PageIndicatorView pageIndicatorView;

    Implictly implictly;
    ImplicitlyListenerComposite implicitlyListenerComposite = new ImplicitlyListenerComposite();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setupViewPager(viewPager);

        viewPager.setSwipeable(false);
        viewPager.setOffscreenPageLimit(4);
        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        fab_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() == 1)
                    fab_left.setVisibility(View.INVISIBLE);

                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        fab_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentPage = viewPager.getCurrentItem();
                int totalPage = viewPager.getAdapter().getCount();

                if(currentPage == totalPage - 2){
                    Confirmation confirmationDialog = new Confirmation();
                    confirmationDialog.setOnOptionDialogListener(new Confirmation.OnOptionDialogListener() {
                        @Override
                        public void onOptionChoosen(Boolean text) {
                            if(text){
                                implicitlyListenerComposite.onRegisterActivityResponse(true);
                            }
                        }
                    });
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    confirmationDialog.show(fragmentManager,Confirmation.class.getSimpleName());
                }
                else{
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    fab_left.setVisibility(View.VISIBLE);
                }

            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        FragementAdapter adapter = new FragementAdapter(getSupportFragmentManager());
        Global mFragment = new Global();
        Bundle bundle = new Bundle();

        bundle.putInt(Global.FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_FIRST);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part1");
        attachListenerOnFragment(mFragment);
        implicitlyListenerComposite.registerListener(implictly);

        bundle = new Bundle();
        mFragment = new Global();
        bundle.putInt(Global.FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_SECOND);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part2");
        attachListenerOnFragment(mFragment);
        implicitlyListenerComposite.registerListener(implictly);

        bundle = new Bundle();
        mFragment = new Global();
        bundle.putInt(Global.FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_THIRD);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part3");
        attachListenerOnFragment(mFragment);
        implicitlyListenerComposite.registerListener(implictly);

        bundle = new Bundle();
        mFragment = new Global();
        bundle.putInt(Global.FRAGEMENT_IDENTITY,FRAGMENT_REGISTER_FORTH);
        mFragment.setArguments(bundle);
        adapter.addFragment(mFragment, "Part4");

        viewPager.setAdapter(adapter);

    }

    void attachListenerOnFragment(Global fragment){
        try {
            implictly = (Implictly) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(this.toString()
                    + " Needs to implement the methods");
        }
    }

    @Override
    public void onRegisterActivityResponse(Boolean text) {
        //needs to be empty
    }


    public void onCompleteResponse(Boolean text) {
        Log.d("BABOM", text + "");
//        if(text){
//            fab_left.setVisibility(View.INVISIBLE);
//            fab_right.setVisibility(View.INVISIBLE);
//            btn_done.setVisibility(View.VISIBLE);
//            btn_masuk.setVisibility(View.INVISIBLE);
//            pageIndicatorView.setVisibility(View.INVISIBLE);
//            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
//        } else {
//            Snackbar.make(getCurrentFocus(), "Data Masih Salah", Snackbar.LENGTH_LONG).show();
//        }
    }

    @Override
    public void onCompleteFormResponse(String text) {
        Log.d("BABOM", text + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        implicitlyListenerComposite.removeListener();
    }
}
