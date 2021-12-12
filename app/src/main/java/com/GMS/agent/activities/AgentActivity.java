package com.GMS.agent.activities;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.GeneralClasses.CitizenItemClickListener;
import com.GMS.HistoryActivity;
import com.GMS.R;
import com.GMS.SettingActivity;
import com.GMS.agent.adapters.ItemClickListener;
import com.GMS.agent.adapters.RecyclerViewAdapterCitizen;
import com.GMS.agent.helperClasses.CitizenItem;
import com.GMS.databinding.ActivityAgentBinding;
import com.GMS.firebaseFireStore.CitizenActionDetails;
import com.GMS.firebaseFireStore.CollectionName;
import com.GMS.representative.adapters.RecyclerViewRepAdapterCitizen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AgentActivity extends AppCompatActivity {

    //MainAdapter adapter;

    RecyclerViewAdapterCitizen adapter;

    private ActivityAgentBinding mBinding;

    public static Drawable mAcceptedBackgroundImage;
    private Dialog stationDialog;
    private static final int CALL_PERMISSION = 122;
    String idAction;
    long sellingPrice ;
    CitizenItemClickListener mItemClickListener;
    ArrayList<CitizenActionDetails> detailsItems = new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference mCollectionRef = db.collection(CollectionName.CITIZENS.name());
    private final CollectionReference mCollectionRefNeighborhood = db.collection(CollectionName.NEIGHBORHOODS.name());
    private final CollectionReference mCollectionRefAction = db.collection(CollectionName.ACTIONS.name());
    private final CollectionReference mCollectionRefActionDetails = db.collection(CollectionName.ACTION_DETAILS.name());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAgentBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mAcceptedBackgroundImage = getDrawable(R.drawable.accpted_image_background_shape);
        // change color of status bar color
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        setSupportActionBar(mBinding.agentTopBar.toolBarAgent);
        setTitle("Agent");
        mBinding.cardViewHeaderContainer.setBackgroundColor(Color.TRANSPARENT);

        final int HEIGHT_HEADER_VIEW = mBinding.backgroundHeader.getHeight();

        mBinding.moreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.moreTextView.getText().equals(getString(R.string.hide))) {
                    ViewGroup.LayoutParams params = mBinding.cardViewHeaderContainer.getLayoutParams();
                    mBinding.moreTextView.setText(R.string.show);
                    params.height = mBinding.textViews.getHeight() * 2;
                    mBinding.cardViewHeaderContainer.setLayoutParams(params);
                    // view
                    ViewGroup.LayoutParams pView = mBinding.backgroundHeader.getLayoutParams();
                    double height = mBinding.textViews.getHeight() * 1;
                    pView.height = (int) height;
                    mBinding.backgroundHeader.setLayoutParams(pView);
                } else {
                    ViewGroup.LayoutParams params = mBinding.cardViewHeaderContainer.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    mBinding.cardViewHeaderContainer.setLayoutParams(params);
                    mBinding.moreTextView.setText(R.string.hide);
                    ViewGroup.LayoutParams pView = mBinding.backgroundHeader.getLayoutParams();
                    double height = mBinding.agentTopBar.toolBarAgent.getHeight() * 1.7;
                    pView.height = (int) height;
                    mBinding.backgroundHeader.setLayoutParams(pView);
                }

            }
        });

        mItemClickListener = new CitizenItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        };

     getAction();




    }
    public  void initRV()
    {
        mBinding.progressWhileLoading.setVisibility(View.GONE);
        adapter = new RecyclerViewAdapterCitizen(detailsItems ,getBaseContext() ,mItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.rvCitizen.setHasFixedSize(true);
        mBinding.rvCitizen.setLayoutManager(layoutManager);
        mBinding.rvCitizen.setAdapter(adapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_agent_top_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.search_ic);
        SearchView searchView = (SearchView) searchItem.getActionView();
        createDialog();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }

        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting_item:
                Intent intentSetting = new Intent(this.getBaseContext(), SettingActivity.class);
                startActivity(intentSetting);
                break;
            case R.id.history_item:
                Intent intentHistory = new Intent(this.getBaseContext(), HistoryActivity.class);
                startActivity(intentHistory);
                break;
            case R.id.station_item:
                showDialog();
                break;
            case R.id.cylinder_recieve_item:
                Intent intentCylindersReceive = new Intent(this.getBaseContext(), CylindersReceiveActivity.class);
                startActivity(intentCylindersReceive);

        }
        return super.onOptionsItemSelected(item);
    }

    private void createDialog() {
        stationDialog = new Dialog(mBinding.getRoot().getContext());
        stationDialog.setContentView(R.layout.station_dialoge_details);
        stationDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Window window = stationDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        stationDialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    private void showDialog() {
        stationDialog.show();
        stationDialog.findViewById(R.id.btn_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stationDialog.dismiss();
            }
        });
        stationDialog.findViewById(R.id.tv_phone_call_station_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stationDialog.dismiss();
                checkPermission();

            }
        });

    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mBinding.getRoot().getContext(), new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION);
            return;
        }
        startCalling();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CALL_PERMISSION:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCalling();
                }
        }
    }

    private void startCalling() {

        TextView tvCallNumber = stationDialog.findViewById(R.id.tv_phone_call_station_dialog);
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tvCallNumber.getText().toString()));
        startActivity(callIntent);
    }
    private void getAction() {

        mCollectionRefAction.whereEqualTo(CollectionName.Fields.actionDate.name(), String.valueOf(new java.sql.Date(System.currentTimeMillis())))
                .whereEqualTo(CollectionName.Fields.neighborhoodDetails.name() + "." + CollectionName.Fields.name.name(), "Mousa Street")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    idAction =queryDocumentSnapshots.getDocuments().get(0).getId().toString();
                    sellingPrice = queryDocumentSnapshots.getDocuments().get(0).getLong(CollectionName.Fields.sellingPrice.name().toString());

                    getActionDetails();

                } else {
                    mBinding.progressWhileLoading.setVisibility(View.GONE);
                    Toast.makeText(getBaseContext(), "no Action today", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void getActionDetails() {
        mCollectionRefAction.document(idAction).collection(CollectionName.ACTION_DETAILS.name())
                .whereEqualTo(CollectionName.Fields.deliveredState.name() + "." + CollectionName.Fields.aqelVerified, true)
                .whereEqualTo(CollectionName.Fields.deliveredState.name() + "." + CollectionName.Fields.repVerified, true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e(TAG, e.toString());
                        }
                        if (queryDocumentSnapshots.isEmpty()) {
                            adapter = null ;
                            detailsItems.clear();
                            initRV();
                            mBinding.progressWhileLoading.setVisibility(View.GONE);
                            Toast.makeText(getBaseContext(), "no Items", Toast.LENGTH_SHORT).show();
                        } else {
                            adapter = null ;
                            detailsItems.clear();
                            for (QueryDocumentSnapshot q : queryDocumentSnapshots) {

                                CitizenActionDetails actionDetails = q.toObject(CitizenActionDetails.class);
                                actionDetails.setDocumentId(q.getId());
                                detailsItems.add(actionDetails);
                            }
                            initRV();
                        }
                    }
                });
    }
}