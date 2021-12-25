package com.GMS.manager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.databinding.StationsRecyclerviewItemBinding;
import com.GMS.manager.helperClasses.ItemClickListener;
import com.GMS.manager.models.Stations;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.StationViewHolder> implements Filterable {

    private List<Stations> mStationsList, mFullStationsList;
    private Context mContext;
    private Filter stationsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();
            ArrayList<Stations> temporaryStationsList = new ArrayList<>();

            if (searchText.isEmpty())
                temporaryStationsList.addAll(mFullStationsList);
            else {
                for (Stations stations : mFullStationsList) {
                    if (stations.getStationName().toLowerCase().contains(searchText)) {
                        temporaryStationsList.add(stations);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = temporaryStationsList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mStationsList.clear();
            mStationsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    private ItemClickListener mItemClickListener;

    public StationsAdapter(List<Stations> stationsList, Context context, ItemClickListener itemClickListener) {
        this.mStationsList = stationsList;
        mFullStationsList = new ArrayList<>(stationsList);
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public StationsAdapter.StationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new StationViewHolder(StationsRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StationsAdapter.StationViewHolder holder, int position) {
        holder.mItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onClick(position);
            }
        });
        holder.mItemBinding.managerStationName.setText(mStationsList.get(position).getStationName());
        holder.mItemBinding.managerStationImage.setImageResource(mStationsList.get(position).getStationImage());
    }

    @Override
    public int getItemCount() {
        return mStationsList.size();
    }

    @Override
    public Filter getFilter() {
        return stationsFilter;
    }

    public static class StationViewHolder extends RecyclerView.ViewHolder {
        StationsRecyclerviewItemBinding mItemBinding;

        public StationViewHolder(@NonNull @NotNull StationsRecyclerviewItemBinding binding) {
            super(binding.getRoot());
            mItemBinding = binding;
        }
    }


}
