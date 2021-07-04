package com.GMS.representative.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;

import com.GMS.R;
import com.GMS.databinding.ActivityRepresentativeBinding;
import com.GMS.representative.adapters.MainAdapter;
import com.GMS.representative.adapters.ViewPager2Adapter;
import com.GMS.representative.fragments.NeedScanFragment;
import com.GMS.representative.fragments.VerifiedFragment;
import com.google.android.material.tabs.TabLayout;

public class RepresentativeActivity extends AppCompatActivity {

    MainAdapter mAdapter ;
    ViewPager2Adapter vpAdapter ;
    ActivityRepresentativeBinding mBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRepresentativeBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.toolBar.toolBarAgent);
        setTitle("Representative");
     FragmentManager fm  = getSupportFragmentManager();
     vpAdapter = new ViewPager2Adapter(fm , getLifecycle());
     mBinding.mainViewPager.setAdapter(vpAdapter);
     mBinding.tabLayoutRepresentative.addTab(mBinding.tabLayoutRepresentative.newTab().setText("Need Scan"));
     mBinding.tabLayoutRepresentative.addTab(mBinding.tabLayoutRepresentative.newTab().setText("Need Scan"));
     mBinding.tabLayoutRepresentative.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
         @Override
         public void onTabSelected(TabLayout.Tab tab) {
             mBinding.mainViewPager.setCurrentItem(tab.getPosition());
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
           mBinding.tabLayoutRepresentative.selectTab(mBinding.tabLayoutRepresentative.getTabAt(position));
         }
         
     });

        /*
        mAdapter = new MainAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new NeedScanFragment() , "Need Scan");
        mAdapter.addFragment(new VerifiedFragment() , "Verified");
        mBinding.mainViewPager.setAdapter(mAdapter);
        mBinding.tabLayoutRepresentative.setupWithViewPager(mBinding.mainViewPager);
        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_representative_top_bar , menu);

        return true ;
    }
}