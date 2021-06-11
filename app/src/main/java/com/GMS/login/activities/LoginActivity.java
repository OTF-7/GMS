package com.GMS.login.activities;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager2.widget.ViewPager2;

import com.GMS.R;
import com.GMS.login.adapters.LoginViewPagerAdapter;
import com.GMS.login.helperClasses.AppBarStateChangeListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginActivity extends AppCompatActivity {

    CoordinatorLayout rootLayout;
    ViewPager2 logInPager;
    ImageView sign_up_image, sign_in_image;
    LoginViewPagerAdapter viewPagerAdapter;
    FloatingActionButton switchFormButton;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    Animation appBarLayoutAnimation;
    ViewGroup.LayoutParams appBarLayoutParams;
    int appBarHeight = 0;
    NestedScrollView nestedScrollView;
    boolean signin_signup_state = false;
    TransitionDrawable transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        rootLayout = findViewById(R.id.login_root_layout);
        logInPager = findViewById(R.id.login_pager);
        sign_in_image = findViewById(R.id.sign_in_image);
        sign_up_image = findViewById(R.id.sign_up_image);
        switchFormButton = findViewById(R.id.switch_form_button);
        collapsingToolbarLayout = findViewById(R.id.login_collapsing_toolbar);
        appBarLayout = findViewById(R.id.login_appbar);
        toolbar = findViewById(R.id.login_toolbar);
        appBarLayoutParams = appBarLayout.getLayoutParams();
        final float scale = getResources().getDisplayMetrics().density;
        nestedScrollView = findViewById(R.id.login_nestedScrollView);
        transition = (TransitionDrawable) rootLayout.getBackground();

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
                    transition.reverseTransition(800);
                    sign_up_image.setVisibility(View.GONE);
                    sign_in_image.setVisibility(View.VISIBLE);
                    rootLayout.setBackground(getResources().getDrawable(R.drawable.sign_in_background));
                    toolbar.setNavigationIcon(R.drawable.ic_sign_in);
                    signin_signup_state = false;
                    appBarLayoutAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.zooming);
                    appBarHeight = (int) (280f * scale + 0.5f);

                } else {
                    switchFormButton.setImageResource(R.drawable.ic_sign_in);
                    collapsingToolbarLayout.setTitle("Sign up");

                    sign_up_image.setVisibility(View.VISIBLE);
                    sign_in_image.setVisibility(View.GONE);
                    transition.startTransition(200);
                    rootLayout.setBackground(getResources().getDrawable(R.drawable.sign_up_background));
                    toolbar.setNavigationIcon(R.drawable.ic_sign_up);
                    signin_signup_state = true;
                    appBarLayoutAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.zooming);
                    appBarHeight = (int) (200f * scale + 0.5f);
                }
                if (appBarLayoutParams.height != appBarHeight) {
                    appBarLayoutParams.height = appBarHeight;
                    appBarLayout.startAnimation(appBarLayoutAnimation);
                    appBarLayout.setLayoutParams(appBarLayoutParams);
                }
            }
        });

        switchFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!signin_signup_state) {
                    logInPager.setCurrentItem(1);
                } else {
                    logInPager.setCurrentItem(0);
                }
            }
        });
    }
}