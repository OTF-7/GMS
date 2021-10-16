package com.GMS.representative.adapters;

import android.content.res.Resources;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.databinding.AdditionRequestItemBinding;
import com.GMS.representative.helperClass.CitizenAdditionRequest;
import com.GMS.representative.helperClass.RepresentativeClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class AdditionRequestRecyclerViewAdapter extends RecyclerView.Adapter<AdditionRequestRecyclerViewAdapter.AdditionRequestViewHolder> implements Filterable {


    ArrayList<CitizenAdditionRequest> lists = new ArrayList<>();
    ArrayList<CitizenAdditionRequest> fullList = new ArrayList<>();
    RepresentativeClickListener mRepresentativeClickListener;

    public AdditionRequestRecyclerViewAdapter(ArrayList<CitizenAdditionRequest> lists, RepresentativeClickListener mRepresentativeClickListener) {
        this.lists = lists;
        this.fullList = new ArrayList<>(this.lists);
        this.mRepresentativeClickListener = mRepresentativeClickListener;
    }

    @NonNull
    @NotNull
    @Override
    public AdditionRequestViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        return new AdditionRequestRecyclerViewAdapter.AdditionRequestViewHolder(AdditionRequestItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdditionRequestViewHolder holder, int position) {
        CitizenAdditionRequest item = lists.get(position);
        holder.mAdditionRequestItemBinding.tvCitizenName.setText(item.getCitizenName());
        holder.mAdditionRequestItemBinding.tvCitizenAddress.setText(item.getCitizenAddress());
        holder.mAdditionRequestItemBinding.tvCitizenHireDate.setText(item.getCitizenHireDate());
        holder.mAdditionRequestItemBinding.tvSeeDocumentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRepresentativeClickListener.onClick(position , holder.mAdditionRequestItemBinding.tvSeeDocumentDetail.getText().toString());
            }
        });
        holder.mAdditionRequestItemBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRepresentativeClickListener.onClick(position , holder.mAdditionRequestItemBinding.tvConfirm.getText().toString());
            }
        });
        holder.mAdditionRequestItemBinding.tvRegret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRepresentativeClickListener.onClick(position , holder.mAdditionRequestItemBinding.tvRegret.getText().toString());
            }
        });


    }

    @Override
    public int getItemCount() {
        return lists.size();
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
            lists.clear();
            lists.addAll((Collection<? extends CitizenAdditionRequest>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    class AdditionRequestViewHolder extends RecyclerView.ViewHolder {

        AdditionRequestItemBinding mAdditionRequestItemBinding;

        public AdditionRequestViewHolder(AdditionRequestItemBinding mAdditionRequestItemBinding) {
            super(mAdditionRequestItemBinding.getRoot());
            this.mAdditionRequestItemBinding = mAdditionRequestItemBinding;
            this.mAdditionRequestItemBinding.tvSeeDocumentDetail.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            this.mAdditionRequestItemBinding.tvCitizenName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            this.mAdditionRequestItemBinding.tvCitizenName.setSelected(true);
        }
    }

}
