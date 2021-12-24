package com.GMS;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.GeneralAdapters.RecyclerViewAdapterHistory;
import com.GMS.GeneralClasses.NetworkCollection;
import com.GMS.GeneralClasses.Report;
import com.GMS.GeneralClasses.SingleItemClickListener;
import com.GMS.databinding.ActivityHistoryBinding;
import com.GMS.firebaseFireStore.ActionCollection;
import com.GMS.firebaseFireStore.CollectionName;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding mBinding;
    private static final String REFRESH_SWIPE = "REFRESH_SWIPE";
    private static final String REFRESH_START = "REFRESH_START";
    private static final String DOCUMENT_ID = "DOCUMENT_ID";
    RecyclerViewAdapterHistory adapter;
    SingleItemClickListener mSingleItemClickListener;
    ArrayList<ActionCollection> mHistoryItems = new ArrayList<>();

    private final static int PERMISSION_EXTERNAL_STORAGE = 105;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference mCollectionRefAction = db.collection(CollectionName.ACTIONS.name());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolBarHistory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.md_theme_light_primary));

        mSingleItemClickListener = new SingleItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent ADAIntent = new Intent(getBaseContext(), ActionDetailsActivity.class);
                ADAIntent.putExtra(DOCUMENT_ID, mHistoryItems.get(position).getDocumentId());
                startActivity(ADAIntent);
            }
        };
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

                adapter.getFilter().filter(newText);

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
        adapter = new RecyclerViewAdapterHistory(getBaseContext(), mHistoryItems, mSingleItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        mBinding.rvHistory.setLayoutManager(layoutManager);
        mBinding.rvHistory.setAdapter(adapter);
        mBinding.rvHistory.setHasFixedSize(true);
    }

    private void createPdf() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mBinding.getRoot().getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_STORAGE);
            return;
        }
         Report.Pdf.Actions.createActions(mHistoryItems, getBaseContext());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_EXTERNAL_STORAGE:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getBaseContext(), "granted external storage", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "dismiss external storage", Toast.LENGTH_SHORT).show();

                }
        }
    }

    private void checkConnectOfWifiOrData(String comeFrom) {
        if (NetworkCollection.checkConnection(this)) {
            mBinding.activityHistoryInternetConnectionTv.setVisibility(View.GONE);
            if (mHistoryItems.isEmpty()) {
                mBinding.progressWhileLoading.setVisibility(View.VISIBLE);
            } else {
                mBinding.progressWhileLoading.setVisibility(View.GONE);
            }


            getAction();
        } else {
            if (mHistoryItems.isEmpty()) {
                mBinding.activityHistoryInternetConnectionTv.setVisibility(View.VISIBLE);
                mBinding.progressWhileLoading.setVisibility(View.GONE);

            } else {
                mBinding.activityHistoryInternetConnectionTv.setVisibility(View.GONE);
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

    private void getAction() {

        mCollectionRefAction.whereEqualTo(CollectionName.Fields.actionDate.name(), String.valueOf(new java.sql.Date(System.currentTimeMillis())))
                .whereEqualTo(CollectionName.Fields.neighborhoodDetails.name() + "." + CollectionName.Fields.name.name(), "Mousa Street")
                .whereEqualTo(CollectionName.Fields.agentName.name(), "khalid")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    mBinding.activityHistoryNoItemTv.setVisibility(View.GONE);
                    for (QueryDocumentSnapshot q : queryDocumentSnapshots) {
                        ActionCollection actionCollection = q.toObject(ActionCollection.class);
                        actionCollection.setDocumentId(q.getId());
                        mHistoryItems.add(actionCollection);
                    }
                    initRV();
                } else {
                    mBinding.progressWhileLoading.setVisibility(View.GONE);
                    mBinding.activityHistoryNoItemTv.setVisibility(View.VISIBLE);
                    Toast.makeText(getBaseContext(), "no Action today", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}