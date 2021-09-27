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

import com.GMS.agent.activities.AgentActivity;
import com.GMS.agent.helperClasses.CitizenItem;
import com.GMS.databinding.CitizenItemRvBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class RecyclerViewAdapterCitizen extends RecyclerView.Adapter<RecyclerViewAdapterCitizen.ViewHolderCitizen> implements Filterable {

    private final ArrayList<CitizenItem> items;
    private final ArrayList<CitizenItem> lstsFull;
    private final Filter filterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();

            ArrayList<CitizenItem> tempLst = new ArrayList<>();

            if (searchText.isEmpty()) {
                tempLst.addAll(lstsFull);
            } else {
                for (CitizenItem item : lstsFull) {

                    if (item.getCitizenName().toLowerCase().contains(searchText) || item.getCitizenId().toLowerCase().contains(searchText)) {

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
            items.addAll((Collection<? extends CitizenItem>) results.values);
            notifyDataSetChanged();
        }
    };
    private Context mContext;
    private int countOfAcceptedCitizen;
    private ItemClickListener mItemClickListener;
    private int selectedPosition = -1;
    private int idList;

    public RecyclerViewAdapterCitizen(ArrayList<CitizenItem> items) {
        this.items = items;
        lstsFull = new ArrayList<>(items);
    }

    public RecyclerViewAdapterCitizen(ArrayList<CitizenItem> items, Context context) {
        this.items = items;
        mContext = context;
        lstsFull = new ArrayList<>(items);
    }

    public RecyclerViewAdapterCitizen(ArrayList<CitizenItem> items, Context context, ItemClickListener itemClickListener, int idList) {
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
    public void onBindViewHolder(@NonNull @NotNull ViewHolderCitizen holder, int position) {
        CitizenItem item = items.get(position);
        holder.mCitizenItemRvBinding.tvCitizenName.setText(item.getCitizenName());
        holder.mCitizenItemRvBinding.tvCitizenId.setText(item.getCitizenId());
        holder.mCitizenItemRvBinding.ivState.setImageResource(item.getIvStateResource());
        holder.mCitizenItemRvBinding.tvPrice.setText("RY " + item.getCountCylinder() * item.getPrice());
        holder.mCitizenItemRvBinding.tvCount.setText(String.valueOf(item.getCountCylinder()));
        if (items.get(position).isAcceptedState()) {
            holder.mCitizenItemRvBinding.ivCitizen.setBorderColor(Color.GREEN);
        } else {
            holder.mCitizenItemRvBinding.ivCitizen.setBorderColor(Color.RED);
        }
        holder.mCitizenItemRvBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idList == AgentActivity.FULL_LIST_ID) {
                    //code for accept the citizen
                    int mPosition = holder.getAdapterPosition();
                    items.get(position).setAcceptedState(true);
                    mItemClickListener.onClick(position, items.get(position).isAcceptedState());
                    selectedPosition = position;
                    notifyDataSetChanged();
                } else if (idList == AgentActivity.ACCEPTED_LIST_ID) {
                    // code for deny thr citizen
                    items.get(position).setAcceptedState(false);
                    mItemClickListener.onClick(position, items.get(position).isAcceptedState());
                    selectedPosition = position;

                    notifyDataSetChanged();
                }
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

