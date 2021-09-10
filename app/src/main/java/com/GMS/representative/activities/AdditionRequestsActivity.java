package com.GMS.representative.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.databinding.ActivityAdditionRequestsBinding;
import com.GMS.representative.adapters.AdditionRequestRecyclerViewAdapter;
import com.GMS.representative.helperClass.CitizenAdditionRequest;

import java.util.ArrayList;

public class AdditionRequestsActivity extends AppCompatActivity {

    ActivityAdditionRequestsBinding mBinding;
    AdditionRequestRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAdditionRequestsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolBar);
        setTitle("Addition Requests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        int pendingNotification = intent.getIntExtra("pendingNotification", 0);
        intent.putExtra("pendingNotification", --pendingNotification);
        setResult(RepresentativeActivity.ADDITION_REQUEST_REQ_CODE, intent);

        ArrayList<CitizenAdditionRequest> items = new ArrayList<>();
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


        adapter = new AdditionRequestRecyclerViewAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        mBinding.rvAdditionRequests.setHasFixedSize(true);
        mBinding.rvAdditionRequests.setLayoutManager(layoutManager);
        mBinding.rvAdditionRequests.setAdapter(adapter);
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
                this.finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}