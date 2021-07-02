package com.GMS.manager.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.databinding.EmployeesRecyclerviewItemBinding;
import com.GMS.manager.models.Employees;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeeViewHolder> {

    private List<Employees> mEmployeesList;

    public EmployeesAdapter(List<Employees> employeesList) {
        this.mEmployeesList = employeesList;
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
        holder.mItemBinding.employeeState.setCompoundDrawablesWithIntrinsicBounds(mEmployeesList.get(position).getEmployeeStateIcon(),
                0, 0, 0);
    }

    @Override
    public int getItemCount() {
        return mEmployeesList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        EmployeesRecyclerviewItemBinding mItemBinding;

        public EmployeeViewHolder(@NonNull @NotNull EmployeesRecyclerviewItemBinding binding) {
            super(binding.getRoot());
            mItemBinding = binding;
        }
    }
}
