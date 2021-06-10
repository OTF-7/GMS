package com.GMS.login.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterSetting extends RecyclerView.Adapter<RecyclerViewAdapterSetting.RecyclerViewViewHolderSetting>
{
    ArrayList<com.example.manager.SettingItem> settingItems ;

    public RecyclerViewAdapterSetting(ArrayList<com.example.manager.SettingItem> settingItems)
    {
        this.settingItems = settingItems;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolderSetting onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_setting_item,null , false);
        RecyclerViewViewHolderSetting viewHolder = new RecyclerViewViewHolderSetting(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return settingItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolderSetting holder, int position)
    {
        com.example.manager.SettingItem item = settingItems.get(position);
        holder.circleImageView.setImageResource(item.getIconResource());
        holder.tvSettingItemTitle.setText(item.getTitle());
        holder.getTvSettingItemDescription.setText(item.getDescription());

    }
    class RecyclerViewViewHolderSetting extends RecyclerView.ViewHolder
     {
         TextView tvSettingItemTitle , getTvSettingItemDescription ;
       CircleImageView circleImageView ;
         public RecyclerViewViewHolderSetting(@NonNull View itemView)
         {
             super(itemView);
             circleImageView = itemView.findViewById(R.id.iv_setting_item_icon);
             tvSettingItemTitle = itemView.findViewById(R.id.tv_setting_item_title);
             getTvSettingItemDescription =itemView.findViewById(R.id.tv_setting_item_description);

         }
     }

}
