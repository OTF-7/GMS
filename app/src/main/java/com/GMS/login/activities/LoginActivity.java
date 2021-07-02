package com.GMS.login.activities;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.GMS.R;
import com.GMS.databinding.ActivityLoginBinding;
import com.GMS.login.adapters.AppBarStateChangeListener;
import com.GMS.login.adapters.LoginViewPagerAdapter;
import com.google.android.material.appbar.AppBarLayout;

public class LoginActivity extends AppCompatActivity {

    final int DELAYED_TIME = 50;
    final Handler handler = new Handler();
    ActivityLoginBinding loginBinding;
    LoginViewPagerAdapter viewPagerAdapter;
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

        appBarLayoutParams = loginBinding.loginAppbar.getLayoutParams();
        final float scale = getResources().getDisplayMetrics().density;

        appBarLayoutAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.zooming);

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

        loginBinding.loginNestedScrollView.setAlpha(0);
        loginBinding.loginNestedScrollView.setTranslationY(400);
        loginBinding.switchFormButton.setTranslationX(400);

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
                    appBarHeight = (int) (200f * scale + 0.5f);
                }
                if (appBarLayoutParams.height != appBarHeight) {
                    appBarLayoutParams.height = appBarHeight;
                    loginBinding.loginAppbar.startAnimation(appBarLayoutAnimation);
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

    @Override
    protected void onStart() {
        super.onStart();
        loginBinding.loginNestedScrollView.animate().translationY(0).alpha(1).setDuration(750).setStartDelay(100);
        loginBinding.switchFormButton.animate().translationX(0).setDuration(500).setStartDelay(700);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loginBinding.loginAppbar.setAnimation(appBarLayoutAnimation);
            }
        }, DELAYED_TIME);
    }
}