package com.GMS.login.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;

import com.GMS.R;
import com.GMS.databinding.FragmentSingInBinding;


public class SingInFragment extends Fragment {
    FragmentSingInBinding signinBinding;
    Animation fieldsAnimation;

    public SingInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        signinBinding = FragmentSingInBinding.inflate(inflater, container, false);

        fieldsAnimation = AnimationUtils.loadAnimation(getContext(),
                R.anim.lefttoright);


        return signinBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}