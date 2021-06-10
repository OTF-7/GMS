package com.GMS.login.fragments;

import android.os.Bundle;
import android.os.Handler;
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
    Animation buttonAnimation;
    Animation formAnimation;
    Animation fabAnimation;
    Animation appBarLayoutAnimation;

    public SingInFragment() {
        // Required empty public constructor
    }

    final int DELAYED_TIME = 2700;
    final Handler handler = new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        signinBinding = FragmentSingInBinding.inflate(inflater, container, false);
        fieldsAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright);


        signinBinding.usernameField.setTranslationY(800);
        signinBinding.usernameField.setAlpha(0);
        signinBinding.passwordField.setTranslationY(800);
        signinBinding.passwordField.setAlpha(0);
        signinBinding.signInButton.setTranslationY(1600);
        signinBinding.signInButton.setAlpha(0);

        return signinBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        signinBinding.usernameField.animate().translationY(0).alpha(1).setDuration(750).setStartDelay(1200);
        signinBinding.passwordField.animate().translationY(0).alpha(1).setDuration(750).setStartDelay(1500);
        signinBinding.signInButton.animate().translationY(0).alpha(1).setDuration(750).setStartDelay(1800);

    }
}