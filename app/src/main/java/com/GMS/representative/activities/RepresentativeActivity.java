package com.GMS.representative.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
import com.GMS.firebaseFireStore.CollectionName;
import com.GMS.representative.adapters.MainAdapter;
import com.GMS.representative.adapters.ViewPager2Adapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RepresentativeActivity extends AppCompatActivity {

    public static final int ADDITION_REQUEST_REQ_CODE = 1;
    MenuItem mMenuItemNotification;
    TextView tvNotificationCounter;
    ImageView ivNotificationIcon;
    public static int pendingNotification = 0;
    MainAdapter mAdapter;
    ViewPager2Adapter vpAdapter;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference mCollectionRef = db.collection(CollectionName.CITIZENS.name());
    private final CollectionReference mCollectionRefNeighborhood = db.collection(CollectionName.NEIGHBORHOODS.name());

    ActivityRepresentativeBinding mBinding;
    private final String REP_ACTIVITY_NOTIFICATION="REP_ACTIVITY_NOTIFICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRepresentativeBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        // change color of status bar color
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.md_theme_light_primary));
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
                if (mBinding.tabLayoutRepresentative.getSelectedTabPosition() == 0) {
                    Intent intent = new Intent(mBinding.getRoot().getContext(), QRScannerActivity.class);
                    intent.putExtra(Constant.ACTIVITY.toString(), Constant.REPNEEDSCAN.toString());
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
        mMenuItemNotification = menu.findItem(R.id.menu_representative_item_notification);
        //checkNotification();

        return true;
    }

    private void checkNotification() {


        try {

            if (pendingNotification <= 0) {
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
        catch (Exception ex)
        {
            Log.e(REP_ACTIVITY_NOTIFICATION , ex.getMessage());
        }
    }

    private void openAdditionRequestActivity() {
        Intent intent = new Intent(this, AdditionRequestsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);


        switch (item.getItemId()) {
            case R.id.setting_item:
                Intent intent = new Intent(mBinding.getRoot().getContext(), SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_representative_item_notification:
                Toast.makeText(getBaseContext(), item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                openAdditionRequestActivity();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*
        if (requestCode == ADDITION_REQUEST_REQ_CODE) {
            pendingNotification = data.getIntExtra("pendingNotification", pendingNotification);
            checkNotification();
        }

         */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*
        mCollectionRef.whereEqualTo(CollectionName.Fields.state.name(), false).addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                pendingNotification = queryDocumentSnapshots.size();
                checkNotification();
            }
        });

         */
        mCollectionRefNeighborhood.whereEqualTo(CollectionName.Fields.name.name(), "Mousa Street")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           String [] id = new String[1];
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty())
                {
                    for(QueryDocumentSnapshot q : queryDocumentSnapshots) {
                        id[0] = q.getId();
                        break;
                    }
                    mCollectionRefNeighborhood.document(id[0]).collection(CollectionName.CITIZENS.name()).whereEqualTo(CollectionName.Fields.state.name(), false).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                return;
                            }
                            pendingNotification = queryDocumentSnapshots.size();
                            checkNotification();
                        }
                    });


                }
            }
        });
    }
}