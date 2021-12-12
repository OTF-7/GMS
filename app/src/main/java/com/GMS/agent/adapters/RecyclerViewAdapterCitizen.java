package com.GMS.agent.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.GeneralClasses.CitizenItemClickListener;
import com.GMS.agent.activities.AgentActivity;
import com.GMS.agent.activities.CylindersReceiveActivity;
import com.GMS.agent.helperClasses.CitizenItem;
import com.GMS.databinding.CitizenItemRvBinding;
import com.GMS.firebaseFireStore.CitizenActionDetails;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class RecyclerViewAdapterCitizen extends RecyclerView.Adapter<RecyclerViewAdapterCitizen.ViewHolderCitizen> implements Filterable {

    private final ArrayList<CitizenActionDetails> items;
    private final ArrayList<CitizenActionDetails> lstsFull;
    private final Filter filterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();

            ArrayList<CitizenActionDetails> tempLst = new ArrayList<>();

            if (searchText.isEmpty()) {
                tempLst.addAll(lstsFull);
            } else {
                for (CitizenActionDetails item : lstsFull) {

                    if (item.getName().toLowerCase().contains(searchText) || item.getIdInParent().toLowerCase().contains(searchText)) {

                        tempLst.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = tempLst;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            items.clear();
            items.addAll((Collection<? extends CitizenActionDetails>) results.values);
            notifyDataSetChanged();
        }
    };
    private Context mContext;
    private int countOfAcceptedCitizen;
    private CitizenItemClickListener mItemClickListener;
    private int selectedPosition = -1;
    private int idList;

    public RecyclerViewAdapterCitizen(ArrayList<CitizenActionDetails> items, Context baseContext, CitizenItemClickListener mItemClickListener) {
        this.items = items;
        lstsFull = new ArrayList<>(items);
        this.mItemClickListener= mItemClickListener;
    }

    public RecyclerViewAdapterCitizen(ArrayList<CitizenActionDetails> items, Context context) {
        this.items = items;
        mContext = context;
        lstsFull = new ArrayList<>(items);
    }

    public RecyclerViewAdapterCitizen(ArrayList<CitizenActionDetails> items, Context context, CitizenItemClickListener itemClickListener, int idList) {
        this.items = items;
        this.idList = idList;
        mContext = context;
        mItemClickListener = itemClickListener;
        lstsFull = new ArrayList<>(items);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderCitizen onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolderCitizen(CitizenItemRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCitizen holder, int position) {
        CitizenActionDetails item = items.get(position);
        holder.mCitizenItemRvBinding.tvCitizenName.setText(item.getName());
        holder.mCitizenItemRvBinding.tvCitizenId.setText(item.getIdInParent());
        holder.mCitizenItemRvBinding.tvPrice.setText("RY " + item.getTotal());
        holder.mCitizenItemRvBinding.tvCount.setText(String.valueOf(item.getDeliveredQuantity()));
        holder.mCitizenItemRvBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onClick(position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Filter getFilter() {
        return filterUser;
    }

    class ViewHolderCitizen extends RecyclerView.ViewHolder {
        CitizenItemRvBinding mCitizenItemRvBinding;

        public ViewHolderCitizen(CitizenItemRvBinding mCitizenItemRvBinding) {
            super(mCitizenItemRvBinding.getRoot());
            this.mCitizenItemRvBinding = mCitizenItemRvBinding;
        }
    }
}

