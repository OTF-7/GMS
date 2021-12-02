package com.GMS.aqel.adapters;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.GeneralClasses.CitizenItemClickListener;
import com.GMS.R;
import com.GMS.aqel.helperClass.CitizenItemOfAqel;
import com.GMS.databinding.CitizenItemRvBinding;
import com.GMS.firebaseFireStore.CitizenActionDetails;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class RecyclerViewAqelAdapter extends RecyclerView.Adapter<RecyclerViewAqelAdapter.ViewHolderCitizen> implements Filterable {

    ArrayList<CitizenActionDetails> listsCitizen, listsFull;
    int typeOfPage;
    CitizenItemClickListener mItemClickListener ;
    private Filter filterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();
            ArrayList<CitizenActionDetails> tempLst = new ArrayList<>();

            if (searchText.isEmpty())
                tempLst.addAll(listsFull);
            else {
                for (CitizenActionDetails item : listsFull) {
                    if (item.getName().toLowerCase().contains(searchText)) {
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

            listsCitizen.clear();
            listsCitizen.addAll((Collection<? extends CitizenActionDetails>) results.values);
            notifyDataSetChanged();
        }
    };

    public RecyclerViewAqelAdapter(ArrayList<CitizenActionDetails> lstsCitizen, int typeOfPage , CitizenItemClickListener mItemClickListener) {
        this.listsCitizen = lstsCitizen;
        this.typeOfPage = typeOfPage;
        this.listsFull = new ArrayList<>(lstsCitizen);
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderCitizen onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        return new ViewHolderCitizen(CitizenItemRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public int getItemCount() {
        return listsCitizen.size();
    }

    @Override
    public Filter getFilter() {
        return filterUser;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderCitizen holder, int position) {

        CitizenActionDetails item = listsCitizen.get(position);

        holder.mCitizenItemRvBinding.tvCitizenName.setText(item.getName());
        holder.mCitizenItemRvBinding.tvCitizenId.setText(item.getIdInParent());
        holder.mCitizenItemRvBinding.ivState.setImageResource(R.drawable.ic_need_scan);
        holder.mCitizenItemRvBinding.tvCount.setText(String.valueOf(item.getQuantityRequired()));
        holder.mCitizenItemRvBinding.citizenItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onClick(position);
            }
        });
    }

    class ViewHolderCitizen extends RecyclerView.ViewHolder {

        CitizenItemRvBinding mCitizenItemRvBinding;

        public ViewHolderCitizen(CitizenItemRvBinding mCitizenItemRvBinding) {
            super(mCitizenItemRvBinding.getRoot());
            this.mCitizenItemRvBinding = mCitizenItemRvBinding;
        }
    }
}
