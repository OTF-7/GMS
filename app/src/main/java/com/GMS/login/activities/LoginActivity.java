package com.GMS.login.activities;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.GMS.R;
import com.GMS.databinding.ActivityLoginBinding;
import com.GMS.login.adapters.LoginViewPagerAdapter;
import com.GMS.login.helperClasses.AppBarStateChangeListener;
import com.google.android.material.appbar.AppBarLayout;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;

    LoginViewPagerAdapter viewPagerAdapter;
    AppBarLayout appBarLayout;
    Animation appBarLayoutAnimation;
    ViewGroup.LayoutParams appBarLayoutParams;
    int appBarHeight = 0;
    boolean signin_signup_state = false;
    TransitionDrawable transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        appBarLayoutParams = appBarLayout.getLayoutParams();
        final float scale = getResources().getDisplayMetrics().density;

        transition = (TransitionDrawable) loginBinding.getRoot().getBackground();

//        change the form shape when the toolbar collapses and expands
        loginBinding.loginAppbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
                    loginBinding.loginNestedScrollView.setBackground(getResources().getDrawable(R.drawable.login_scrolling_shape2));
                } else {
                    loginBinding.loginNestedScrollView.setBackground(getResources().getDrawable(R.drawable.login_scrolling_shape));
                }
            }
        });

        viewPagerAdapter = new LoginViewPagerAdapter(this);
        loginBinding.loginPager.setAdapter(viewPagerAdapter);
        loginBinding.loginPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (loginBinding.loginPager.getCurrentItem() == 0) {
                    loginBinding.switchFormButton.setImageResource(R.drawable.ic_add);
                    loginBinding.loginCollapsingToolbar.setTitle("Sign in");
                    transition.reverseTransition(800);
                    loginBinding.signUpImage.setVisibility(View.GONE);
                    loginBinding.signInImage.setVisibility(View.VISIBLE);
                    loginBinding.getRoot().setBackground(getResources().getDrawable(R.drawable.sign_in_background));
                    loginBinding.loginToolbar.setNavigationIcon(R.drawable.ic_sign_in);
                    signin_signup_state = false;
                    appBarLayoutAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.zooming);
                    appBarHeight = (int) (280f * scale + 0.5f);

                } else {
                    loginBinding.switchFormButton.setImageResource(R.drawable.ic_sign_in);
                    loginBinding.loginCollapsingToolbar.setTitle("Sign up");

                    loginBinding.signUpImage.setVisibility(View.VISIBLE);
                    loginBinding.signInImage.setVisibility(View.GONE);
                    transition.startTransition(200);
                    loginBinding.getRoot().setBackground(getResources().getDrawable(R.drawable.sign_up_background));
                    loginBinding.loginToolbar.setNavigationIcon(R.drawable.ic_sign_up);
                    signin_signup_state = true;
                    appBarLayoutAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.zooming);
                    appBarHeight = (int) (200f * scale + 0.5f);
                }
                if (appBarLayoutParams.height != appBarHeight) {
                    appBarLayoutParams.height = appBarHeight;
                    appBarLayout.startAnimation(appBarLayoutAnimation);
                    loginBinding.loginAppbar.setLayoutParams(appBarLayoutParams);
                }
            }
        });

        loginBinding.switchFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!signin_signup_state) {
                    loginBinding.loginPager.setCurrentItem(1);
                } else {
                    loginBinding.loginPager.setCurrentItem(0);
                }
            }
        });
    }

}