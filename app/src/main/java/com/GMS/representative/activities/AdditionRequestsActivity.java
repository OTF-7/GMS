package com.GMS.representative.activities;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.databinding.ActivityAdditionRequestsBinding;
import com.GMS.firebaseFireStore.CitizenCollection;
import com.GMS.firebaseFireStore.CollectionName;
import com.GMS.representative.adapters.AdditionRequestRecyclerViewAdapter;
import com.GMS.representative.helperClass.RepresentativeClickListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class AdditionRequestsActivity extends AppCompatActivity {

     private ScaleGestureDetector scaleGestureDetector ;
     float scaleFactor=1.0f;
    private static final String TAG_ADDITION_REQUEST_RECYCLE ="TAG_ADDITION_REQUEST_RECYCLE" ;
    ActivityAdditionRequestsBinding mBinding;
    AdditionRequestRecyclerViewAdapter adapter;
    private final ArrayList<CitizenCollection> citizenCollectionItems = new ArrayList<>();
    private TextView tvCitizenName, tvNeighborhood;
    private ImageView ivDocument;
    private RepresentativeClickListener mRepresentativeClickListener;
    private Dialog mDialog;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference mCollectionRef = db.collection(CollectionName.CITIZENS.name());
    private final CollectionReference mCollectionRefNeighborhood = db.collection(CollectionName.NEIGHBORHOODS.name());
    String  id ;
    private static final int pendingNotification = 0;
    Intent intent = new Intent();
    private final String TAG_ADDITION_REQUEST_COMING_DATA="TAG_ADDITION_REQUEST_COMING_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAdditionRequestsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolBar);
        setTitle("Addition Requests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        notificationsCount();
        intent = getIntent();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_addition_requsts_top_bar, menu);
        MenuItem item = menu.findItem(R.id.menu_agent_search_ic);
        SearchView mSearchView = (SearchView) item.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                notificationsCount();
                this.finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    // this function make initialization of recyclerview and addapter *look(updates)*
    private void initRV() {
        try {
            adapter = new AdditionRequestRecyclerViewAdapter(citizenCollectionItems, mRepresentativeClickListener);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
            mBinding.rvAdditionRequests.setHasFixedSize(true);
            mBinding.rvAdditionRequests.setLayoutManager(layoutManager);
            mBinding.rvAdditionRequests.setAdapter(adapter);
            mBinding.infoRequests.setVisibility(View.GONE);
            mBinding.progressWhileLoading.setVisibility(View.GONE);
        }
        catch (Exception ex)
        {
            Log.e(TAG_ADDITION_REQUEST_RECYCLE,ex.getMessage());
        }
    }

    private void createDialog(int position) {
        mDialog = new Dialog(mBinding.getRoot().getContext());
        mDialog.setContentView(R.layout.see_document_dialog);
        mDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        mDialog.setCancelable(true);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        // inflate the elements of dialog
        tvCitizenName = mDialog.findViewById(R.id.tv_citizen_name_dialog);
        tvNeighborhood = mDialog.findViewById(R.id.tv_neighborhood_dialog);
        ivDocument = mDialog.findViewById(R.id.iv_document);
        // set the values for views
        tvCitizenName.setText(citizenCollectionItems.get(position).getFullName());
        tvNeighborhood.setText(citizenCollectionItems.get(position).getNeighborhood());
        ivDocument.setImageResource(R.drawable.ic_document);
        Picasso.with(mDialog.getContext())
                .load(citizenCollectionItems.get(position).getDocumentUrl())
                .fit()
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(ivDocument);
        //scaleGestureDetector = new ScaleGestureDetector( mDialog.getContext(), new ScaleListener());

    }

    private void showDialog() {
        mDialog.show();
        mDialog.findViewById(R.id.close_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

    }

    private void confirmAdditionRequest(int position) {

        CitizenCollection citizenCollection = citizenCollectionItems.get(position);
        citizenCollection.setState(true);
        // citizenCollection.setAdditionDetails((Map<String, Object>) citizenCollection.getAdditionDetails().put(CollectionName.Fields.dateCertain.name(),String.valueOf(new java.sql.Date(System.currentTimeMillis()))));
        // citizenCollection.setAdditionDetails((Map<String, Object>) citizenCollection.getAdditionDetails().put(CollectionName.Fields.dateCertain.name(),String.valueOf(new java.sql.Date(System.currentTimeMillis()))));
        HashMap<String, Object> confirmation = citizenCollection.getAdditionDetails();
        confirmation.put(CollectionName.Fields.dateCertain.name(), String.valueOf(new java.sql.Date(System.currentTimeMillis())));
        confirmation.put(CollectionName.Fields.representativeCertain.name(), "Abdulrahman khalid M");
        citizenCollection.setAdditionDetails(confirmation);
        confirmation = null;
        mCollectionRefNeighborhood.document(id).collection(CollectionName.CITIZENS.name()).document(citizenCollection.getDocumentId()).set(citizenCollection, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        executeNumOfFamilyTransaction();
                        Toast.makeText(getBaseContext(), "Confirmed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void regretAdditionRequest(int position) {
        CitizenCollection citizenCollection = citizenCollectionItems.get(position);
        citizenCollection.setRegret(true);
        //  citizenCollection.setAdditionDetails((Map<String, Object>) citizenCollection.getAdditionDetails().put(CollectionName.Fields.dateCertain.name(),String.valueOf(new java.sql.Date(System.currentTimeMillis()))));
        // citizenCollection.setAdditionDetails((Map<String, Object>) citizenCollection.getAdditionDetails().put(CollectionName.Fields.dateCertain.name(),String.valueOf(new java.sql.Date(System.currentTimeMillis()))));
        HashMap<String, Object> regret = citizenCollection.getAdditionDetails();
        regret.put(CollectionName.Fields.dateCertain.name(), String.valueOf(new java.sql.Date(System.currentTimeMillis())));
        regret.put(CollectionName.Fields.representativeCertain.name(), "Abdulrahman khalid M");
        citizenCollection.setAdditionDetails(regret);
        regret = null;
        mCollectionRef.document(citizenCollection.getDocumentId()).set(citizenCollection, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getBaseContext(), "Confirmed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void notificationsCount() {
        intent.putExtra("pendingNotification", pendingNotification);
        setResult(RepresentativeActivity.ADDITION_REQUEST_REQ_CODE, intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

       mCollectionRefNeighborhood.whereEqualTo(CollectionName.Fields.name.name(), "Mousa Street")
               .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               if(!queryDocumentSnapshots.isEmpty())
               {
                       id=queryDocumentSnapshots.getDocuments().get(0).getId().toString();
                   mCollectionRefNeighborhood.document(id).collection(CollectionName.CITIZENS.name()).whereEqualTo(CollectionName.Fields.state.name(), false)
                           .addSnapshotListener(new EventListener<QuerySnapshot>() {
                               @Override
                               public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                   if (e != null) {
                                       Log.e(TAG, e.toString());
                                   }

                        if (queryDocumentSnapshots.isEmpty()) {
                                   mBinding.progressWhileLoading.setVisibility(View.GONE);
                                   mBinding.infoRequests.setVisibility(View.VISIBLE);
                                   mBinding.rvAdditionRequests.setVisibility(View.GONE);
                               } else {
                            try {

                                mBinding.rvAdditionRequests.setVisibility(View.VISIBLE);
                                citizenCollectionItems.clear();
                                adapter = null;
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    CitizenCollection citizenDocument = documentSnapshot.toObject(CitizenCollection.class);
                                    citizenDocument.setDocumentId(documentSnapshot.getId());
                                    citizenCollectionItems.add(citizenDocument);
                                }
                            }catch (Exception ex)
                            {
                                Log.e(TAG_ADDITION_REQUEST_COMING_DATA , ex.getMessage().toString());
                                Toast.makeText(getBaseContext() ,ex.getMessage().toString() , Toast.LENGTH_SHORT).show();
                            }
                                   mRepresentativeClickListener = new RepresentativeClickListener() {
                                       @Override
                                       public void onClick(int position, String tvItem) {
                                           if (tvItem == getString(R.string.see_document)) {
                                               createDialog(position);
                                               showDialog();
                                           } else if (tvItem == getString(R.string.confirm)) {
                                               confirmAdditionRequest(position);
                                           } else if (tvItem == getString(R.string.regret)) {
                                               regretAdditionRequest(position);
                                           }
                                       }

                                   };
                                   initRV();
                               }
                               }
                           });
               }
           }
       });




    }

    private  void executeNumOfFamilyTransaction()
    {
        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentReference docRef = mCollectionRefNeighborhood.document(id);
                DocumentSnapshot docSnap = transaction.get(docRef);
                long numberOfFamilies = docSnap.getLong(CollectionName.Fields.numberOfFamilies.name().toString())+1;
                transaction.update(docRef ,CollectionName.Fields.numberOfFamilies.name().toString() ,numberOfFamilies);
                return null;
            }
        });
    }
    /* // not working
    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
             scaleFactor = Math.max(0.1f ,Math.min(scaleFactor ,10.f));
             ivDocument.setScaleX(scaleFactor);
             ivDocument.setScaleY(scaleFactor);

            return true;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

     */
}