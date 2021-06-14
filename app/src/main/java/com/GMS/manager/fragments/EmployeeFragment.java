package com.GMS.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.databinding.FragmentEmployeeMgrBinding;
import com.GMS.manager.adapters.RecyclerViewAdapterEmployees;
import com.GMS.manager.helperClasses.EmployeeItem;

import java.util.ArrayList;


public class EmployeeFragment extends Fragment {
    FragmentEmployeeMgrBinding mBinding;

    public EmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mBinding = FragmentEmployeeMgrBinding.inflate(inflater, container, false);
        ArrayList<com.GMS.manager.helperClasses.EmployeeItem> items = new ArrayList<>();
        items.add(new EmployeeItem("Abdulrahman Khalid", "Manager", "Not Available"));
        items.add(new EmployeeItem("Omar Taha", "Administrator", "Available"));
        items.add(new EmployeeItem("Mohammed Shihab", "delegate", "Not Available"));
        items.add(new EmployeeItem("Omar swaid", "representative", "Available"));
        items.add(new EmployeeItem("Abdulrahman Khalid", "Manager", "Available"));
        items.add(new EmployeeItem("Omar Taha", "Administrator", "Available"));
        items.add(new EmployeeItem("Mohammed Shihab", "delegate", "Not Available"));
        items.add(new EmployeeItem("Omar swaid", "representative", "Available"));

        RecyclerViewAdapterEmployees adapter = new RecyclerViewAdapterEmployees(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvEmployees.setHasFixedSize(true);
        mBinding.rvEmployees.setLayoutManager(layoutManager);
        mBinding.rvEmployees.setAdapter(adapter);

        return mBinding.getRoot();
    }
}