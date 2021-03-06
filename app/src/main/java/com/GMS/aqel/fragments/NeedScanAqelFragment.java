package com.GMS.aqel.fragments;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.Constant;
import com.GMS.GeneralClasses.CitizenItemClickListener;
import com.GMS.QRScannerActivity;
import com.GMS.R;
import com.GMS.SettingActivity;
import com.GMS.aqel.activities.AddCitizenActivity;
import com.GMS.aqel.activities.AqelNotificationsActivity;
import com.GMS.aqel.adapters.RecyclerViewAqelAdapter;
import com.GMS.aqel.helperClass.CitizenItemOfAqel;
import com.GMS.databinding.FragmentNeedScanAqelBinding;
import com.GMS.firebaseFireStore.CitizenActionDetails;
import com.GMS.firebaseFireStore.CollectionName;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
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
import java.util.HashMap;
import java.util.Map;

public class NeedScanAqelFragment extends Fragment {

    public static final int FRAGMENT_ID = 1;
    MenuItem mMenuItemNotification;
    TextView tvNotificationCounter;
    ImageView ivNotificationIcon;
    public static int pendingNotification = 0;
    private final String AQEL_NOTIFICATION = "AQEL_NOTIFICATION";
    FragmentNeedScanAqelBinding mBinding;
    RecyclerViewAqelAdapter adapter;
    String resultId = null;
    ArrayList<CitizenItemOfAqel> items = new ArrayList<>();
    private final static String SEARCH_ALL = "SEARCH_ALL";
    private final static String CHECK_ITEM = "CHECK_ITEM";
    ArrayList<CitizenActionDetails> detailsItems = new ArrayList<>();
    CitizenItemClickListener mItemClickListener;
    private final static int AQEL_NEED_SCAN_FRAGMENT = 101;
    private final static String RESULT = "RESULT";
    private Dialog mDialog;
    private final static String TYPE_OF_CHECK = "TYPE_OF_CHECK";
    private final static String POSITION = "POSITION";
    private TextInputEditText textInputEditTextQTY;
    private TextView tvTotal, tvRequiredQty;
    String idAction;

    long sellingPrice;
    private static int Qty;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference mCollectionRef = db.collection(CollectionName.CITIZENS.name());
    private final CollectionReference mCollectionRefNeighborhood = db.collection(CollectionName.NEIGHBORHOODS.name());
    private final CollectionReference mCollectionRefAction = db.collection(CollectionName.ACTIONS.name());
    private final CollectionReference mCollectionRefActionDetails = db.collection(CollectionName.ACTION_DETAILS.name());

    private String id;
    // this for dealing with Runnable an clean it
    Handler mHandler = new Handler();

