package com.GMS.agent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.agent.helperClasses.CitizenItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class RecyclerViewAdapterCitizen extends RecyclerView.Adapter<RecyclerViewAdapterCitizen.ViewHolderCitizen> implements Filterable {

    ArrayList<CitizenItem> items ;
    ArrayList<CitizenItem> lstsFull ;
    Context mContext ;
    public RecyclerViewAdapterCitizen(ArrayList<CitizenItem> items) {
        this.items = items;
        lstsFull = new ArrayList<>(items);
    }

    public RecyclerViewAdapterCitizen(ArrayList<CitizenItem> items, Context context) {
        this.items = items;
        mContext = context;
        lstsFull = new ArrayList<>(items);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderCitizen onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.citizen_item_rv, parent , false);
        ViewHolderCitizen viewHolder= new ViewHolderCitizen(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderCitizen holder, int position) {
        CitizenItem item = items.get(position);


        holder.citizenName.setText(item.getCitizenName());
        holder.citizenId.setText(item.getCitizenId());
        holder.count.setText(String.valueOf(item.getCountCylinder()));
        holder.ivStatte.setImageResource(item.getIvStateResource());
        holder.price.setText("RY "+String.valueOf(item.getCountCylinder()*item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return filterUser;
    }
    private Filter filterUser=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();

            ArrayList<CitizenItem> tempLst =new ArrayList<>();

             if(searchText.isEmpty()) {
                tempLst.addAll(lstsFull);
            }
            else
            {
                for(CitizenItem item :lstsFull)
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

            items.clear();
            items.addAll((Collection<? extends CitizenItem>) results.values);
            notifyDataSetChanged();
        }
    };


    class ViewHolderCitizen extends RecyclerView.ViewHolder
    {
        TextView citizenName , citizenId , count , price;
        ImageView ivStatte ;
        public ViewHolderCitizen(@NonNull @NotNull View itemView) {
            super(itemView);
            citizenName = itemView.findViewById(R.id.tv_citizen_name);
            citizenId = itemView.findViewById(R.id.tv_citizen_id);
            count = itemView.findViewById(R.id.tv_count);
            ivStatte = itemView.findViewById(R.id.iv_state);
            price = itemView.findViewById(R.id.tv_price);
        }
    }
}
