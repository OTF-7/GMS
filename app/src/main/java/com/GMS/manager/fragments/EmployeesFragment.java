package com.GMS.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.databinding.FragmentEmployeesBinding;
import com.GMS.manager.adapters.EmployeesAdapter;
import com.GMS.manager.models.Employees;

import java.util.ArrayList;
import java.util.List;

public class EmployeesFragment extends Fragment {
    FragmentEmployeesBinding mEmployeesBinding;
    EmployeesAdapter employeesAdapter;
    List<Employees> mEmployeesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return mEmployeesBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEmployeesBinding = FragmentEmployeesBinding.inflate(getLayoutInflater());
        mEmployeesList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        fillEmployees();
        employeesAdapter = new EmployeesAdapter(mEmployeesList);
        mEmployeesBinding.employeesRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mEmployeesBinding.employeesRecyclerView.setAdapter(employeesAdapter);
        mEmployeesBinding.employeesRecyclerView.setLayoutManager(layoutManager);
    }

    private void fillEmployees() {
        for (int i = 0; i < 10; i++) {
            Employees employee = new Employees();
            employee.setEmployeeCurrentState("Working in Alqahera");
            employee.setEmployeeName("Mohammed Shehab");
            employee.setEmployeeIcon(R.drawable.sign_in_image);
            employee.setEmployeeStateIcon(R.drawable.favorite_icon);
            employee.setEmployeeType("Rep");
            employee.setEmployeeTypeIcon(R.drawable.quantity_icon);
            mEmployeesList.add(employee);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEmployeesBinding = null;
    }
}