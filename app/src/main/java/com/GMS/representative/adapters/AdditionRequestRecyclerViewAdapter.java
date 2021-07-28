package com.GMS.representative.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.databinding.AdditionRequestItemBinding;
import com.GMS.representative.helperClass.CitizenAdditionRequest;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdditionRequestRecyclerViewAdapter extends RecyclerView.Adapter<AdditionRequestRecyclerViewAdapter.AdditionRequestViewHolder> {


    ArrayList<CitizenAdditionRequest> lsts = new ArrayList<>();

    public AdditionRequestRecyclerViewAdapter(ArrayList<CitizenAdditionRequest> lsts) {
        this.lsts = lsts;
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
        holder.mAdditionRequestItemBinding.tvCitizenHireDate.setText(item.getCitizenName());
        holder.mAdditionRequestItemBinding.tvCitizenAddress.setText(item.getCitizenAddress());
        holder.mAdditionRequestItemBinding.tvCitizenHireDate.setText(item.getCitizenHireDate());

    }

    @Override
    public int getItemCount() {
        return lsts.size();
    }

    class AdditionRequestViewHolder extends RecyclerView.ViewHolder {

        AdditionRequestItemBinding mAdditionRequestItemBinding;

        public AdditionRequestViewHolder(AdditionRequestItemBinding mAdditionRequestItemBinding) {
            super(mAdditionRequestItemBinding.getRoot());
            this.mAdditionRequestItemBinding = mAdditionRequestItemBinding;
            this.mAdditionRequestItemBinding.tvSeeDocumentDetail.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}
