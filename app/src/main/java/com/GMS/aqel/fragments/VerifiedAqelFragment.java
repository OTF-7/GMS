package com.GMS.aqel.fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.GMS.SettingActivity;
import com.GMS.aqel.activities.AddCitizenActivity;
import com.GMS.aqel.adapters.RecyclerViewAqelAdapter;
import com.GMS.databinding.FragmentVerifiedAqelBinding;
import com.GMS.firebaseFireStore.CitizenActionDetails;
import com.GMS.firebaseFireStore.CollectionName;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VerifiedAqelFragment extends Fragment {

    FragmentVerifiedAqelBinding mBinding;
    RecyclerViewAqelAdapter adapter;
    public static final int FRAGMENT_ID=1;

    ArrayList<CitizenActionDetails> detailsItems = new ArrayList<>();
    CitizenItemClickListener mItemClickListener;
    long sellingPrice ;
    private static int Qty;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference mCollectionRef = db.collection(CollectionName.CITIZENS.name());
    private final CollectionReference mCollectionRefNeighborhood = db.collection(CollectionName.NEIGHBORHOODS.name());
    private final CollectionReference mCollectionRefAction = db.collection(CollectionName.ACTIONS.name());
    private final CollectionReference mCollectionRefActionDetails = db.collection(CollectionName.ACTION_DETAILS.name());
    String idAction;
    private String id;
    public VerifiedAqelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentVerifiedAqelBinding.inflate(inflater , container , false);
       getAction();
        mItemClickListener = new CitizenItemClickListener() {
            @Override
            public void onClick(int position) {
                cancelTheVerification(position);
                Toast.makeText(getContext(), ""+position, Toast.LENGTH_SHORT).show();
                }
        };

        return mBinding.getRoot();

    }
    private void cancelTheVerification(int position)
    {
        int count = detailsItems.get(position).getDeliveredQuantity();
        Map<String , Object> deliveredState= new HashMap<>();
        deliveredState = detailsItems.get(position).getDeliveredState();
        if(!((Boolean) deliveredState.get(CollectionName.Fields.delivered.name()))&& !((Boolean) deliveredState.get(CollectionName.Fields.received.name()))) {
            deliveredState.put(CollectionName.Fields.aqelVerified.name(), false);
            mCollectionRefAction.document(idAction).collection(CollectionName.ACTION_DETAILS.name()).document(detailsItems.get(position).getDocumentId())
                    .update(CollectionName.Fields.deliveredState.name(), deliveredState)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(@NonNull Void unused) {
                    executeTransaction(count);
                }
            });

        }
        else
        {
            Toast.makeText(mBinding.getRoot().getContext(), "impossible to canceled", Toast.LENGTH_SHORT).show();
        }

    }
    private void initRV()
    {
        mBinding.progressWhileLoading.setVisibility(View.GONE);
        adapter = new RecyclerViewAqelAdapter(detailsItems , FRAGMENT_ID , mItemClickListener);
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
        inflater.inflate(R.menu.menu_aqel_top_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.aqel_search_ic);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.add_citizen :
                Intent mAddCitizenIntent = new Intent(this.getActivity()  , AddCitizenActivity.class);
                startActivity(mAddCitizenIntent);
                break;
            case R.id.setting_item:
                Intent mSettingIntent = new Intent(this.getActivity() , SettingActivity.class);
                startActivity(mSettingIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
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
                .whereEqualTo(CollectionName.Fields.deliveredState.name() + "." + CollectionName.Fields.aqelVerified, true)
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
                           }

                        }
                        initRV();
                    }

                });
    }

    private void executeTransaction(int count)

    {
        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentReference docRef=mCollectionRefAction.document(idAction) ;
                DocumentSnapshot documentSnapshot =transaction.get(docRef);
                long qty =  documentSnapshot.getLong(CollectionName.Fields.aqelCount.name().toString())-count;
                transaction.update(docRef ,CollectionName.Fields.aqelCount.name().toString() , qty);
                return null;
            }
        });
    }
}