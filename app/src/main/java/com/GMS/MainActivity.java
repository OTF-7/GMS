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
        setContentView(R.layout.activity_main);
 /*       pager = findViewById(R.id.pager);
        loginViewPagerAdapter = new LoginViewPagerAdapter(getSupportFragmentManager(), 1);
        pager.setAdapter(loginViewPagerAdapter);*/
    }

}