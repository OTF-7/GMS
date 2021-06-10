package com.GMS.login.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.login.helperClasses.ActionItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerVieAdappterActions extends RecyclerView.Adapter<RecyclerVieAdappterActions.RecyclerViewHolderActions>{

    ArrayList<ActionItem> actions;

    public RecyclerVieAdappterActions(ArrayList<ActionItem> actions) {
        this.actions = actions;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerViewHolderActions onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_actions_item , null , false);
        RecyclerViewHolderActions viewHolder = new RecyclerViewHolderActions(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolderActions holder, int position) {

        ActionItem item = actions.get(position);
        holder.agent.setText(holder.agent.getText()+item.getAgent());
        holder.aqel.setText( holder.aqel.getText()+item.getAqel());
        holder.representative.setText(holder.representative.getText()+item.getRepresentative());
        holder.neighborhood.setText(holder.neighborhood.getText()+item.getNeighborhood());

        holder.price.setText(holder.price.getText()+String.valueOf(item.getPrice()));
        holder.qty.setText(holder.qty.getText()+String.valueOf(item.getQty()));
        holder.seller.setText(holder.seller.getText()+String.valueOf(item.getSeller()));
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    class RecyclerViewHolderActions extends RecyclerView.ViewHolder
    {
        TextView agent ,aqel , representative , qty , seller , price ,neighborhood;
        public RecyclerViewHolderActions(@NonNull @NotNull View itemView) {
            super(itemView);
            agent = itemView.findViewById(R.id.tv_agent_name);
            aqel = itemView.findViewById(R.id.tv_aqel_name);
            representative = itemView.findViewById(R.id.tv_representative_name);
            qty = itemView.findViewById(R.id.tv_quantity);
            seller = itemView.findViewById(R.id.tv_seller_quantity);
            price = itemView.findViewById(R.id.tv_action_price);
            neighborhood = itemView.findViewById(R.id.tv_neighborhood_name);

        }
    }
}
