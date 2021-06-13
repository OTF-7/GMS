package com.GMS.login.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.GMS.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {
    FragmentSignUpBinding signupBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        signupBinding = FragmentSignUpBinding.inflate(inflater, container, false);
        return signupBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        signupBinding = null;
    }
}