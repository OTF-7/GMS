package com.GMS.manager.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.databinding.StationsRecyclerviewItemBinding;
import com.GMS.manager.models.Stations;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StationsAdapter extends RecyclerView.Adapter<StationsAdapter.StationViewHolder> {

    private List<Stations> mStationsList;

    public StationsAdapter(List<Stations> stationsList) {
        this.mStationsList = stationsList;
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

    public static class StationViewHolder extends RecyclerView.ViewHolder {
        StationsRecyclerviewItemBinding mItemBinding;

        public StationViewHolder(@NonNull @NotNull StationsRecyclerviewItemBinding binding) {
            super(binding.getRoot());
            mItemBinding = binding;
        }
    }
}
