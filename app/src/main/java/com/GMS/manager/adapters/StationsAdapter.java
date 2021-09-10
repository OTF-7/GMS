package com.GMS.manager.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.databinding.StationsRecyclerviewItemBinding;
import com.GMS.manager.models.Stations;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.StationViewHolder> implements Filterable {

    private List<Stations> mStationsList, mFullStationsList;
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

    public StationsAdapter(List<Stations> stationsList) {
        this.mStationsList = stationsList;
        mFullStationsList = new ArrayList<>(stationsList);
    }

    @NonNull
    @NotNull
    @Override
    public StationsAdapter.StationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new StationViewHolder(StationsRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StationsAdapter.StationViewHolder holder, int position) {
        holder.mItemBinding.stationName.setText(mStationsList.get(position).getStationName());
        holder.mItemBinding.stationIcon.setImageResource(mStationsList.get(position).getStationIcon());
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
