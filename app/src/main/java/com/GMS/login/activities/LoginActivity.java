package com.GMS.login.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager2.widget.ViewPager2;

import com.GMS.login.adapters.LoginViewPagerAdapter;
import com.GMS.R;
import com.GMS.login.helperClasses.AppBarStateChangeListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginActivity extends AppCompatActivity {

    ViewPager2 logInPager;
    LoginViewPagerAdapter viewPagerAdapter;
    FloatingActionButton switchFormButton;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    ViewGroup.LayoutParams appBarLayoutParams;
    int appBarHeight = 0;
    NestedScrollView nestedScrollView;
    boolean signin_signup_state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        logInPager = findViewById(R.id.login_pager);
        switchFormButton = findViewById(R.id.switch_form_button);
        collapsingToolbarLayout = findViewById(R.id.login_collapsing_toolbar);
        appBarLayout = findViewById(R.id.login_appbar);
        appBarLayoutParams = appBarLayout.getLayoutParams();
        final float scale = getResources().getDisplayMetrics().density;
        nestedScrollView = findViewById(R.id.login_nestedScrollView);

//        change the form shape when the toolbar collapses and expands
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
                    nestedScrollView.setBackground(getResources().getDrawable(R.drawable.login_scrolling_shape2));
                } else {
                    nestedScrollView.setBackground(getResources().getDrawable(R.drawable.login_scrolling_shape));
                }
            }
        });
        viewPagerAdapter = new LoginViewPagerAdapter(this);
        logInPager.setAdapter(viewPagerAdapter);

        logInPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (logInPager.getCurrentItem() == 0) {
                    switchFormButton.setImageResource(R.drawable.ic_add);
                    collapsingToolbarLayout.setTitle("Sign in");
                    appBarHeight = (int) (280f * scale + 0.5f);

                } else {
                    switchFormButton.setImageResource(R.drawable.ic_sign_in);
                    collapsingToolbarLayout.setTitle("Sign up");
                    appBarHeight = (int) (200f * scale + 0.5f);
                }
                appBarLayoutParams.height = appBarHeight;
                appBarLayout.setLayoutParams(appBarLayoutParams);
            }
        });

        switchFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!signin_signup_state) {
                    logInPager.setCurrentItem(1);
                    signin_signup_state = true;
                } else {
                    logInPager.setCurrentItem(0);
                    signin_signup_state = false;
                }

            }
        });
    }
}