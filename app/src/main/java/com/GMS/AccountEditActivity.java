package com.GMS;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.GMS.databinding.ActivityAccountEditBinding;

public class AccountEditActivity extends AppCompatActivity {

    private ActivityAccountEditBinding mBinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAccountEditBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }
}