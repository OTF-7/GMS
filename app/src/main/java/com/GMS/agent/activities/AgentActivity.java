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
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Switch;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.GMS.GeneralClasses.CitizenItemClickListener;
import com.GMS.GeneralClasses.NetworkCollection;
import com.GMS.HistoryActivity;
import com.GMS.R;
import com.GMS.SettingActivity;
import com.GMS.agent.adapters.RecyclerViewAdapterCitizen;
import com.GMS.databinding.ActivityAgentBinding;
import com.GMS.firebaseFireStore.CitizenActionDetails;
import com.GMS.firebaseFireStore.CollectionName;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Transaction;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

public class AgentActivity extends AppCompatActivity {

    //MainAdapter adapter;

    RecyclerViewAdapterCitizen adapter;

    private ActivityAgentBinding mBinding;

    public static Drawable mAcceptedBackgroundImage;
    private Dialog stationDialog, receivingMoneyDialog;
    private static final int CALL_PERMISSION = 122;
    private static final String REFRESH_SWIPE = "REFRESH_SWIPE";
    private static final String REFRESH_START = "REFRESH_START";
    String idAction;
    long sellingPrice;
    long Qty;
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
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.md_theme_light_primary));
        createReceivingMoneyDialog();
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
                showReceivingMoneyDialog(position);
            }
        };

        mBinding.activityAgentSwipeRefreshRv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkConnectOfWifiOrData(REFRESH_SWIPE);
                mBinding.activityAgentSwipeRefreshRv.setRefreshing(false);
                mBinding.activityAgentNoItemTv.setVisibility(View.GONE);
            }
        });


    }

    public void initRV() {
        mBinding.progressWhileLoading.setVisibility(View.GONE);
        adapter = new RecyclerViewAdapterCitizen(detailsItems, getBaseContext(), mItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mBinding.rvCitizen.setHasFixedSize(true);
        mBinding.rvCitizen.setLayoutManager(layoutManager);
        mBinding.rvCitizen.setAdapter(adapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        checkConnectOfWifiOrData(REFRESH_START);

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
        MenuItem searchItem = menu.findItem(R.id.general_search_item);
        SearchView searchView = (SearchView) searchItem.getActionView();
        createDialog();
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(getBaseContext(), "closed", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter == null)
                    return false;
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
                    mBinding.activityAgentNoItemTv.setVisibility(View.GONE);
                    idAction = queryDocumentSnapshots.getDocuments().get(0).getId();
                    sellingPrice = queryDocumentSnapshots.getDocuments().get(0).getLong(CollectionName.Fields.sellingPrice.name());
                    mBinding.stationName.setText(queryDocumentSnapshots.getDocuments().get(0).getString(CollectionName.Fields.stationDetails.name() + "." + CollectionName.Fields.stationName.name()));
                    mBinding.stationCount.setText(String.valueOf(queryDocumentSnapshots.getDocuments().get(0).getLong(CollectionName.Fields.deliveredQuantity.name())));
                    mBinding.aqelName.setText(queryDocumentSnapshots.getDocuments().get(0).getString(CollectionName.Fields.aqelName.name()));
                    mBinding.countAqel.setText(String.valueOf(queryDocumentSnapshots.getDocuments().get(0).getLong(CollectionName.Fields.aqelCount.name())));
                    mCollectionRefAction.document(idAction).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (error != null) {
                                Log.e(TAG, error.toString());
                            }
                            if(value.exists())
                            {
                               mBinding.countAgent.setText(String.valueOf(value.getLong(CollectionName.Fields.neighborhoodDetails
                                       .name()+"."+CollectionName.Fields.numberOfDelivered.name())));
                            }
                        }
                    });
                    getActionDetails();

                } else {
                    mBinding.activityAgentNoItemTv.setText(getString(R.string.no_action_yet));
                    mBinding.activityAgentNoItemTv.setVisibility(View.VISIBLE);
                    mBinding.progressWhileLoading.setVisibility(View.GONE);

                }
            }
        });
    }

    private void getActionDetails() {
        mCollectionRefAction.document(idAction).collection(CollectionName.ACTION_DETAILS.name())
                .whereEqualTo(CollectionName.Fields.deliveredState.name() + "." + CollectionName.Fields.aqelVerified, true)
                .whereEqualTo(CollectionName.Fields.deliveredState.name() + "." + CollectionName.Fields.repVerified, true)
                .whereEqualTo(CollectionName.Fields.deliveredState.name() + "." + CollectionName.Fields.received, false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e(TAG, e.toString());
                        }
                        if (queryDocumentSnapshots.isEmpty()) {
                            adapter = null;
                            detailsItems.clear();
                            initRV();
                            mBinding.activityAgentNoItemTv.setText(getString(R.string.no_item_yet));
                            mBinding.activityAgentNoItemTv.setVisibility(View.VISIBLE);
                            mBinding.progressWhileLoading.setVisibility(View.GONE);
                            Toast.makeText(getBaseContext(), "no Items", Toast.LENGTH_SHORT).show();
                        } else {
                            adapter = null;
                            detailsItems.clear();
                            mBinding.activityAgentNoItemTv.setVisibility(View.GONE);
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

    private void checkConnectOfWifiOrData(String comeFrom) {
        if (NetworkCollection.checkConnection(this)) {
            mBinding.activityAgentNoItemTv.setVisibility(View.GONE);
            mBinding.activityAgentInternetConnectionTv.setVisibility(View.GONE);
            if (detailsItems.isEmpty()) {

                mBinding.progressWhileLoading.setVisibility(View.VISIBLE);
            } else {
                mBinding.progressWhileLoading.setVisibility(View.GONE);
            }

            getAction();
        } else {
            mBinding.activityAgentNoItemTv.setVisibility(View.GONE);
            if (detailsItems.isEmpty()) {
                mBinding.activityAgentInternetConnectionTv.setVisibility(View.VISIBLE);
                mBinding.progressWhileLoading.setVisibility(View.GONE);

            } else {
                mBinding.activityAgentInternetConnectionTv.setVisibility(View.GONE);
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

    private void createReceivingMoneyDialog() {
        receivingMoneyDialog = new Dialog(mBinding.getRoot().getContext());
        receivingMoneyDialog.setContentView(R.layout.dialog_recieving_money);
        receivingMoneyDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Window window = receivingMoneyDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        //  window.getAttributes().windowAnimations = R.style.FadeDialogAnimation;
        receivingMoneyDialog.setCancelable(false);

        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    private void showReceivingMoneyDialog(int position) {


        receivingMoneyDialog.show();
        receivingMoneyDialog.findViewById(R.id.tv_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptData(position);
            }
        });
        receivingMoneyDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receivingMoneyDialog.dismiss();
            }
        });

    }

    private void acceptData(int position) {
        CitizenActionDetails citizenActionDetails = detailsItems.get(position);
        Qty=citizenActionDetails.getDeliveredQuantity();
        Map<String, Object> updateState = citizenActionDetails.getDeliveredState();
        updateState.put(CollectionName.Fields.received.name(), true);
        citizenActionDetails.setDeliveredState(updateState);
        receivingMoneyDialog.dismiss();
        mCollectionRefAction.document(idAction).collection(CollectionName.ACTION_DETAILS.name())
                .document(detailsItems.get(position).getDocumentId()).set(citizenActionDetails, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        executeTransaction();
                        Toast.makeText(mBinding.getRoot().getContext(), "have received", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void execTransaction() {
        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentReference docRef = mCollectionRefAction.document(idAction);
                DocumentSnapshot documentSnapshot = transaction.get(docRef);
                long qty = documentSnapshot.getLong(CollectionName.Fields.neighborhoodDetails.name()+"."+CollectionName.Fields.numberOfDelivered.name()) + Qty;
                transaction.update(docRef, CollectionName.Fields.aqelCount.name(), qty);
                return null;
            }
        });
    }
    private void  executeTransaction()
    {
        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentReference docRef = mCollectionRefAction.document(idAction);
                DocumentSnapshot documentSnapshot = transaction.get(docRef);
                long qty = documentSnapshot.getLong(CollectionName.Fields.neighborhoodDetails.name()+"."+CollectionName.Fields.numberOfDelivered.name()) + Qty;
                transaction.update(docRef, CollectionName.Fields.neighborhoodDetails.name()+"."+CollectionName.Fields.numberOfDelivered.name(), qty);

                return null;
            }
        });
    }
}
