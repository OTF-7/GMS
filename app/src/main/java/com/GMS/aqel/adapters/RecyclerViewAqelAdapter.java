package com.GMS.aqel.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.aqel.helperClass.CitizenItemOfAqel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAqelAdapter  extends RecyclerView.Adapter<RecyclerViewAqelAdapter.ViewHolderCitizen> {

    ArrayList<CitizenItemOfAqel> lstsCitizen ;
    int typeOfPage ;

    public RecyclerViewAqelAdapter(ArrayList<CitizenItemOfAqel> lstsCitizen, int typeOfPage) {
        this.lstsCitizen = lstsCitizen;
        this.typeOfPage = typeOfPage;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderCitizen onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.citizen_item_rv , parent , false);
        ViewHolderCitizen viewHoder = new ViewHolderCitizen(view);

        return viewHoder ;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderCitizen holder, int position) {

        CitizenItemOfAqel item = lstsCitizen.get(position);

        holder.citizenName.setText(holder.citizenName.getText()+item.getCitizenName());
        holder.citizenId.setText(holder.citizenId.getText()+item.getCitizenId());
        holder.count.setText(holder.count.getText()+String.valueOf(item.getCountCylinder()));
        holder.ivStatte.setImageResource(item.getIvStateResource());
    }

    @Override
    public int getItemCount() {
        return lstsCitizen.size();
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
