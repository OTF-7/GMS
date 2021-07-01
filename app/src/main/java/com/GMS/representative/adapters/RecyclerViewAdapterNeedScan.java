package com.GMS.representative.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.representative.helperClasses.CitizenItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapterNeedScan  extends RecyclerView.Adapter<RecyclerViewAdapterNeedScan.ViewHolderNeedScan> {

    ArrayList<CitizenItem> citizenItems;

    public RecyclerViewAdapterNeedScan(ArrayList<CitizenItem> citizenItems) {
        this.citizenItems = citizenItems;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderNeedScan onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.need_scan_item_representative, null , false);
        ViewHolderNeedScan viewHolder= new ViewHolderNeedScan(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderNeedScan holder, int position) {

        CitizenItem item = citizenItems.get(position);

        holder.citizenName.setText(holder.citizenName.getText()+item.getCitizenName());
        holder.citizenId.setText(holder.citizenId.getText()+item.getCitizenId());
        holder.count.setText(holder.count.getText()+String.valueOf(item.getCountCylinder()));

    }

    @Override
    public int getItemCount() {
        return citizenItems.size();
    }

    class ViewHolderNeedScan extends RecyclerView.ViewHolder
    {
            TextView citizenName , citizenId , count;

        public ViewHolderNeedScan(@NonNull @NotNull View itemView) {
            super(itemView);
            citizenName = itemView.findViewById(R.id.tv_citizen_name);
            citizenId = itemView.findViewById(R.id.tv_citizen_id);
            count = itemView.findViewById(R.id.tv_count);
        }
    }
}