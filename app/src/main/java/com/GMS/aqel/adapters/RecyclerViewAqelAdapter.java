package com.GMS.aqel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.aqel.helperClass.CitizenItemOfAqel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class RecyclerViewAqelAdapter  extends RecyclerView.Adapter<RecyclerViewAqelAdapter.ViewHolderCitizen> implements Filterable {

    ArrayList<CitizenItemOfAqel> lstsCitizen ;
    ArrayList<CitizenItemOfAqel> lstsFull;
    int typeOfPage ;

    public RecyclerViewAqelAdapter(ArrayList<CitizenItemOfAqel> lstsCitizen, int typeOfPage) {
        this.lstsCitizen = lstsCitizen;
        this.typeOfPage = typeOfPage;
        this.lstsFull = new ArrayList<>(lstsCitizen);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderCitizen onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.citizen_item_rv , parent , false);
        ViewHolderCitizen viewHoder = new ViewHolderCitizen(view);

        return viewHoder ;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderCitizen holder, int position) {

        CitizenItemOfAqel item = lstsCitizen.get(position);

        holder.citizenName.setText(item.getCitizenName());
        holder.citizenId.setText(item.getCitizenId());
        holder.count.setText(String.valueOf(item.getCountCylinder()));
        holder.ivStatte.setImageResource(item.getIvStateResource());
    }

    @Override
    public int getItemCount() {
        return lstsCitizen.size();
    }

    @Override
    public Filter getFilter() {
        return filterUser;
    }
    private Filter filterUser=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();
            ArrayList<CitizenItemOfAqel> tempLst =new ArrayList<>();

            if(searchText.isEmpty())
                tempLst.addAll(lstsFull);
            else
            {
                for(CitizenItemOfAqel item :lstsFull)
                {
                    if(item.getCitizenName().toLowerCase().contains(searchText))
                    {
                        tempLst.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values=tempLst;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            lstsCitizen.clear();
            lstsCitizen.addAll((Collection<? extends CitizenItemOfAqel>) results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolderCitizen extends RecyclerView.ViewHolder
    {

        TextView citizenName , citizenId , count;
        ImageView ivStatte ;
        public ViewHolderCitizen(@NonNull @NotNull View itemView) {
            super(itemView);
            citizenName = itemView.findViewById(R.id.tv_citizen_name);
            citizenId = itemView.findViewById(R.id.tv_citizen_id);
            count = itemView.findViewById(R.id.tv_count);
            ivStatte = itemView.findViewById(R.id.iv_state);
        }
    }
}
