package com.GMS.login.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.GMS.R;
import com.GMS.login.adapters.RecyclerViewAdapterEmployees;
import com.GMS.login.adapters.RecyclerViewAdapterSetting;
import com.GMS.login.helperClasses.EmployeeItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class EmployeeFragment extends Fragment {

    RecyclerView rvEmployees ;
    public EmployeeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_mgr, container, false);
         rvEmployees = view.findViewById(R.id.rv_employees);
        ArrayList<com.GMS.login.helperClasses.EmployeeItem> items = new ArrayList<>();
        items.add(new EmployeeItem("Abdulrahman Khalid" , "Manager" , "Not Available"));
        items.add(new EmployeeItem("Omar Taha" , "Administrator" , "Available"));
        items.add(new EmployeeItem("Mohammed Shihab" , "delegate" , "Not Available"));
        items.add(new EmployeeItem("Omar swaid" , "representative" , "Available"));
        items.add(new EmployeeItem("Abdulrahman Khalid" , "Manager" , "Available"));
        items.add(new EmployeeItem("Omar Taha" , "Administrator" , "Available"));
        items.add(new EmployeeItem("Mohammed Shihab" , "delegate" , "Not Available"));
        items.add(new EmployeeItem("Omar swaid" , "representative" , "Available"));

        RecyclerViewAdapterEmployees adapter= new RecyclerViewAdapterEmployees(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvEmployees.setHasFixedSize(true);
        rvEmployees.setLayoutManager(layoutManager);
        rvEmployees.setAdapter(adapter);

       return  view ;
    }
}