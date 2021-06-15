package com.GMS.manager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.GMS.R;
import com.GMS.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding mbinding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(mbinding.getRoot());
    }
}