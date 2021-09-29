package com.GMS.representative.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.GMS.Constant;
import com.GMS.QRScannerActivity;
import com.GMS.R;
import com.GMS.SettingActivity;
import com.GMS.databinding.ActivityRepresentativeBinding;
import com.GMS.representative.adapters.MainAdapter;
import com.GMS.representative.adapters.ViewPager2Adapter;
import com.google.android.material.tabs.TabLayout;

public class RepresentativeActivity extends AppCompatActivity {

    public static final int ADDITION_REQUEST_REQ_CODE = 1;
    MenuItem mMenuItemNotification;
    TextView tvNotificationCounter;
    ImageView ivNotificationIcon;
    public static int pendingNotification = 99;
    MainAdapter mAdapter;
    ViewPager2Adapter vpAdapter;
    ActivityRepresentativeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRepresentativeBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        // change color of status bar color
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(mBinding.toolBar.toolBarRepresentative);
        setTitle("Representative");
        FragmentManager fm = getSupportFragmentManager();
        vpAdapter = new ViewPager2Adapter(fm, getLifecycle());
        mBinding.mainViewPager.setAdapter(vpAdapter);
        mBinding.tabLayoutRepresentative.addTab(mBinding.tabLayoutRepresentative.newTab().setText(getResources().getString(R.string.need_scan)));
        mBinding.tabLayoutRepresentative.addTab(mBinding.tabLayoutRepresentative.newTab().setText(getResources().getString(R.string.verified)));
        mBinding.tabLayoutRepresentative.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
                mBinding.tabLayoutRepresentative.selectTab(mBinding.tabLayoutRepresentative.getTabAt(position));
                if (position == 1) {
                    mBinding.fabScan.setVisibility(View.GONE);
                } else {
                    mBinding.fabScan.setVisibility(View.VISIBLE);
                }
            }

        });
        mBinding.fabScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBinding.tabLayoutRepresentative.getSelectedTabPosition()==0) {
                    Intent intent = new Intent(mBinding.getRoot().getContext(), QRScannerActivity.class);
                    intent.putExtra(Constant.ACTIVITY.toString() , Constant.REPNEEDSCAN.toString());
                    startActivity(intent);
                }
            }
        });

        /*
        mAdapter = new MainAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new NeedScanRepFragment() , "Need Scan");
        mAdapter.addFragment(new VerifiedRepFragment() , "Verified");
        mBinding.mainViewPager.setAdapter(mAdapter);
        mBinding.tabLayoutRepresentative.setupWithViewPager(mBinding.mainViewPager);
        */

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_representative_top_bar, menu);
        mMenuItemNotification = menu.findItem(R.id.notification_addition);
        checkNotification();

        return true;
    }

    private void checkNotification() {


        if (pendingNotification <=0) {
            mMenuItemNotification.setActionView(null);
        } else {
            mMenuItemNotification.setActionView(R.layout.notification_layout);
            View view = mMenuItemNotification.getActionView();
            tvNotificationCounter = view.findViewById(R.id.notification_counter);
            ivNotificationIcon = view.findViewById(R.id.iv_notification_icon);
            ivNotificationIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAdditionRequestActivity();
                }
            });
            Drawable mDrawable = getDrawable(R.drawable.notification_counter_shape);
            view.findViewById(R.id.card_view).setBackground(mDrawable);
            tvNotificationCounter.setText(String.valueOf(pendingNotification));
        }
    }

    private void openAdditionRequestActivity() {
        Intent intent = new Intent(this, AdditionRequestsActivity.class);
        intent.putExtra("pendingNotification", pendingNotification);
        startActivityForResult(intent, ADDITION_REQUEST_REQ_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);


        switch (item.getItemId()) {
            case R.id.setting_item:
                  Intent intent = new Intent(mBinding.getRoot().getContext() , SettingActivity.class);
                  startActivity(intent);
                break;

            case R.id.notification_addition:
                Toast.makeText(getBaseContext(), item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                openAdditionRequestActivity();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDITION_REQUEST_REQ_CODE) {
            pendingNotification = data.getIntExtra("pendingNotification", pendingNotification);
            checkNotification();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding=null;
    }
}