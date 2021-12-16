package com.GMS.manager.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.databinding.EmployeesRecyclerviewItemBinding;
import com.GMS.manager.models.Employees;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeeViewHolder> implements Filterable {

    private List<Employees> mEmployeesList, mFullEmployeesList;
    private Filter employeesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();
            ArrayList<Employees> temporaryEmployeesList = new ArrayList<>();

            if (searchText.isEmpty())
                temporaryEmployeesList.addAll(mFullEmployeesList);
            else {
                for (Employees employees : mFullEmployeesList) {
                    if (employees.getEmployeeName().toLowerCase().contains(searchText)) {
                        temporaryEmployeesList.add(employees);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = temporaryEmployeesList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mEmployeesList.clear();
            mEmployeesList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    private static final String TAG = "Employees Adapter";
    public EmployeesAdapter(List<Employees> employeesList) {
        this.mEmployeesList = employeesList;
        this.mFullEmployeesList = new ArrayList<>(employeesList);
        Log.d(TAG, "EmployeesAdapter: " + employeesList.size());
        Log.d(TAG, "EmployeesAdapter: " + mEmployeesList.size());
        Log.d(TAG, "EmployeesAdapter: " + mFullEmployeesList.size());
    }

    @NonNull
    @NotNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new EmployeeViewHolder(EmployeesRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EmployeeViewHolder holder, int position) {
        holder.mItemBinding.employeeName.setText(mEmployeesList.get(position).getEmployeeName());
        holder.mItemBinding.employeeState.setText(mEmployeesList.get(position).getEmployeeCurrentState());
        holder.mItemBinding.employeeType.setText(mEmployeesList.get(position).getEmployeeType());
        holder.mItemBinding.employeeIcon.setImageResource(mEmployeesList.get(position).getEmployeeIcon());
        holder.mItemBinding.employeeType.setCompoundDrawablesWithIntrinsicBounds(0, mEmployeesList.get(position).getEmployeeTypeIcon(),
                0, 0);
        Log.d(TAG, "onBindViewHolder: " + mEmployeesList.get(position).getEmployeeName()
                + mEmployeesList.get(position).getEmployeeType()
                + mEmployeesList.get(position).getEmployeeCurrentState()
                + mEmployeesList.get(position).getEmployeeIcon()
                + mEmployeesList.get(position).getEmployeeTypeIcon());
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + mEmployeesList.size());
        return mEmployeesList.size();
    }

    @Override
    public Filter getFilter() {
        return employeesFilter;
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        EmployeesRecyclerviewItemBinding mItemBinding;

        public EmployeeViewHolder(@NonNull @NotNull EmployeesRecyclerviewItemBinding binding) {
            super(binding.getRoot());
            mItemBinding = binding;
        }
    }
}
