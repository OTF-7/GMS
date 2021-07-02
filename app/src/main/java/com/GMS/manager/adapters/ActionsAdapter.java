package com.GMS.manager.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.databinding.ActionsRecyclerviewItemBinding;
import com.GMS.manager.models.Actions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.ActionsViewHolder> {
    List<Actions> mActionsList;

    public ActionsAdapter(List<Actions> actionsList) {
        mActionsList = actionsList;
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
        holder.actionsRecyclerviewItemBinding.actionAgentName
                .setText(mActionsList.get(position).getAgentName());
        holder.actionsRecyclerviewItemBinding.actionLocation
                .setText(mActionsList.get(position).getNeighborhoodName());
        holder.actionsRecyclerviewItemBinding.actionRepName
                .setText(mActionsList.get(position).getRepresentativeName());
        holder.actionsRecyclerviewItemBinding.actionDate
                .setText(mActionsList.get(position).getDate());
        if (mActionsList.get(position).isActionState()) {
            holder.actionsRecyclerviewItemBinding.actionState.setText("Active");
            holder.actionsRecyclerviewItemBinding.actionState.
                    setBackgroundResource(R.drawable.action_lable_active_shape);
        } else {
            holder.actionsRecyclerviewItemBinding.actionState.setText("Completed");
            holder.actionsRecyclerviewItemBinding.actionState.
                    setBackgroundResource(R.drawable.action_lable_completed_shape);
        }
    }

    @Override
    public int getItemCount() {
        return mActionsList.size();
    }

    public static class ActionsViewHolder extends RecyclerView.ViewHolder {
        ActionsRecyclerviewItemBinding actionsRecyclerviewItemBinding;

        public ActionsViewHolder(@NonNull @NotNull ActionsRecyclerviewItemBinding binding) {
            super(binding.getRoot());
            actionsRecyclerviewItemBinding = binding;
        }
    }
}
