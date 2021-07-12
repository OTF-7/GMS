package com.GMS.representative.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addition_request_item,parent , false);
        AdditionRequestViewHolder viewHolder = new AdditionRequestViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdditionRequestViewHolder holder, int position) {
        CitizenAdditionRequest item = lsts.get(position);
        holder.tvCitizenName.setText(item.getCitizenName());
        holder.tvCitizenAddress.setText(item.getCitizenAddress());
        holder.tvCitizenHireDate.setText(item.getCitizenHireDate());


    }

    @Override
    public int getItemCount() {
        return lsts.size();
    }

    class AdditionRequestViewHolder extends RecyclerView.ViewHolder
    {
        ImageView citizenPicture ;
        TextView tvCitizenName , tvCitizenAddress, tvCitizenHireDate , tvSeeDocument ;
        public AdditionRequestViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            citizenPicture = itemView.findViewById(R.id.citizen_picture);
            tvCitizenName = itemView.findViewById(R.id.tv_citizen_name);
            tvCitizenAddress = itemView.findViewById(R.id.tv_citizen_address);
            tvCitizenHireDate = itemView.findViewById(R.id.tv_citizen_hire_date);
            tvSeeDocument = itemView.findViewById(R.id.tv_see_document_detail);
            tvSeeDocument.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }
}
