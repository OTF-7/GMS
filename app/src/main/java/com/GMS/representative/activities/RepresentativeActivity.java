package com.GMS.representative.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;

import com.GMS.R;
import com.GMS.databinding.ActivityRepresentativeBinding;
import com.GMS.representative.adapters.MainAdapter;
import com.GMS.representative.fragments.NeedScanFragment;
import com.GMS.representative.fragments.VerifiedFragment;

public class RepresentativeActivity extends AppCompatActivity {

    MainAdapter mAdapter ;
    ActivityRepresentativeBinding mBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRepresentativeBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.toolBar.toolBarAgent);
        setTitle("Representative");

        mAdapter = new MainAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new NeedScanFragment() , "Need Scan");
        mAdapter.addFragment(new VerifiedFragment() , "Verified");
        mBinding.mainViewPager.setAdapter(mAdapter);
        mBinding.tabLayoutRepresentative.setupWithViewPager(mBinding.mainViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_representative_top_bar , menu);

        return true ;
    }
}