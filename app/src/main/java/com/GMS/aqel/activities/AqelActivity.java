package com.GMS.aqel.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.GMS.Constant;
import com.GMS.QRScannerActivity;
import com.GMS.R;
import com.GMS.aqel.adapters.ViewPager2AqelAdapter;
import com.GMS.databinding.ActivityAqelBinding;
import com.GMS.firebaseFireStore.ActionCollection;
import com.GMS.firebaseFireStore.CollectionName;
import com.GMS.firebaseFireStore.NeighborhoodCollection;
import com.GMS.representative.activities.AdditionRequestsActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class  AqelActivity extends AppCompatActivity {

    ActivityAqelBinding mBinding;
    ViewPager2AqelAdapter adapter;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference action = db.collection(CollectionName.ACTIONS.name().toString());
    private final CollectionReference mCollectionRef = db.collection(CollectionName.NEIGHBORHOODS.name());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAqelBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // change color of status bar color
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.md_theme_light_primary));
        setSupportActionBar(mBinding.toolBar.toolBarAqel);
        setTitle("Aqel");
        //addAction();
        FragmentManager fm = getSupportFragmentManager();
        adapter = new ViewPager2AqelAdapter(fm, getLifecycle());
        mBinding.mainViewPager.setAdapter(adapter);
        mBinding.tabLayoutAqel.addTab(mBinding.tabLayoutAqel.newTab().setText(getResources().getString(R.string.need_scan)));
        mBinding.tabLayoutAqel.addTab(mBinding.tabLayoutAqel.newTab().setText(getResources().getString(R.string.verified)));
        mBinding.tabLayoutAqel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBinding.mainViewPager.setCurrentItem(tab.getPosition());
                /*
                if (mBinding.mainViewPager.getCurrentItem() == 1 || tab.getPosition() == 1) {
                    mBinding.fabScan.setVisibility(View.GONE);
                } else {
                    mBinding.fabScan.setVisibility(View.VISIBLE);
                }

                 */
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
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }
    private void addAction() {
        Map<String, Object> stationDetails = new HashMap<>();
        stationDetails.put(CollectionName.Fields.stationName.name(), " AlHowak");
        stationDetails.put(CollectionName.Fields.telephones.name(), "734 157 397");
        Map<String, Object> neighborhoodDetails = new HashMap<>();
        neighborhoodDetails.put(CollectionName.Fields.name.name(), "Mousa Street");
        neighborhoodDetails.put(CollectionName.Fields.numberOfDelivered.name(), 0);
        ActionCollection actionCollection = new ActionCollection("Abdulrahman",
                0 ,
                "abubaker",
                "khalid",
                3350,
                1189,
                String.valueOf(new java.sql.Date(System.currentTimeMillis())),
                stationDetails, neighborhoodDetails);

        action.add(actionCollection).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(getBaseContext() , "action" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}


