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

public class RecyclerViewAdapterVerified  extends RecyclerView.Adapter<RecyclerViewAdapterVerified.ViewHolderVerified> {

    ArrayList<CitizenItem> items ;

    public RecyclerViewAdapterVerified(ArrayList<CitizenItem> items) {
        this.items = items;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderVerified onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.need_scan_item_representative, null , false);
        ViewHolderVerified viewHolder= new ViewHolderVerified(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderVerified holder, int position) {
        CitizenItem item = items.get(position);

        holder.citizenName.setText(holder.citizenName.getText()+item.getCitizenName());
        holder.citizenId.setText(holder.citizenId.getText()+item.getCitizenId());
        holder.count.setText(holder.count.getText()+String.valueOf(item.getCountCylinder()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolderVerified extends RecyclerView.ViewHolder
    {
        TextView citizenName , citizenId , count;
        public ViewHolderVerified(@NonNull @NotNull View itemView) {
            super(itemView);
            citizenName = itemView.findViewById(R.id.tv_citizen_name);
            citizenId = itemView.findViewById(R.id.tv_citizen_id);
            count = itemView.findViewById(R.id.tv_count);
        }
    }
}
