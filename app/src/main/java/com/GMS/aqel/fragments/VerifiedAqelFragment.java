package com.GMS.aqel.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.GMS.R;
import com.GMS.agent.adapters.RecyclerViewAqelAdapter;
import com.GMS.aqel.helperClass.CitizenItemOfAqel;
import com.GMS.databinding.FragmentVerifiedAqelBinding;
import com.GMS.databinding.FragmentVerifiedAqelBinding;
import com.GMS.representative.helperClass.CitizenItemOfRep;

import java.util.ArrayList;

public class VerifiedAqelFragment extends Fragment {

    FragmentVerifiedAqelBinding mBinding;
    public static final int FRAGMENT_ID=1;
    public VerifiedAqelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentVerifiedAqelBinding.inflate(inflater , container , false);


        ArrayList<CitizenItemOfAqel> items = new ArrayList<>();
        items.add(new CitizenItemOfAqel("Abdulrahman Khalid" , "45d55d45s55g" , 3  , R.drawable.ic_identity_card_scan));
        items.add(new CitizenItemOfAqel("Abdulrahman Khalid" , "45d55d45s55g" , 3 ,R.drawable.ic_identity_card_scan ));
        items.add(new CitizenItemOfAqel("Abdulrahman Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_identity_card_scan));
        items.add(new CitizenItemOfAqel("Abdulrahman Khalid" , "45d55d45s55g" ,  3, R.drawable.ic_identity_card_scan));
        items.add(new CitizenItemOfAqel("Abdulrahman Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_identity_card_scan));
        items.add(new CitizenItemOfAqel("Abdulrahman Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_identity_card_scan));

        RecyclerViewAqelAdapter adapter = new RecyclerViewAqelAdapter(items , FRAGMENT_ID);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvVerifiedFragment.setHasFixedSize(true);
        mBinding.rvVerifiedFragment.setLayoutManager(layoutManager);
        mBinding.rvVerifiedFragment.setAdapter(adapter);


        return mBinding.getRoot();

    }
}