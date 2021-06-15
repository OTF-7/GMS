package com.GMS.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.databinding.FragmentSettingMgrBinding;
import com.GMS.manager.adapters.RecyclerViewAdapterSetting;
import com.GMS.manager.helperClasses.SettingItem;

import java.util.ArrayList;

public class SettingFragment extends Fragment {

    FragmentSettingMgrBinding mBinding;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mBinding = FragmentSettingMgrBinding.inflate(inflater, container, false);
        // Inflate the recyclerView for this layout(fragment)
        ArrayList<com.GMS.manager.helperClasses.SettingItem> items = new ArrayList<>();
        items.add(new com.GMS.manager.helperClasses.SettingItem(R.drawable.ic_account, "Account", "previcy and Security"));
        items.add(new com.GMS.manager.helperClasses.SettingItem(R.drawable.ic_notifications, "Notifications", "tone and Info"));
        items.add(new SettingItem(R.drawable.ic_data_storage , "Data and Storage" ,"use network to get datat and save it"));
        items.add(new com.GMS.manager.helperClasses.SettingItem(R.drawable.ic_helping, "Helping", "assistant center , call us"));
         RecyclerViewAdapterSetting adapter = new RecyclerViewAdapterSetting(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvSetting.setHasFixedSize(true);
        mBinding.rvSetting.setLayoutManager(layoutManager);
        mBinding.rvSetting.setAdapter(adapter);

        return mBinding.getRoot();
    }
}