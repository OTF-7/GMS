package com.GMS.representative.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.GMS.R;
import com.GMS.databinding.FragmentNeedScanBinding;
import com.GMS.databinding.FragmentVerifiedBinding;
import com.GMS.representative.adapters.RecyclerViewAdapterNeedScan;
import com.GMS.representative.adapters.RecyclerViewAdapterVerified;
import com.GMS.representative.helperClasses.CitizenItem;

import java.util.ArrayList;

public class VerifiedFragment extends Fragment {

    FragmentVerifiedBinding mBinding;

    public VerifiedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentVerifiedBinding .inflate(inflater , container , false);


        ArrayList<CitizenItem> items = new ArrayList<>();
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));

        RecyclerViewAdapterVerified adapter = new RecyclerViewAdapterVerified(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvVerifiedItem.setHasFixedSize(true);
        mBinding.rvVerifiedItem.setLayoutManager(layoutManager);
        mBinding.rvVerifiedItem.setAdapter(adapter);

        return mBinding.getRoot();
    }
}