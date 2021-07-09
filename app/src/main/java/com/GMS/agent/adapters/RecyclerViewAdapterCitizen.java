package com.GMS.agent.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.agent.helperClasses.CitizenItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapterCitizen extends RecyclerView.Adapter<RecyclerViewAdapterCitizen.ViewHolderCitizen> {

    ArrayList<CitizenItem> items ;

    public RecyclerViewAdapterCitizen(ArrayList<CitizenItem> items) {
        this.items = items;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderCitizen onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.citizen_item_rv, null , false);
        ViewHolderCitizen viewHolder= new ViewHolderCitizen(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderCitizen holder, int position) {
        CitizenItem item = items.get(position);

        holder.citizenName.setText(holder.citizenName.getText()+item.getCitizenName());
        holder.citizenId.setText(holder.citizenId.getText()+item.getCitizenId());
        holder.count.setText(holder.count.getText()+String.valueOf(item.getCountCylinder()));
        holder.ivStatte.setImageResource(item.getIvStateResource());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    class ViewHolderCitizen extends RecyclerView.ViewHolder
    {
        TextView citizenName , citizenId , count;
        ImageView ivStatte ;
        public ViewHolderCitizen(@NonNull @NotNull View itemView) {
            super(itemView);
            citizenName = itemView.findViewById(R.id.tv_citizen_name);
            citizenId = itemView.findViewById(R.id.tv_citizen_id);
            count = itemView.findViewById(R.id.tv_count);
            ivStatte = itemView.findViewById(R.id.iv_state);
        }
    }
}
