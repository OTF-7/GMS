package com.GMS.aqel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.GeneralClasses.CitizenItemClickListener;
import com.GMS.aqel.helperClass.CitizenItemOfAqel;
import com.GMS.databinding.CitizenItemRvBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class RecyclerViewAqelAdapter extends RecyclerView.Adapter<RecyclerViewAqelAdapter.ViewHolderCitizen> implements Filterable {

    ArrayList<CitizenItemOfAqel> listsCitizen, listsFull;
    int typeOfPage;
    CitizenItemClickListener mItemClickListener ;
    private Filter filterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText = constraint.toString().toLowerCase();
            ArrayList<CitizenItemOfAqel> tempLst = new ArrayList<>();

            if (searchText.isEmpty())
                tempLst.addAll(listsFull);
            else {
                for (CitizenItemOfAqel item : listsFull) {
                    if (item.getCitizenName().toLowerCase().contains(searchText)) {
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
            listsCitizen.addAll((Collection<? extends CitizenItemOfAqel>) results.values);
            notifyDataSetChanged();
        }
    };

    public RecyclerViewAqelAdapter(ArrayList<CitizenItemOfAqel> lstsCitizen, int typeOfPage , CitizenItemClickListener mItemClickListener) {
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

        CitizenItemOfAqel item = listsCitizen.get(position);

        holder.mCitizenItemRvBinding.tvCitizenName.setText(item.getCitizenName());
        holder.mCitizenItemRvBinding.tvCitizenId.setText(item.getCitizenId());
        holder.mCitizenItemRvBinding.tvCount.setText(String.valueOf(item.getCountCylinder()));
        holder.mCitizenItemRvBinding.ivState.setImageResource(item.getIvStateResource());
        holder.mCitizenItemRvBinding.citizenItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onClick(position , holder.mCitizenItemRvBinding.tvCitizenId.getText().toString());
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
