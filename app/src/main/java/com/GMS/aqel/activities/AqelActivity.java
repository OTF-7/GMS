package com.GMS.aqel.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

        setSupportActionBar(mBinding.toolBar.toolBarAqel);
        setTitle("Aqel");

        FragmentManager fm  = getSupportFragmentManager();
        adapter = new ViewPager2AqelAdapter(fm , getLifecycle());
        mBinding.mainViewPager.setAdapter(adapter);
        mBinding.tabLayoutAqel.addTab(mBinding.tabLayoutAqel.newTab().setText(getResources().getString(R.string.need_scan)));
        mBinding.tabLayoutAqel.addTab(mBinding.tabLayoutAqel.newTab().setText(getResources().getString(R.string.verified)));
        mBinding.tabLayoutAqel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                mBinding.tabLayoutAqel.selectTab(mBinding.tabLayoutAqel.getTabAt(position));
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_aqel_top_bar, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.add_citizen:
                Toast.makeText(getBaseContext() , item.getTitle() , Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_item:
                Toast.makeText(getBaseContext() , item.getTitle() , Toast.LENGTH_SHORT).show();
                break;
        }




        return true ;
    }
}
