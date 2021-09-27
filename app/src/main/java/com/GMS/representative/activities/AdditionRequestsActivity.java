package com.GMS.representative.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.databinding.ActivityAdditionRequestsBinding;
import com.GMS.representative.adapters.AdditionRequestRecyclerViewAdapter;
import com.GMS.representative.helperClass.CitizenAdditionRequest;
import com.GMS.representative.helperClass.RepresentativeClickListener;

import java.util.ArrayList;

public class AdditionRequestsActivity extends AppCompatActivity {

    ActivityAdditionRequestsBinding mBinding;
    AdditionRequestRecyclerViewAdapter adapter;
    private final ArrayList<CitizenAdditionRequest> items = new ArrayList<>();
    private TextView tvCitizenName, tvNeighborhood;
    private ImageView ivDocument;
    private RepresentativeClickListener mRepresentativeClickListener;
    private Dialog mDialog;
    private static int pendingNotification = 0;
    Intent intent = new Intent();

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
        pendingNotification = intent.getIntExtra("pendingNotification", 0);

        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Omar Taha", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Saad Ahmed", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Fouaz Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Saeed Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("salah Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Abubaker", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));

        mRepresentativeClickListener = new RepresentativeClickListener() {
            @Override
            public void onClick(int position, String tvItem) {
                if (tvItem == getString(R.string.see_document)) {
                    createDialog(position);
                    showDialog();
                } else if (tvItem == getString(R.string.confirm)) {
                    confirmAdditionRequest();
                } else if (tvItem == getString(R.string.regret)) {
                    regretAdditionRequest(position);
                }
            }

        };
        initRV();

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
        MenuItem item = menu.findItem(R.id.search_ic);
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

    // this function make intitialzation of recyclerview and addapter *look(updates)*
    private void initRV() {
        adapter = new AdditionRequestRecyclerViewAdapter(items, mRepresentativeClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        mBinding.rvAdditionRequests.setHasFixedSize(true);
        mBinding.rvAdditionRequests.setLayoutManager(layoutManager);
        mBinding.rvAdditionRequests.setAdapter(adapter);
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
        tvCitizenName.setText(items.get(position).getCitizenName());
        tvNeighborhood.setText(items.get(position).getCitizenAddress());
        ivDocument.setImageResource(R.drawable.ic_document);
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

    private void confirmAdditionRequest() {
        --pendingNotification;
        Toast.makeText(getBaseContext(), "confirm", Toast.LENGTH_SHORT).show();
    }

    private void regretAdditionRequest(int position) {
        // code to remove from database NOsql then remove from the lists which call items
        items.remove(position);
        --pendingNotification;
        // to initialize the RecyclerView with the list after delete from the database... avoidance matters
        initRV();
    }

    private void notificationsCount() {
        intent.putExtra("pendingNotification", pendingNotification);
        setResult(RepresentativeActivity.ADDITION_REQUEST_REQ_CODE, intent);
    }

}