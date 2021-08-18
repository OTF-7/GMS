package com.GMS.manager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.SettingActivity;
import com.GMS.aqel.activities.AddCitizenActivity;
import com.GMS.databinding.FragmentEmployeesBinding;
import com.GMS.manager.adapters.EmployeesAdapter;
import com.GMS.manager.models.Employees;

import org.jetbrains.annotations.NotNull;

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
        mEmployeesBinding = FragmentEmployeesBinding.inflate(getLayoutInflater());
        mEmployeesList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        fillEmployees();
        employeesAdapter = new EmployeesAdapter(mEmployeesList);
        mEmployeesBinding.employeesRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mEmployeesBinding.employeesRecyclerView.setAdapter(employeesAdapter);
        mEmployeesBinding.employeesRecyclerView.setLayoutManager(layoutManager);
        return mEmployeesBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void fillEmployees() {
        Employees employee = new Employees();

        employee.setEmployeeCurrentState("Working in Alqahera");
        employee.setEmployeeName("Mohammed Shehab");
        employee.setEmployeeIcon(R.drawable.picture1);
        employee.setEmployeeStateIcon(R.drawable.ic_rest);
        employee.setEmployeeType("Rep");
        employee.setEmployeeTypeIcon(R.drawable.ic_rep_icon);
        mEmployeesList.add(employee);

        employee = new Employees();
        employee.setEmployeeCurrentState("Working in Almadena");
        employee.setEmployeeName("Kamal Balah");
        employee.setEmployeeIcon(R.drawable.sign_in_image);
        employee.setEmployeeStateIcon(R.drawable.ic_working);
        employee.setEmployeeType("Agent");
        employee.setEmployeeTypeIcon(R.drawable.ic_agent_icon);
        mEmployeesList.add(employee);

        employee = new Employees();
        employee.setEmployeeCurrentState("Working in Mosa ST");
        employee.setEmployeeName("Mohammed Alhamzy");
        employee.setEmployeeIcon(R.drawable.picture2);
        employee.setEmployeeStateIcon(R.drawable.ic_working);
        employee.setEmployeeType("Agent");
        employee.setEmployeeTypeIcon(R.drawable.ic_agent_icon);
        mEmployeesList.add(employee);

        employee = new Employees();
        employee.setEmployeeCurrentState("Working in Aldamgha");
        employee.setEmployeeName("Ammar Sharqy");
        employee.setEmployeeIcon(R.drawable.picture3);
        employee.setEmployeeStateIcon(R.drawable.ic_working);
        employee.setEmployeeType("Rep");
        employee.setEmployeeTypeIcon(R.drawable.ic_rep_icon);
        mEmployeesList.add(employee);

        employee = new Employees();
        employee.setEmployeeCurrentState("Working in sana'a ST");
        employee.setEmployeeName("Salah Doos");
        employee.setEmployeeIcon(R.drawable.picture4);
        employee.setEmployeeStateIcon(R.drawable.ic_rest);
        employee.setEmployeeType("Rep");
        employee.setEmployeeTypeIcon(R.drawable.ic_rep_icon);
        mEmployeesList.add(employee);

        employee = new Employees();
        employee.setEmployeeCurrentState("Working in Gamal ST");
        employee.setEmployeeName("wael Masawod");
        employee.setEmployeeIcon(R.drawable.sign_in_image);
        employee.setEmployeeStateIcon(R.drawable.ic_working);
        employee.setEmployeeType("Rep");
        employee.setEmployeeTypeIcon(R.drawable.ic_rep_icon);
        mEmployeesList.add(employee);

        employee = new Employees();
        employee.setEmployeeCurrentState("Working in Aldhmia ST");
        employee.setEmployeeName("Salah Ghazy");
        employee.setEmployeeIcon(R.drawable.picture5);
        employee.setEmployeeStateIcon(R.drawable.ic_rest);
        employee.setEmployeeType("agent");
        employee.setEmployeeTypeIcon(R.drawable.ic_agent_icon);
        mEmployeesList.add(employee);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_action_bar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.ic_action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                employeesAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                employeesAdapter.getFilter().filter(newText);
                return false;
            }

        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification_addition:
                Intent mAddCitizenIntent = new Intent(this.getActivity(), AddCitizenActivity.class);
                startActivity(mAddCitizenIntent);
                break;
            case R.id.setting_item:
                Intent mSettingIntent = new Intent(this.getActivity(), SettingActivity.class);
                startActivity(mSettingIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mEmployeesBinding = null;
    }
}