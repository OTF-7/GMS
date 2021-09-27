package com.GMS.GeneralAdapters;

import android.content.Context;
import android.graphics.Paint;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.GeneralClasses.HistoryItem;
import com.GMS.GeneralClasses.SingleItemClickListener;
import com.GMS.R;
import com.GMS.databinding.HistoryItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class RecyclerViewAdapterHistory extends RecyclerView.Adapter<RecyclerViewAdapterHistory.HistoryViewHolder> implements Filterable {

    Context mContect;
    ArrayList<HistoryItem> items ;
    ArrayList<HistoryItem> fullLists;

    SingleItemClickListener mSingleItemClickListener;
    public RecyclerViewAdapterHistory(Context mContext, ArrayList<HistoryItem> items , SingleItemClickListener singleItemClickListener) {
        this.mContect = mContext;
        this.items = items;
       this.mSingleItemClickListener = singleItemClickListener;
        fullLists = new ArrayList<>(items);
    }

    @NonNull
    @NotNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(HistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HistoryViewHolder holder, int position) {
        HistoryItem item = items.get(position);
        holder.mHistoryItemBindingBing.historyLocation.setText(item.getLocation());
        holder.mHistoryItemBindingBing.historyDate.setText(item.getDate());
        holder.mHistoryItemBindingBing.historyRepName.setText(item.getPartnerName());
        holder.mHistoryItemBindingBing.historyPrice.setText(String.valueOf(item.getPrice()));
        holder.mHistoryItemBindingBing.historyStation.setText(item.getStation());
        holder.mHistoryItemBindingBing.historyStationQty.setText(String.valueOf(item.getQty()));
        holder.mHistoryItemBindingBing.ibShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.mHistoryItemBindingBing.viewSeparator.getVisibility()==View.VISIBLE)
                {
                    holder.mHistoryItemBindingBing.viewSeparator.setVisibility(View.GONE);

                }
                else
                {
                    holder.mHistoryItemBindingBing.viewSeparator.setVisibility(View.VISIBLE);
                }
                if( holder.mHistoryItemBindingBing.childContainer.getVisibility()==View.VISIBLE) {
                    TransitionManager.beginDelayedTransition( holder.mHistoryItemBindingBing.containerParent , new AutoTransition());
                    holder.mHistoryItemBindingBing.childContainer.setVisibility(View.GONE);
                    holder.mHistoryItemBindingBing.ibShow.setImageResource(R.drawable.ic_down_arrow);
                }
                else
                {
                    TransitionManager.beginDelayedTransition( holder.mHistoryItemBindingBing.containerParent , new AutoTransition());
                    holder.mHistoryItemBindingBing.childContainer.setVisibility(View.VISIBLE);
                    holder.mHistoryItemBindingBing.ibShow.setImageResource(R.drawable.ic_top_arrow);

                }
            }
        });

        holder.mHistoryItemBindingBing.tvDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSingleItemClickListener.onClick("hello" , "hi");
                Toast.makeText(mContect.getApplicationContext() ,"hello" , Toast.LENGTH_SHORT).show();
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

    private final Filter filterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();

            ArrayList<HistoryItem> tempLst = new ArrayList<>();

            if (searchText.isEmpty()) {
                tempLst.addAll(fullLists);
            } else {
                for (HistoryItem item : fullLists) {

                   if (item.getDate().toLowerCase().replace("/" , "").contains(searchText)) {

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
            items.addAll((Collection<? extends HistoryItem>) results.values);
            notifyDataSetChanged();
        }
    };
    class HistoryViewHolder extends RecyclerView.ViewHolder
    {
        HistoryItemBinding mHistoryItemBindingBing ;

        public HistoryViewHolder(HistoryItemBinding mBinding) {
            super(mBinding.getRoot());
            this.mHistoryItemBindingBing=mBinding;
            this.mHistoryItemBindingBing.tvDetails.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        }
    }
}
