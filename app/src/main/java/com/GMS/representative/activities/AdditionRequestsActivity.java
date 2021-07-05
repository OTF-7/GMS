package com.GMS.representative.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.GMS.R;
import com.GMS.agent.adapters.RecyclerViewAdapterCitizen;
import com.GMS.agent.helperClasses.CitizenItem;
import com.GMS.databinding.ActivityAdditionRequestsBinding;
import com.GMS.representative.adapters.AdditionRequestRecyclerViewAdapter;
import com.GMS.representative.helperClass.CitizenAdditionRequest;

import java.util.ArrayList;

public class AdditionRequestsActivity extends AppCompatActivity {

    ActivityAdditionRequestsBinding mBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAdditionRequestsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


        ArrayList<CitizenAdditionRequest> items = new ArrayList<>();
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));
        items.add(new CitizenAdditionRequest("Abdulrahman Khalid" , "AlKprnish" , "20/20/2021"));


        AdditionRequestRecyclerViewAdapter adapter = new AdditionRequestRecyclerViewAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        mBinding.rvAdditionRequests.setHasFixedSize(true);
        mBinding.rvAdditionRequests.setLayoutManager(layoutManager);
        mBinding.rvAdditionRequests.setAdapter(adapter);
    }
}