package com.GMS.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.GMS.databinding.FragmentSignUpRequestsBinding;


public class SignUpRequestsFragment extends Fragment {
    private FragmentSignUpRequestsBinding mBinding;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentSignUpRequestsBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

}