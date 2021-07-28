package com.GMS.aqel.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.GMS.R;
import com.GMS.aqel.adapters.ViewPager2AqelAdapter;
import com.GMS.databinding.ActivityAqelBinding;
import com.google.android.material.tabs.TabLayout;

public class AqelActivity extends AppCompatActivity {

    ActivityAqelBinding mBinding;
    ViewPager2AqelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAqelBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        // change color of status bar color
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(mBinding.toolBar.toolBarAqel);
        setTitle("Aqel");

        FragmentManager fm = getSupportFragmentManager();
        adapter = new ViewPager2AqelAdapter(fm, getLifecycle());
        mBinding.mainViewPager.setAdapter(adapter);
        mBinding.tabLayoutAqel.addTab(mBinding.tabLayoutAqel.newTab().setText(getResources().getString(R.string.need_scan)));
        mBinding.tabLayoutAqel.addTab(mBinding.tabLayoutAqel.newTab().setText(getResources().getString(R.string.verified)));
        mBinding.tabLayoutAqel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBinding.mainViewPager.setCurrentItem(tab.getPosition());
                if (mBinding.mainViewPager.getCurrentItem() == 1 || tab.getPosition() == 1) {
                    mBinding.fabScan.setVisibility(View.GONE);
                } else {
                    mBinding.fabScan.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mBinding.mainViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mBinding.tabLayoutAqel.selectTab(mBinding.tabLayoutAqel.getTabAt(position));
                if (position == 1) {
                    mBinding.fabScan.setVisibility(View.GONE);
                } else {
                    mBinding.fabScan.setVisibility(View.VISIBLE);
                }

            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding=null;
    }
}