    public NeedScanAqelFragment() {
        // Required empty public constructor
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == AQEL_NEED_SCAN_FRAGMENT) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            resultId = intent.getStringExtra(RESULT);
                            String checking = intent.getStringExtra(TYPE_OF_CHECK);
                            int position = intent.getIntExtra(POSITION, -1);
                            openQrScanner(checking, position);
                        }
                    }
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentNeedScanAqelBinding.inflate(inflater, container, false);
        createDialog();
        // addActionDetails();
        getAction();

        mBinding.fabScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mBinding.getRoot().getContext(), QRScannerActivity.class);
                intent.putExtra(Constant.ACTIVITY.toString(), Constant.AQELNEEDSCANFRAGNENT.toString());
                intent.putExtra(TYPE_OF_CHECK, SEARCH_ALL);
                activityResultLauncher.launch(intent);


            }
        });


        mItemClickListener = new CitizenItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(mBinding.getRoot().getContext(), QRScannerActivity.class);
                intent.putExtra(Constant.ACTIVITY.toString(), Constant.AQELNEEDSCANFRAGNENT.toString());
                intent.putExtra(TYPE_OF_CHECK, CHECK_ITEM);
                intent.putExtra(POSITION, position);
                activityResultLauncher.launch(intent);


            }
        };

        return mBinding.getRoot();
    }

    public void openQrScanner(String checking, int position) {

        if (checking.equals(CHECK_ITEM) && position > -1) {
            if (resultId.equals(detailsItems.get(position).getIdInParent())) {
                tvRequiredQty.setText(String.valueOf(detailsItems.get(position).getQuantityRequired()));
                showDialog(position);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "there is error try again to read QR code", Toast.LENGTH_SHORT).show();
            }


        } else {
            checkingItm();
        }


    }

    private void checkingItm() {
        boolean isAvailable = false;
        int position = -1;
        for (int i = 0; i < detailsItems.size(); i++) {
            if (resultId.equals(detailsItems.get(i).getIdInParent())) {
                tvRequiredQty.setText(String.valueOf(detailsItems.get(i).getQuantityRequired()));
                position = i;
                isAvailable = true;
                break;
            }
        }
        if (isAvailable) {
            showDialog(position);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "there is error try again to read QR code", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_aqel_top_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.aqel_search_ic);
        SearchView searchView = (SearchView) searchItem.getActionView();
        mMenuItemNotification = menu.findItem(R.id.menu_aqel_notification);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (adapter != null)
                    adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null)
                    adapter.getFilter().filter(newText);
                return false;
            }

        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_citizen:
                Intent mAddCitizenIntent = new Intent(this.getActivity(), AddCitizenActivity.class);
                startActivity(mAddCitizenIntent);
                break;
            case R.id.setting_item:
                Intent mSettingIntent = new Intent(this.getActivity(), SettingActivity.class);
                startActivity(mSettingIntent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void createDialog() {
        mDialog = new Dialog(mBinding.getRoot().getContext());
        mDialog.setContentView(R.layout.accept_qr_scanner_dialoge);
        mDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        mDialog.setCancelable(true);
        textInputEditTextQTY = mDialog.findViewById(R.id.quantity_text_input);
        tvTotal = mDialog.findViewById(R.id.tv_price);
        tvRequiredQty = mDialog.findViewById(R.id.tv_required_Qty);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    private void showDialog(int position) {
        mDialog.show();
        mRunnable.run();
        mDialog.findViewById(R.id.close_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(mRunnable);
                mDialog.dismiss();
            }
        });
        mDialog.findViewById(R.id.btn_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptData(position);

            }
        });
    }

    private void acceptData(int position) {
        if (textInputEditTextQTY.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "no Quantity", Toast.LENGTH_SHORT).show();
        } else if (Integer.valueOf(textInputEditTextQTY.getText().toString().trim()) > detailsItems.get(position).getQuantityRequired()) {
            Toast.makeText(getActivity(), "qty is so much", Toast.LENGTH_SHORT).show();
        } else {
            Qty = Integer.valueOf(textInputEditTextQTY.getText().toString().trim());
            CitizenActionDetails citizenActionDetails = detailsItems.get(position);
            citizenActionDetails.setDeliveredQuantity((int) Qty);
            citizenActionDetails.setTotal((int) (sellingPrice * Qty));
            Map<String, Object> updateState = citizenActionDetails.getDeliveredState();
            updateState.put(CollectionName.Fields.aqelVerified.name(), true);
            citizenActionDetails.setDeliveredState(updateState);
            Qty = Integer.valueOf(textInputEditTextQTY.getText().toString());
            mHandler.removeCallbacks(mRunnable);
            mDialog.dismiss();
            mCollectionRefAction.document(idAction).collection(CollectionName.ACTION_DETAILS.name())
                    .document(detailsItems.get(position).getDocumentId()).set(citizenActionDetails, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "Verified", Toast.LENGTH_SHORT).show();

                            executeTransaction();

                        }

                    });
            // Toast.makeText(getActivity(), String.valueOf(Qty*sellingPrice), Toast.LENGTH_SHORT).show();
        }
    }

    // this realtime listen for changes with editText at the dialog
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (textInputEditTextQTY.getText().toString().trim().equals("")) {
                tvTotal.setText(getString(R.string.total) + " " + 0);
            } else {
                tvTotal.setText(getString(R.string.total) + " " + Integer.valueOf(textInputEditTextQTY.getText().toString()) * sellingPrice);
            }
            mHandler.postDelayed(this, 500);
        }
    };

    private void initRV() {
        mBinding.progressWhileLoading.setVisibility(View.GONE);
        adapter = new RecyclerViewAqelAdapter(detailsItems, FRAGMENT_ID, mItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvNeedScanFragment.setHasFixedSize(true);
        mBinding.rvNeedScanFragment.setLayoutManager(layoutManager);
        mBinding.rvNeedScanFragment.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void getAction() {

        mCollectionRefAction.whereEqualTo(CollectionName.Fields.actionDate.name(), String.valueOf(new java.sql.Date(System.currentTimeMillis())))
                .whereEqualTo(CollectionName.Fields.neighborhoodDetails.name() + "." + CollectionName.Fields.name.name(), "Mousa Street")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    pendingNotification = 1;

                    idAction = queryDocumentSnapshots.getDocuments().get(0).getId();
                    sellingPrice = queryDocumentSnapshots.getDocuments().get(0).getLong(CollectionName.Fields.sellingPrice.name());
                    getActionDetails();

                } else {
                    mBinding.progressWhileLoading.setVisibility(View.GONE);
                    Toast.makeText(getContext().getApplicationContext(), "no Action today", Toast.LENGTH_SHORT).show();

                }
                checkNotification();
            }
        });

    }

    private void getActionDetails() {
        mCollectionRefAction.document(idAction).collection(CollectionName.ACTION_DETAILS.name())
                .whereEqualTo(CollectionName.Fields.deliveredState.name() + "." + CollectionName.Fields.aqelVerified.name(), false)
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
                            mBinding.progressWhileLoading.setVisibility(View.GONE);
                            Toast.makeText(getContext().getApplicationContext(), "no Items", Toast.LENGTH_SHORT).show();
                        } else {
                            adapter = null;
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

    private void addActionDetails() {
        Toast.makeText(getContext().getApplicationContext(), "addDetails", Toast.LENGTH_SHORT).show();
        mCollectionRefNeighborhood.whereEqualTo(CollectionName.Fields.name.name(),
                "Mousa Street").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            String id;
            String actionId;

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot q : queryDocumentSnapshots) {
                        id = q.getId();
                        break;
                    }
                    mCollectionRefAction.whereEqualTo(CollectionName.Fields.actionDate.name(), String.valueOf(new java.sql.Date(System.currentTimeMillis()))).get().addOnSuccessListener(
                            new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot q : queryDocumentSnapshots) {
                                        actionId = q.getId();
                                        break;
                                    }
                                    mCollectionRefNeighborhood.document(id).collection(CollectionName.CITIZENS.name()).whereEqualTo(CollectionName.Fields.state.name(), true).get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                    if (!queryDocumentSnapshots.isEmpty()) {

                                                        Map<String, Object> deliveredState = new HashMap<>();
                                                        deliveredState.put(CollectionName.Fields.aqelVerified.name(), false);
                                                        deliveredState.put(CollectionName.Fields.repVerified.name(), false);
                                                        deliveredState.put(CollectionName.Fields.delivered.name(), false);
                                                        deliveredState.put(CollectionName.Fields.received.name(), false);

                                                        for (QueryDocumentSnapshot q : queryDocumentSnapshots) {
                                                            detailsItems.add(new CitizenActionDetails(q.getId(),
                                                                    q.getString(CollectionName.Fields.serialNumber.name()),
                                                                    q.getString(CollectionName.Fields.firstName.name()),
                                                                    q.getString(CollectionName.Fields.secondName.name()),
                                                                    q.getString(CollectionName.Fields.lastName.name())
                                                                    , 0, 0.0, String.valueOf(new java.sql.Date(System.currentTimeMillis())),
                                                                    ""
                                                                    , deliveredState, q.getLong(CollectionName.Fields.numberOfCylinders.name())));
                                                        }
                                                        for (CitizenActionDetails c : detailsItems) {
                                                            mCollectionRefAction.document(actionId).collection(CollectionName.ACTION_DETAILS.name()).add(c)
                                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentReference documentReference) {
                                                                            Toast.makeText(getContext().getApplicationContext(), "data have gotten", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    });
                                                        }
                                                    }
                                                }
                                            });

                                }
                            }
                    );
                }
            }
        });

    }


    private void executeTransaction() {
        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentReference docRef = mCollectionRefAction.document(idAction);
                DocumentSnapshot documentSnapshot = transaction.get(docRef);
                long qty = documentSnapshot.getLong(CollectionName.Fields.aqelCount.name()) + Qty;
                transaction.update(docRef, CollectionName.Fields.aqelCount.name(), qty);
                return null;
            }
        });
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
                Drawable mDrawable = getActivity().getDrawable(R.drawable.notification_counter_shape);
                view.findViewById(R.id.card_view).setBackground(mDrawable);
                tvNotificationCounter.setText(String.valueOf(pendingNotification));
            }
        } catch (Exception ex) {
            Log.e(AQEL_NOTIFICATION, ex.getMessage());
        }
    }

    private void openAdditionRequestActivity() {
        Intent intent = new Intent(mBinding.getRoot().getContext(), AqelNotificationsActivity.class);
        startActivity(intent);
    }
}