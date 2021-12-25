package com.GMS.manager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.databinding.ActionsRecyclerviewItemBinding;
import com.GMS.manager.helperClasses.ItemClickListener;
import com.GMS.manager.models.Actions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.ActionsViewHolder> implements Filterable {
    ArrayList<Actions> mActionsList, mFullActionsList;
    ItemClickListener mItemClickListener;

    private Filter actionsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();
            ArrayList<Actions> temporaryActionsList = new ArrayList<>();

            if (searchText.isEmpty())
                temporaryActionsList.addAll(mFullActionsList);
            else {
                for (Actions action : mFullActionsList) {
                    if (action.getNeighborhoodName().toLowerCase().contains(searchText) ||
                            action.getDate().toLowerCase().contains(searchText)) {
                        temporaryActionsList.add(action);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = temporaryActionsList;
            return filterResults;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mActionsList.clear();
            mActionsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public ActionsAdapter(ArrayList<Actions> actionsList, ItemClickListener itemClickListener) {
        mFullActionsList = actionsList;
        mActionsList = new ArrayList<>(actionsList);
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public ActionsAdapter.ActionsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ActionsViewHolder(ActionsRecyclerviewItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ActionsAdapter.ActionsViewHolder holder, int position) {
        holder.actionsRecyclerviewItemBinding.managerActionMainCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onClick(position);
            }
        });
        holder.actionsRecyclerviewItemBinding.actionLocation
                .setText(mActionsList.get(position).getNeighborhoodName());
        holder.actionsRecyclerviewItemBinding.actionDate
                .setText(mActionsList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mActionsList.size();
    }

    @Override
    public Filter getFilter() {
        return actionsFilter;
    }

    public static class ActionsViewHolder extends RecyclerView.ViewHolder {
        ActionsRecyclerviewItemBinding actionsRecyclerviewItemBinding;

        public ActionsViewHolder(@NonNull @NotNull ActionsRecyclerviewItemBinding binding) {
            super(binding.getRoot());
            actionsRecyclerviewItemBinding = binding;
        }
    }
}
