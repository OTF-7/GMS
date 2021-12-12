package com.GMS.aqel.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
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
        mBinding.fabScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBinding.tabLayoutAqel.getSelectedTabPosition()==0) {
                    /*
                    Intent intent = new Intent(mBinding.getRoot().getContext(), QRScannerActivity.class);
                    intent.putExtra(Constant.ACTIVITY.toString() , Constant.AQELNEEDSCANFRAGNENT.toString());
                    startActivity(intent);
                     */
                    Map<String ,Object> aqelDetails = new HashMap<>();
                    Map<String , Object> location = new HashMap<>();
                    location.put(CollectionName.Fields.latitude.name(), "12 14");
                    location.put(CollectionName.Fields.longitude.name(), "12 15");
                    aqelDetails.put(CollectionName.Fields.name.name(), "Ahmed");
                    aqelDetails.put(CollectionName.Fields.userName.name(), "a7md");
                    aqelDetails.put(CollectionName.Fields.password.name(), "123");
                    aqelDetails.put(CollectionName.Fields.hireDate.name(), String.valueOf(new java.sql.Date(System.currentTimeMillis())));
                    Map<String ,Object> socialAccounts = new HashMap<>();
                    socialAccounts.put(CollectionName.Fields.facebook.name(), "facebook");
                    socialAccounts.put(CollectionName.Fields.google.name(), "google");
                    ArrayList<String> telephones = new ArrayList<>();
                    telephones.add("734157397");
                    telephones.add("770038518");
                    aqelDetails.put(CollectionName.Fields.socialAccounts.name(), socialAccounts);
                    aqelDetails.put(CollectionName.Fields.telephones.name(), telephones);
                    NeighborhoodCollection neighborhood = new NeighborhoodCollection("Mousa Street" ,(List<String>) telephones ,0 ,
                            aqelDetails ,location);
                    mCollectionRef.add(neighborhood).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getBaseContext(), "done", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
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


