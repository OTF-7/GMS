package com.GMS.representative.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.GMS.R;
import com.GMS.databinding.FragmentVerifiedBinding;
import com.GMS.representative.adapters.RecyclerViewAdapterCitizen;
import com.GMS.representative.helperClass.CitizenItem;

import java.util.ArrayList;

public class VerifiedFragment extends Fragment {


    public static final int FRAGMENT_ID=2;
    FragmentVerifiedBinding mBinding ;
    public VerifiedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       mBinding  = FragmentVerifiedBinding.inflate(inflater , container , false);

        ArrayList<CitizenItem> items = new ArrayList<>();
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3  , R.drawable.ic_identity_card_scan));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3 ,R.drawable.ic_identity_card_scan ));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_identity_card_scan));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" ,  3, R.drawable.ic_identity_card_scan));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_identity_card_scan));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_identity_card_scan));

        RecyclerViewAdapterCitizen adapter = new RecyclerViewAdapterCitizen(items  , FRAGMENT_ID);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvVerifiedFragment.setHasFixedSize(true);
        mBinding.rvVerifiedFragment.setLayoutManager(layoutManager);
        mBinding.rvVerifiedFragment.setAdapter(adapter);

       return mBinding.getRoot() ;
    }
}