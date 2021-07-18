package com.GMS.aqel.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.GMS.R;
import com.GMS.aqel.adapters.RecyclerViewAqelAdapter;
import com.GMS.aqel.helperClass.CitizenItemOfAqel;
import com.GMS.databinding.FragmentNeedScanAqelBinding;

import java.util.ArrayList;

public class NeedScanAqelFragment extends Fragment {

    FragmentNeedScanAqelBinding mBinding;
    public static final int FRAGMENT_ID=1;
    public NeedScanAqelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentNeedScanAqelBinding.inflate(inflater , container , false);

        ArrayList<CitizenItemOfAqel> items = new ArrayList<>();
        items.add(new CitizenItemOfAqel("Abdulrahman Khalid" , "45d55d45s55g" , 3  , R.drawable.ic_qr_need_scan));
        items.add(new CitizenItemOfAqel("Omar Taha" , "45d55d45s55g" , 3 ,R.drawable.ic_qr_need_scan ));
        items.add(new CitizenItemOfAqel("Abubaker Khalid" , "45d55d45s55g" , 3 , R.drawable.ic_qr_need_scan));
        items.add(new CitizenItemOfAqel("Mohammed Shihab" , "45d55d45s55g" ,  3, R.drawable.ic_qr_need_scan));
        items.add(new CitizenItemOfAqel("Omar swaid" , "45d55d45s55g" , 3 , R.drawable.ic_qr_need_scan));
        items.add(new CitizenItemOfAqel("Hasan Someeri" , "45d55d45s55g" , 3 , R.drawable.ic_qr_need_scan));

        RecyclerViewAqelAdapter adapter = new RecyclerViewAqelAdapter(items , FRAGMENT_ID);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvNeedScanFragment.setHasFixedSize(true);
        mBinding.rvNeedScanFragment.setLayoutManager(layoutManager);
        mBinding.rvNeedScanFragment.setAdapter(adapter);
        return mBinding.getRoot();
    }
}