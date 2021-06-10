package com.GMS.login.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.GMS.R;
import com.GMS.login.adapters.RecyclerViewAdapterSetting;

import java.util.ArrayList;

public class SettingFragment extends Fragment {

    RecyclerView rvSetting ;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_setting_mgr, container, false);
        // Inflate the recyclerView for this layout(fragment)
        rvSetting = view.findViewById(R.id.rv_setting);
        ArrayList<com.example.manager.SettingItem> items = new ArrayList<>();
         items.add(new com.example.manager.SettingItem(R.drawable.ic_account, "Account" ,"previcy and Security"));
        items.add(new com.example.manager.SettingItem(R.drawable.ic_notifications, "Notifications" ,"tone and Info"));
        items.add(new com.example.manager.SettingItem(R.drawable.ic_helping, "Helping" ,"assistant center , call us"));

        RecyclerViewAdapterSetting adapter = new RecyclerViewAdapterSetting(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvSetting.setHasFixedSize(true);
        rvSetting.setLayoutManager(layoutManager);
        rvSetting.setAdapter(adapter);

        return view ;
    }
}