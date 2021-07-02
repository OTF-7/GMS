package com.GMS.representative.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.GMS.R;
import com.GMS.databinding.ActivityRepresentativeBinding;
import com.GMS.representative.adapters.ContentRepFragmentAdapter;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RepresentativeActivity extends AppCompatActivity {

    ActivityRepresentativeBinding mBinding;
    //MainAdapter adapter;
    ContentRepFragmentAdapter contentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRepresentativeBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.repTopBar.toolBarRep);
        setTitle("Representative");
        mBinding.cardViewHeaderContainer.setBackgroundColor(Color.TRANSPARENT);
        mBinding.cardViewHeaderContainer.setAlpha(0);
        mBinding.cardViewHeaderContainer.setTranslationY(-400);
        final int HEIGHT_HEADER_VIEW = mBinding.backgroundHeader.getHeight();
        /*tablayout
        adapter = new MainAdapter(getSupportFragmentManager());
        adapter.addFragment(new NeedScanFragment() , "need scan");
        adapter.addFragment(new VerifiedFragment() , "Verified");
        mbindig.viewPager2.setAdapter(adapter);
        mbindig.tabLayout.setupWithViewPager(mbindig.viewPager2);
        */
        // viwepager2
        FragmentManager fm = getSupportFragmentManager();
        contentAdapter = new ContentRepFragmentAdapter(fm, getLifecycle());
        mBinding.viewPager2.setAdapter(contentAdapter);
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText("Need Scan"));
        mBinding.tabLayout.addTab(mBinding.tabLayout.newTab().setText("Verified"));
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                mBinding.viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mBinding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mBinding.tabLayout.selectTab(mBinding.tabLayout.getTabAt(position));
            }
        });

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {

                BadgeDrawable mBadgeDrawable ;
                switch (position)
                {
                    case 0 :
                        tab.setText("Need Scan");
                        mBadgeDrawable=tab.getOrCreateBadge();
                        mBadgeDrawable.setBackgroundColor(getResources().getColor(R.color.blue));
                        mBadgeDrawable.setVisible(true);
                        break;
                    case 1 :
                        tab.setText("Verified");
                        mBadgeDrawable =tab.getOrCreateBadge();
                        mBadgeDrawable.setBackgroundColor(getResources().getColor(R.color.blue));
                        mBadgeDrawable.setVisible(true);
                        break;
                }
            }
        });

        tabLayoutMediator.attach();
        mBinding.moreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.moreTextView.getText().equals("hide")) {
                    ViewGroup.LayoutParams params = mBinding.cardViewHeaderContainer.getLayoutParams();

                    params.height = mBinding.textViews.getHeight() * 2;
                    mBinding.cardViewHeaderContainer.setLayoutParams(params);
                    mBinding.moreTextView.setText("more");
                    // view
                    ViewGroup.LayoutParams pView = mBinding.backgroundHeader.getLayoutParams();
                    double height = mBinding.textViews.getHeight() * 1;
                    pView.height = (int) height;
                    mBinding.backgroundHeader.setLayoutParams(pView);
                } else {
                    ViewGroup.LayoutParams params = mBinding.cardViewHeaderContainer.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    mBinding.cardViewHeaderContainer.setLayoutParams(params);
                    mBinding.moreTextView.setText("hide");

                    ViewGroup.LayoutParams pView = mBinding.backgroundHeader.getLayoutParams();
                    double height = mBinding.repTopBar.toolBarRep.getHeight() * 1.7;
                    pView.height = (int) height;
                    mBinding.backgroundHeader.setLayoutParams(pView);
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mBinding.cardViewHeaderContainer.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(200);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_representative_top_bar, menu);
        return true;
    }
}