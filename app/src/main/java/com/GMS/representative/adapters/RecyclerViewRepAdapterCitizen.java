package com.GMS.representative.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.databinding.CitizenItemRvBinding;
import com.GMS.representative.helperClass.CitizenItemOfRep;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class RecyclerViewRepAdapterCitizen extends RecyclerView.Adapter<RecyclerViewRepAdapterCitizen.ViewHolderCitizen>
        implements Filterable {
    ArrayList<CitizenItemOfRep> lsts = new ArrayList<>();
    ArrayList<CitizenItemOfRep> fullLsts;
    private final Filter filterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();
            ArrayList<CitizenItemOfRep> tempLst = new ArrayList<>();
            if (searchText.isEmpty()) {
                tempLst.addAll(fullLsts);
            } else {
                for (CitizenItemOfRep item : fullLsts)
                    if (item.getCitizenName().toLowerCase().contains(searchText)) {
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
            lsts.addAll((Collection<? extends CitizenItemOfRep>) results.values);
            notifyDataSetChanged();
        }
    };
    int typeOfPage;

    public RecyclerViewRepAdapterCitizen(ArrayList<CitizenItemOfRep> lsts, int typeOfPage) {
        this.lsts = lsts;
        this.typeOfPage = typeOfPage;
        this.fullLsts = new ArrayList<>(this.lsts);
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

        CitizenItemOfRep item = lsts.get(position);

        holder.mCitizenItemRvBinding.tvCitizenName.setText(item.getCitizenName());
        holder.mCitizenItemRvBinding.tvCitizenId.setText(item.getCitizenId());
        holder.mCitizenItemRvBinding.tvCount.setText(String.valueOf(item.getCountCylinder()));
        holder.mCitizenItemRvBinding.ivState.setImageResource(item.getIvStateResource());
    }

    class ViewHolderCitizen extends RecyclerView.ViewHolder {
        CitizenItemRvBinding mCitizenItemRvBinding;

        public ViewHolderCitizen(CitizenItemRvBinding mCitizenItemRvBinding) {
            super(mCitizenItemRvBinding.getRoot());
            this.mCitizenItemRvBinding = mCitizenItemRvBinding;
        }
    }
}
