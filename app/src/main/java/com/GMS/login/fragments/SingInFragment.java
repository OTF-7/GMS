package com.GMS.login.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.GMS.agent.activities.AgentActivity;
import com.GMS.aqel.activities.AqelActivity;
import com.GMS.databinding.FragmentSingInBinding;
import com.GMS.manager.activities.ManagerActivity;
import com.GMS.agent.activities.AgentActivity;
import com.GMS.representative.activities.RepresentativeActivity;

public class SingInFragment extends Fragment {
    final int DELAYED_TIME = 2700;
    final Handler handler = new Handler();
    FragmentSingInBinding signinBinding;

    public SingInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        signinBinding = FragmentSingInBinding.inflate(inflater, container, false);

        signinBinding.usernameField.setTranslationY(800);
        signinBinding.usernameField.setAlpha(0);
        signinBinding.passwordField.setTranslationY(800);
        signinBinding.passwordField.setAlpha(0);
        signinBinding.signInButton.setTranslationY(1600);
        signinBinding.signInButton.setAlpha(0);
        signinBinding.forgotPasswordButton.setTranslationX(2000);
        signinBinding.forgotPasswordButton.setAlpha(0);
        signinBinding.fingerPrintAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinBinding.fingerPrintAnimation.playAnimation();
            }
        });
        signinBinding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signinBinding.usernameField.getEditText().getText().toString().equals("age") &&
                        signinBinding.passwordField.getEditText().getText().toString().equals("123")) {
                    Intent intent = new Intent(signinBinding.getRoot().getContext(), AgentActivity.class);
                    startActivity(intent);
                    Toast.makeText(signinBinding.getRoot().getContext(), "Agent", Toast.LENGTH_SHORT).show();


                } else if (signinBinding.usernameField.getEditText().getText().toString().equals("mgr") &&
                        signinBinding.passwordField.getEditText().getText().toString().equals("123")) {
                    Intent intent = new Intent(signinBinding.getRoot().getContext(), ManagerActivity.class);
                    startActivity(intent);
                    Toast.makeText(signinBinding.getRoot().getContext(), "Manager", Toast.LENGTH_SHORT).show();
                }
                else if(signinBinding.usernameField.getEditText().getText().toString().equals("rep") &&
                        signinBinding.passwordField.getEditText().getText().toString().equals("123"))
                {

                    Intent intent = new Intent(signinBinding.getRoot().getContext(), RepresentativeActivity.class);
                    startActivity(intent);
                    Toast.makeText(signinBinding.getRoot().getContext(), "Representative", Toast.LENGTH_SHORT).show();
              }
                else if(signinBinding.usernameField.getEditText().getText().toString().equals("aqe") &&
                        signinBinding.passwordField.getEditText().getText().toString().equals("123"))
                {

                    Intent intent = new Intent(signinBinding.getRoot().getContext(), AqelActivity.class);
                    startActivity(intent);
                    Toast.makeText(signinBinding.getRoot().getContext(), "Aqel", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return signinBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        signinBinding.usernameField.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1200);
        signinBinding.passwordField.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1800);
        signinBinding.signInButton.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(2400);
        signinBinding.forgotPasswordButton.animate().translationX(0).alpha(1).setDuration(750).setStartDelay(3000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        signinBinding = null;
    }
}