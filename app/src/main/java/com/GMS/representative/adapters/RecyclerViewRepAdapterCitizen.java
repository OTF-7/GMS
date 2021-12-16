package com.GMS.representative.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.GeneralClasses.CitizenItemClickListener;
import com.GMS.databinding.CitizenItemRvBinding;
import com.GMS.firebaseFireStore.CitizenActionDetails;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class RecyclerViewRepAdapterCitizen extends RecyclerView.Adapter<RecyclerViewRepAdapterCitizen.ViewHolderCitizen>
        implements Filterable {
    ArrayList<CitizenActionDetails> lsts = new ArrayList<>();
    ArrayList<CitizenActionDetails> fullLsts;
    CitizenItemClickListener mItemClickListener;
    private final Filter filterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();
            ArrayList<CitizenActionDetails> tempLst = new ArrayList<>();
            if (searchText.isEmpty()) {
                tempLst.addAll(fullLsts);
            } else {
                for (CitizenActionDetails item : fullLsts)
                    if (item.getName().toLowerCase().contains(searchText)) {
                        tempLst.add(item);
                    }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = tempLst;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            lsts.clear();
            lsts.addAll((Collection<? extends CitizenActionDetails>) results.values);
            notifyDataSetChanged();
        }
    };

    int typeOfPage;

    public RecyclerViewRepAdapterCitizen(ArrayList<CitizenActionDetails> lsts, int typeOfPage , CitizenItemClickListener mItemClickListener) {
        this.lsts = lsts;
        this.typeOfPage = typeOfPage;
        this.fullLsts = new ArrayList<>(this.lsts);
        this.mItemClickListener=mItemClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderCitizen onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolderCitizen(CitizenItemRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public int getItemCount() {
        return lsts.size();

    }

    @Override
    public Filter getFilter() {
        return filterUser;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderCitizen holder, int position) {

        CitizenActionDetails item = lsts.get(position);

        holder.mCitizenItemRvBinding.tvCitizenName.setText(item.getName());
        holder.mCitizenItemRvBinding.tvCitizenId.setText(item.getIdInParent());
        holder.mCitizenItemRvBinding.tvCount.setText(String.valueOf(item.getDeliveredQuantity()));
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
