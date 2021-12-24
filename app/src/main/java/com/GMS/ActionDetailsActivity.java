package com.GMS;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaDrm;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.GeneralAdapters.RecyclerViewAdapterDetailsHistory;
import com.GMS.GeneralClasses.NetworkCollection;
import com.GMS.GeneralClasses.Report;
import com.GMS.databinding.ActivityActionDetailsBinding;
import com.GMS.firebaseFireStore.CitizenActionDetails;
import com.GMS.firebaseFireStore.CollectionName;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ActionDetailsActivity extends AppCompatActivity {

    ActivityActionDetailsBinding mBinding;
    ArrayList<CitizenActionDetails> citizenActionDetails = new ArrayList<>();
    RecyclerViewAdapterDetailsHistory adapter;
    private static final String REFRESH_SWIPE = "REFRESH_SWIPE";
    private static final String REFRESH_START = "REFRESH_START";
    private static final String DOCUMENT_ID="DOCUMENT_ID";
    Intent intentAction = new Intent();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference mCollectionRefAction = db.collection(CollectionName.ACTIONS.name());

    private final static int PERMISSION_EXTERNAL_STORAGE = 105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityActionDetailsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolBarDetails);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.md_theme_light_primary));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intentAction = getIntent();
        checkConnectOfWifiOrData(REFRESH_START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_history, menu);
        MenuItem searchItem = menu.findItem(R.id.general_search_item);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.full_report:
                createPdf();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRV() {
        mBinding.progressWhileLoading.setVisibility(View.GONE);
        adapter = new RecyclerViewAdapterDetailsHistory(citizenActionDetails, getBaseContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        mBinding.rvDetails.setLayoutManager(layoutManager);
        mBinding.rvDetails.setAdapter(adapter);
        mBinding.rvDetails.setHasFixedSize(true);
    }

    private void createPdf() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mBinding.getRoot().getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_STORAGE);
            return;
        }
        Report.Pdf.ActionDetails.createDetails(citizenActionDetails , getBaseContext()); ;
    }

    private void checkConnectOfWifiOrData(String comeFrom) {
        if (NetworkCollection.checkConnection(this)) {
            mBinding.activityActionDetailsInternetConnectionTv.setVisibility(View.GONE);
            if (citizenActionDetails.isEmpty()) {
                mBinding.progressWhileLoading.setVisibility(View.VISIBLE);
            } else {
                mBinding.progressWhileLoading.setVisibility(View.GONE);
            }
           getDetails();
        } else {
            if (citizenActionDetails.isEmpty()) {
                mBinding.activityActionDetailsInternetConnectionTv.setVisibility(View.VISIBLE);
                mBinding.progressWhileLoading.setVisibility(View.GONE);

            } else {
                mBinding.activityActionDetailsInternetConnectionTv.setVisibility(View.GONE);
                mBinding.progressWhileLoading.setVisibility(View.GONE);

            }

            if (comeFrom == REFRESH_SWIPE) {
                Snackbar.make(mBinding.getRoot(), getString(R.string.chose_image), Snackbar.LENGTH_LONG).setAction("Check", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                    }
                }).show();

            } else {

            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    private void getDetails() {
            mCollectionRefAction.document(intentAction.getStringExtra(DOCUMENT_ID)).collection(CollectionName.ACTION_DETAILS.name())
                  .whereEqualTo(CollectionName.Fields.deliveredState.name()+"."+CollectionName.Fields.delivered.name(),true )
                   .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.e(TAG, e.toString());
                            }
                            if (queryDocumentSnapshots.isEmpty()) {
                                adapter = null;
                                citizenActionDetails.clear();
                                initRV();
                                mBinding.activityActionsDetailsNoItemTv.setText(getString(R.string.no_item_yet));
                                mBinding.activityActionsDetailsNoItemTv.setVisibility(View.VISIBLE);
                                mBinding.progressWhileLoading.setVisibility(View.GONE);
                                Toast.makeText(getBaseContext(), "no Items", Toast.LENGTH_SHORT).show();
                            } else {
                                adapter = null;
                                citizenActionDetails.clear();
                                mBinding.activityActionsDetailsNoItemTv.setVisibility(View.GONE);
                                for (QueryDocumentSnapshot q : queryDocumentSnapshots) {

                                    CitizenActionDetails actionDetails = q.toObject(CitizenActionDetails.class);
                                    actionDetails.setDocumentId(q.getId());
                                    citizenActionDetails.add(actionDetails);
                                }
                                initRV();
                            }
                        }
                    });
        }


}