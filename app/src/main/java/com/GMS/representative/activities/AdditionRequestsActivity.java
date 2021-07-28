package com.GMS.representative.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.databinding.ActivityAdditionRequestsBinding;
import com.GMS.representative.adapters.AdditionRequestRecyclerViewAdapter;
import com.GMS.representative.helperClass.CitizenAdditionRequest;

import java.util.ArrayList;

public class AdditionRequestsActivity extends AppCompatActivity {

    ActivityAdditionRequestsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAdditionRequestsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Intent intent = getIntent();
        int pendingNotification = intent.getIntExtra("pendingNotification", 0);
        intent.putExtra("pendingNotification", --pendingNotification);
        setResult(RepresentativeActivity.ADDITION_REQUEST_REQ_CODE, intent);

        ArrayList<CitizenAdditionRequest> items = new ArrayList<>();
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid", "Alornish", "20/20/2021"));


        AdditionRequestRecyclerViewAdapter adapter = new AdditionRequestRecyclerViewAdapter(items);
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
}