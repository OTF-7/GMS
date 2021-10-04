package com.GMS.GeneralAdapters;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.GMS.GeneralClasses.ActionDetailsCitizen;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.databinding.DetailsItemBinding;
import com.GMS.databinding.HistoryItemBinding;

import java.util.ArrayList;

public class RecyclerViewAdapterDetailsHistory extends RecyclerView.Adapter<RecyclerViewAdapterDetailsHistory.DetailsViewHolder> {

    ArrayList <ActionDetailsCitizen> items = new ArrayList<>();
    Context mContext;

    public RecyclerViewAdapterDetailsHistory(ArrayList<ActionDetailsCitizen> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new DetailsViewHolder(DetailsItemBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {

        ActionDetailsCitizen item = items.get(position);
        holder.mDetailsItemBinding.tvCitizenName.setText(item.getCitizenName());
        holder.mDetailsItemBinding.tvQty.setText(String.valueOf(item.getQty()));
        holder.mDetailsItemBinding.tvAqelName.setText(item.getAqelName());
        holder.mDetailsItemBinding.tvRepName.setText(item.getRepName());
        holder.mDetailsItemBinding.ibShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.mDetailsItemBinding.viewSeparator.getVisibility()==View.VISIBLE)
                {
                    holder.mDetailsItemBinding.viewSeparator.setVisibility(View.GONE);

                }
                else
                {
                    holder.mDetailsItemBinding.viewSeparator.setVisibility(View.VISIBLE);
                }
                if( holder.mDetailsItemBinding.childContainer.getVisibility()==View.VISIBLE) {
                    TransitionManager.beginDelayedTransition( holder.mDetailsItemBinding.containerParent , new AutoTransition());
                    holder.mDetailsItemBinding.childContainer.setVisibility(View.GONE);
                    holder.mDetailsItemBinding.ibShow.setImageResource(R.drawable.ic_down_arrow);
                }
                else
                {
                    TransitionManager.beginDelayedTransition( holder.mDetailsItemBinding.containerParent , new AutoTransition());
                    holder.mDetailsItemBinding.childContainer.setVisibility(View.VISIBLE);
                    holder.mDetailsItemBinding.ibShow.setImageResource(R.drawable.ic_top_arrow);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class DetailsViewHolder extends RecyclerView.ViewHolder
    {
        DetailsItemBinding mDetailsItemBinding;

        public DetailsViewHolder(DetailsItemBinding detailsItemBinding) {
            super(detailsItemBinding.getRoot());
            this.mDetailsItemBinding = detailsItemBinding;
        }
    }
}
