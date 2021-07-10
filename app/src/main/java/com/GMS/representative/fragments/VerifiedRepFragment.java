package com.GMS.representative.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.GMS.R;
import com.GMS.databinding.FragmentVerifiedRepBinding;
import com.GMS.representative.adapters.RecyclerViewRepAdapterCitizen;
import com.GMS.representative.helperClass.CitizenItemOfRep;

import java.util.ArrayList;

public class VerifiedRepFragment extends Fragment {


    public static final int FRAGMENT_ID=2;
    FragmentVerifiedRepBinding mBinding ;
    public VerifiedRepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       mBinding  = FragmentVerifiedRepBinding.inflate(inflater , container , false);

        ArrayList<CitizenItemOfRep> items = new ArrayList<>();
        items.add(new CitizenItemOfRep("Abdulrahman Khalid" , "45d55d45s55g" , 3  , R.drawable.ic_identity_card_scan));
        items.add(new CitizenItemOfRep("Abdulrahman Khalid" , "45d55d45s55g" , 3 ,R.drawable.ic_identity_card_scan ));
        items.add(new CitizenItemOfRep("Abdulrahman Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_identity_card_scan));
        items.add(new CitizenItemOfRep("Abdulrahman Khalid" , "45d55d45s55g" ,  3, R.drawable.ic_identity_card_scan));
        items.add(new CitizenItemOfRep("Abdulrahman Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_identity_card_scan));
        items.add(new CitizenItemOfRep("Abdulrahman Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_identity_card_scan));

        RecyclerViewRepAdapterCitizen adapter = new RecyclerViewRepAdapterCitizen(items  , FRAGMENT_ID);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvVerifiedFragment.setHasFixedSize(true);
        mBinding.rvVerifiedFragment.setLayoutManager(layoutManager);
        mBinding.rvVerifiedFragment.setAdapter(adapter);

       return mBinding.getRoot() ;
    }
}