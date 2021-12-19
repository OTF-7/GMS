package com.GMS.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.GMS.databinding.FragmentSignUpRequestsBinding;


public class ManagerSignUpRequestsFragment extends Fragment {
    FragmentSignUpRequestsBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentSignUpRequestsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }
}