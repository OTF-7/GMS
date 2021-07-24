package com.GMS.representative.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.aqel.helperClass.CitizenItemOfAqel;
import com.GMS.representative.helperClass.CitizenItemOfRep;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class RecyclerViewRepAdapterCitizen extends RecyclerView.Adapter<RecyclerViewRepAdapterCitizen.ViewHolderCitizen>
    implements Filterable
{
     ArrayList<CitizenItemOfRep> lsts = new ArrayList<>();
    ArrayList<CitizenItemOfRep> fullLsts ;
     int typeOfPage;

    public RecyclerViewRepAdapterCitizen(ArrayList<CitizenItemOfRep> lsts, int typeOfPage) {
        this.lsts = lsts;
        this.typeOfPage = typeOfPage;
        this.fullLsts =new ArrayList<>(this.lsts);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolderCitizen onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.citizen_item_rv, parent , false);
        ViewHolderCitizen viewHolder= new ViewHolderCitizen(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderCitizen holder, int position) {

        CitizenItemOfRep item = lsts.get(position);

        holder.citizenName.setText(item.getCitizenName());
        holder.citizenId.setText(item.getCitizenId());
        holder.count.setText(String.valueOf(item.getCountCylinder()));
        holder.ivStatte.setImageResource(item.getIvStateResource());
    }


    @Override
    public int getItemCount() {
        return lsts.size();

    }

    @Override
    public Filter getFilter() {
        return filterUser;
    }
   private Filter filterUser = new Filter() {
       @Override
       protected FilterResults performFiltering(CharSequence constraint) {
           String searchText = constraint.toString().toLowerCase();
           ArrayList<CitizenItemOfRep> tempLst = new ArrayList<>();
           if(searchText.isEmpty())

           {
               tempLst.addAll(fullLsts);
           }
           else {
               for(CitizenItemOfRep item : fullLsts)
                   if(item.getCitizenName().toLowerCase().contains(searchText))
                   {
                       tempLst.add(item);
                   }
           }
           FilterResults filterResults = new FilterResults();
           filterResults.values=tempLst;
           return filterResults;
       }

       @Override
       protected void publishResults(CharSequence constraint, FilterResults results) {
           lsts.clear();
           lsts.addAll((Collection<? extends CitizenItemOfRep>) results.values);
           notifyDataSetChanged();
       }
   };
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
