package com.GMS.representative.fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.Constant;
import com.GMS.GeneralClasses.CitizenItemClickListener;
import com.GMS.QRScannerActivity;
import com.GMS.R;
import com.GMS.databinding.FragmentNeedScanRepBinding;
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
import com.google.firebase.firestore.SetOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


public class NeedScanRepFragment extends Fragment {

    public static final int FRAGMENT_ID = 1;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference mCollectionRef = db.collection(CollectionName.CITIZENS.name());
    private final CollectionReference mCollectionRefNeighborhood = db.collection(CollectionName.NEIGHBORHOODS.name());
    private final CollectionReference mCollectionRefAction = db.collection(CollectionName.ACTIONS.name());
    private final CollectionReference mCollectionRefActionDetails = db.collection(CollectionName.ACTION_DETAILS.name());
    FragmentNeedScanRepBinding mBinding;
    RecyclerViewRepAdapterCitizen adapter;
    String resultId =null;
    private final static int REP_NEED_SCAN_FRAGMENT=101;
    private final static String TYPE_OF_CHECK="TYPE_OF_CHECK";
    private final static String SEARCH_ALL="SEARCH_ALL";
    private final static String CHECK_ITEM ="CHECK_ITEM";
    private final static String POSITION ="POSITION";
    private  final static String RESULT="RESULT";
    String idAction;
    long sellingPrice;
    CitizenItemClickListener mItemClickListener;
    ArrayList<CitizenActionDetails> detailsItems = new ArrayList<>();

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==REP_NEED_SCAN_FRAGMENT)
                    {
                        Intent intent = result.getData();
                        if(intent != null)
                        {
                            resultId=intent.getStringExtra(RESULT);
                            String checking = intent.getStringExtra(TYPE_OF_CHECK).toString();
                            int position = intent.getIntExtra(POSITION , -1);
                            openQrScanner(checking ,position );
                        }
                    }
                }
            }
    );
    public NeedScanRepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentNeedScanRepBinding.inflate(inflater, container, false);

        getAction();
        mItemClickListener = new CitizenItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(mBinding.getRoot().getContext(), QRScannerActivity.class);
                intent.putExtra(Constant.ACTIVITY.toString() , Constant.REPNEEDSCAN.toString());
                intent.putExtra(TYPE_OF_CHECK , CHECK_ITEM);
                intent.putExtra(POSITION , position);
                activityResultLauncher.launch(intent);
            }
        };
        mBinding.fabScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mBinding.getRoot().getContext(), QRScannerActivity.class);
                intent.putExtra(Constant.ACTIVITY.toString(), Constant.REPNEEDSCAN.toString());
                intent.putExtra(TYPE_OF_CHECK, SEARCH_ALL);
                activityResultLauncher.launch(intent);

            }});


                return mBinding.getRoot();
    }

    public void initRV() {
        mBinding.progressWhileLoading.setVisibility(View.GONE);
        adapter = new RecyclerViewRepAdapterCitizen(detailsItems, FRAGMENT_ID, mItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvNeedScanFragment.setHasFixedSize(true);
        mBinding.rvNeedScanFragment.setLayoutManager(layoutManager);
        mBinding.rvNeedScanFragment.setAdapter(adapter);

    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.general_search_item);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }

        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getAction() {

        mCollectionRefAction.whereEqualTo(CollectionName.Fields.actionDate.name(), String.valueOf(new java.sql.Date(System.currentTimeMillis())))
                .whereEqualTo(CollectionName.Fields.neighborhoodDetails.name() + "." + CollectionName.Fields.name.name(), "Mousa Street")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    idAction = queryDocumentSnapshots.getDocuments().get(0).getId().toString();
                    sellingPrice = queryDocumentSnapshots.getDocuments().get(0).getLong(CollectionName.Fields.sellingPrice.name());

                    getActionDetails();

                } else {
                    mBinding.progressWhileLoading.setVisibility(View.GONE);
                    Toast.makeText(Objects.requireNonNull(getContext()).getApplicationContext(), "no Action today", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void getActionDetails() {
        mCollectionRefAction.document(idAction).collection(CollectionName.ACTION_DETAILS.name())
                .whereEqualTo(CollectionName.Fields.deliveredState.name() + "." + CollectionName.Fields.repVerified, false)
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

    private void verifiedCitizen(int position) {

        CitizenActionDetails citizenActionDetails = detailsItems.get(position);
        Map<String, Object> updateState = citizenActionDetails.getDeliveredState();
        updateState.put(CollectionName.Fields.repVerified.name(), true);
        citizenActionDetails.setDeliveredState(updateState);

        mCollectionRefAction.document(idAction).collection(CollectionName.ACTION_DETAILS.name())
                .document(detailsItems.get(position).getDocumentId()).set(citizenActionDetails, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Verified", Toast.LENGTH_SHORT).show();

                    }
                });

    }
    public  void openQrScanner( String checking , int position)
    {

        if( checking.equals(CHECK_ITEM) && position>-1)
        {
            if(resultId.equals(detailsItems.get(position).getSerialNumber().toString())) {
             verifiedCitizen(position);
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext() , "there is error try again to read QR code", Toast.LENGTH_SHORT).show();
            }


        }
        else if(checking.equals(SEARCH_ALL) && position==-1)
        {
            boolean isAvailable = false;
            int index=-1;
            for(int i =0 ; i<detailsItems.size() ; i++)
            {

                if(resultId.equals(String.valueOf(detailsItems.get(i).getSerialNumber())))
                {
                    Toast.makeText(getActivity().getApplicationContext() , detailsItems.get(i).getQuantityRequired()+"done ", Toast.LENGTH_SHORT).show();
                    index = i ;
                    isAvailable=true;
                    break;
                }
            }
            if (isAvailable)
            {
              verifiedCitizen(index);
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext() , " bbbbb there is error try again to read QR code", Toast.LENGTH_SHORT).show();

            }
        }





    }


}