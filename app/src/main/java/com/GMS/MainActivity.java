package com.GMS;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.GMS.databinding.ActivityMainBinding;
import com.GMS.login.adapters.LoginViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    LoginViewPagerAdapter loginViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());


 /*       pager = findViewById(R.id.pager);
        loginViewPagerAdapter = new LoginViewPagerAdapter(getSupportFragmentManager(), 1);
        pager.setAdapter(loginViewPagerAdapter);*/
    }

}