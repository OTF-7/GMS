package com.GMS.manager.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.manager.helperClasses.EmployeeItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterEmployees extends RecyclerView.Adapter<RecyclerViewAdapterEmployees.RecyclerViewHolderEmployees>
    implements Filterable
{
private ArrayList<EmployeeItem> employeeItems ;
    private ArrayList<EmployeeItem> employeeItemsFull ;

    public RecyclerViewAdapterEmployees(ArrayList<EmployeeItem> employeeItems) {
        this.employeeItems = employeeItems;
        employeeItemsFull = new ArrayList<>(employeeItems);
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerViewHolderEmployees onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_employee_item , null , false);
       RecyclerViewHolderEmployees viewHolder= new RecyclerViewHolderEmployees(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolderEmployees holder, int position) {

        EmployeeItem item= employeeItems.get(position);
        holder.tvEmpName.setText(item.getEmpName());
        holder.tvEmpJob.setText(item.getEmpJob());
        holder.tvEmpStatus.setText(item.getEmpStatus());
        if( holder.tvEmpStatus.getText().equals("Available"))
        holder.vieww.setBackgroundResource(R.drawable.corner_shape_available);
        else
            holder.tvEmpStatus.setTextColor(Color.LTGRAY);
    }

    @Override
    public int getItemCount() {
        return employeeItems.size();
    }

    class RecyclerViewHolderEmployees extends  RecyclerView.ViewHolder
    {
        TextView tvEmpName  , tvEmpJob  , tvEmpStatus ;
        View vieww  ;
        public RecyclerViewHolderEmployees(@NonNull @NotNull View itemView) {
            super(itemView);
           tvEmpName = itemView.findViewById(R.id.tv_employee_name);
            tvEmpJob = itemView.findViewById(R.id.tv_employee_job);
            tvEmpStatus = itemView.findViewById(R.id.tv_employee_status);
            vieww= itemView.findViewById(R.id.view_color_state);

        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private  Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<EmployeeItem>  filteredList = new ArrayList<>();
            if(charSequence== null || charSequence.length()==0)
            {
                filteredList.addAll(employeeItemsFull);
            }
            else
            {
                String filterPatter = charSequence.toString().toLowerCase().trim();
                for(EmployeeItem item :employeeItemsFull)
                {
                    if(item.getEmpName().toLowerCase().contains(filterPatter));
                    filteredList.add(item);
                }
            }

            FilterResults results = new FilterResults();
            results.values=filteredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults)
        {
             employeeItems.clear();
             employeeItems.addAll((List)filterResults.values);
             notifyDataSetChanged();
        }
    };
}
