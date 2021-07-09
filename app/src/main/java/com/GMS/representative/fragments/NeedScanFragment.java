package com.GMS.representative.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.GMS.R;

import com.GMS.representative.adapters.RecyclerViewAdapterCitizen;
import com.GMS.representative.helperClass.CitizenItem;
import com.GMS.databinding.FragmentNeedScanBinding;

import java.util.ArrayList;


public class NeedScanFragment extends Fragment {

    public static final  int FRAGMENT_ID=1 ;
    FragmentNeedScanBinding mBinding ;
    public NeedScanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentNeedScanBinding.inflate(inflater , container , false);

        ArrayList<com.GMS.representative.helperClass.CitizenItem> items = new ArrayList<>();
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3  , R.drawable.ic_qr_need_scan));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3 ,R.drawable.ic_qr_need_scan ));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_qr_need_scan));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" ,  3, R.drawable.ic_qr_need_scan));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_qr_need_scan));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_qr_need_scan));

        RecyclerViewAdapterCitizen adapter = new RecyclerViewAdapterCitizen(items , FRAGMENT_ID);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvNeedScanFragment.setHasFixedSize(true);
        mBinding.rvNeedScanFragment.setLayoutManager(layoutManager);
        mBinding.rvNeedScanFragment.setAdapter(adapter);

        return mBinding.getRoot();
    }
}