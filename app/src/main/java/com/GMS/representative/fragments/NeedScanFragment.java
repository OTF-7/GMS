package com.GMS.representative.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.GMS.R;
import com.GMS.agent.adapters.RecyclerViewAdapterCitizen;
import com.GMS.agent.helperClasses.CitizenItem;
import com.GMS.databinding.FragmentNeedScanBinding;

import java.util.ArrayList;


public class NeedScanFragment extends Fragment {


    FragmentNeedScanBinding mBinding ;
    public NeedScanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentNeedScanBinding.inflate(inflater , container , false);

        ArrayList<CitizenItem> items = new ArrayList<>();
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));

        RecyclerViewAdapterCitizen adapter = new RecyclerViewAdapterCitizen(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvNeedScanFragment.setHasFixedSize(true);
        mBinding.rvNeedScanFragment.setLayoutManager(layoutManager);
        mBinding.rvNeedScanFragment.setAdapter(adapter);


        return mBinding.getRoot();
    }
}