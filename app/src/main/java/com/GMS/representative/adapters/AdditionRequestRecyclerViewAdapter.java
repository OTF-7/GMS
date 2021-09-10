package com.GMS.representative.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.agent.helperClasses.CitizenItem;
import com.GMS.databinding.AdditionRequestItemBinding;
import com.GMS.representative.helperClass.CitizenAdditionRequest;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class AdditionRequestRecyclerViewAdapter extends RecyclerView.Adapter<AdditionRequestRecyclerViewAdapter.AdditionRequestViewHolder> implements Filterable {


    ArrayList<CitizenAdditionRequest> lsts = new ArrayList<>();
     ArrayList<CitizenAdditionRequest> fullList = new ArrayList<>();
    public AdditionRequestRecyclerViewAdapter(ArrayList<CitizenAdditionRequest> lsts) {
        this.lsts = lsts;
        this.fullList = new ArrayList<>(this.lsts);
    }

    @NonNull
    @NotNull
    @Override
    public AdditionRequestViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        return new AdditionRequestRecyclerViewAdapter.AdditionRequestViewHolder(AdditionRequestItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdditionRequestViewHolder holder, int position) {
        CitizenAdditionRequest item = lsts.get(position);
        holder.mAdditionRequestItemBinding.tvCitizenName.setText(item.getCitizenName());
        holder.mAdditionRequestItemBinding.tvCitizenAddress.setText(item.getCitizenAddress());
        holder.mAdditionRequestItemBinding.tvCitizenHireDate.setText(item.getCitizenHireDate());

    }

    @Override
    public int getItemCount() {
        return lsts.size();
    }

    @Override
    public Filter getFilter() {
        return filterUser;
    }
    private final Filter filterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            String searchText = charSequence.toString().toLowerCase();
            ArrayList<CitizenAdditionRequest> tempLst = new ArrayList<>();

            if (searchText.isEmpty()) {
                tempLst.addAll(fullList);
            } else {
                for (CitizenAdditionRequest item : fullList) {

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
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            lsts.clear();
            lsts.addAll((Collection<? extends CitizenAdditionRequest>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class AdditionRequestViewHolder extends RecyclerView.ViewHolder {

        AdditionRequestItemBinding mAdditionRequestItemBinding;

        public AdditionRequestViewHolder(AdditionRequestItemBinding mAdditionRequestItemBinding) {
            super(mAdditionRequestItemBinding.getRoot());
            this.mAdditionRequestItemBinding = mAdditionRequestItemBinding;
            this.mAdditionRequestItemBinding.tvSeeDocumentDetail.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}
