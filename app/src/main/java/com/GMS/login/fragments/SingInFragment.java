package com.GMS.login.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.GMS.R;
import com.GMS.login.activities.ManagerActivity;


public class SingInFragment extends Fragment {

    Button btnSignIn ;
    public SingInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sing_in, container, false);
        btnSignIn = view.findViewById(R.id.sign_in_button);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext() , ManagerActivity.class);
                startActivity(intent);
                Toast.makeText(getContext() , "sign in button " , Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}