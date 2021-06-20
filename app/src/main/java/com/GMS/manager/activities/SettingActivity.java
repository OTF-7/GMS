package com.GMS.manager.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.GMS.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding mbinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(mbinding.getRoot());
    }
}