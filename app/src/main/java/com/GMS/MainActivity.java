package com.GMS;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.GMS.login.adapters.LoginViewPagerAdapter;
import com.cuberto.liquid_swipe.LiquidPager;

public class MainActivity extends AppCompatActivity {

    LiquidPager pager;
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