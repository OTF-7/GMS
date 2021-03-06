package com.GMS.representative.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.GeneralClasses.CitizenItemClickListener;
import com.GMS.R;
import com.GMS.databinding.FragmentVerifiedRepBinding;
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

public class VerifiedRepFragment extends Fragment {


    public static final int FRAGMENT_ID = 2;
    RecyclerViewRepAdapterCitizen adapter;
    FragmentVerifiedRepBinding mBinding;
    CitizenItemClickListener mItemClickListener;
    long sellingPrice ;
    private static int Qty;
    ArrayList<CitizenActionDetails> detailsItems = new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference mCollectionRef = db.collection(CollectionName.CITIZENS.name());
    private final CollectionReference mCollectionRefNeighborhood = db.collection(CollectionName.NEIGHBORHOODS.name());
    private final CollectionReference mCollectionRefAction = db.collection(CollectionName.ACTIONS.name());
    private final CollectionReference mCollectionRefActionDetails = db.collection(CollectionName.ACTION_DETAILS.name());
    String idAction;
    public VerifiedRepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentVerifiedRepBinding.inflate(inflater, container, false);

     getAction();
        mItemClickListener = new CitizenItemClickListener() {
            @Override
            public void onClick(int position) {
               Toast.makeText(mBinding.getRoot().getContext() , "not yet" , Toast.LENGTH_SHORT).show();
            }
        };
        return mBinding.getRoot();
    }
    private void initRV()
    {
        mBinding.progressWhileLoading.setVisibility(View.GONE);
        adapter = new RecyclerViewRepAdapterCitizen(detailsItems, FRAGMENT_ID , mItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvVerifiedFragment.setHasFixedSize(true);
        mBinding.rvVerifiedFragment.setLayoutManager(layoutManager);
        mBinding.rvVerifiedFragment.setAdapter(adapter);

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
                    idAction = queryDocumentSnapshots.getDocuments().get(0).getId();
                    sellingPrice= queryDocumentSnapshots.getDocuments().get(0).getLong(CollectionName.Fields.sellingPrice.name().toString());

                    getActionDetails();

                } else {
                    mBinding.progressWhileLoading.setVisibility(View.GONE);
                    Toast.makeText(getContext().getApplicationContext(), "no Action today", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    private void getActionDetails() {
        mCollectionRefAction.document(idAction).collection(CollectionName.ACTION_DETAILS.name())
                .whereEqualTo(CollectionName.Fields.deliveredState.name() + "." + CollectionName.Fields.repVerified, true)

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.e(TAG, e.toString());
                        }
                        if (queryDocumentSnapshots.isEmpty()) {
                            adapter= null ;
                            detailsItems.clear();
                            mBinding.progressWhileLoading.setVisibility(View.GONE);
                            Toast.makeText(getContext().getApplicationContext(), "no Items", Toast.LENGTH_SHORT).show();
                        } else {
                            adapter = null ;
                            detailsItems.clear();
                            for (QueryDocumentSnapshot q : queryDocumentSnapshots) {

                                CitizenActionDetails actionDetails = q.toObject(CitizenActionDetails.class);
                                actionDetails.setDocumentId(q.getId());
                                detailsItems.add(actionDetails);
                                Toast.makeText(getContext().getApplicationContext(), q.getId(), Toast.LENGTH_SHORT).show();

                            }
                            initRV();
                        }
                    }
                });
    }
}