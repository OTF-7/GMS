package com.GMS.login.activities;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.GMS.GeneralClasses.User;
import com.GMS.R;
import com.GMS.agent.activities.AgentActivity;
import com.GMS.aqel.activities.AqelActivity;
import com.GMS.databinding.ActivityLoginBinding;
import com.GMS.login.adapters.AppBarStateChangeListener;
import com.GMS.login.adapters.LoginViewPagerAdapter;
import com.GMS.login.utilities.changePagerState;
import com.GMS.manager.activities.ManagerActivity;
import com.GMS.representative.activities.RepresentativeActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    public static ActivityLoginBinding loginBinding;
    final int DELAYED_TIME = 50;
    final Handler handler = new Handler();
    public Animation appBarLayoutAnimation;
    LoginViewPagerAdapter viewPagerAdapter;
    TransitionDrawable transition;
    FirebaseAuth mAuth;
    FirebaseUser user;
    LoginActivity mActivity = this;
    float height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        checkIfItIsSignedIn();
        setContentView(loginBinding.getRoot());


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        transition = (TransitionDrawable) loginBinding.getRoot().getBackground();
//        change the form shape when the toolbar collapses and expands
        loginBinding.loginAppbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
                    if (loginBinding.loginPager.getCurrentItem() == 1)
                        loginBinding.signUpImage.setVisibility(View.GONE);
                    loginBinding.loginNestedScrollView.setBackground(getResources().getDrawable(R.drawable.login_scrolling_shape2));
                } else {
                    if (loginBinding.loginPager.getCurrentItem() == 1)
                        loginBinding.signUpImage.setVisibility(View.VISIBLE);
                    loginBinding.loginNestedScrollView.setBackground(getResources().getDrawable(R.drawable.login_scrolling_shape));
                }
            }
        });
        appBarLayoutAnimation = AnimationUtils.loadAnimation(this,
                R.anim.zooming);
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
                    changePagerState.signin_signup_state = false;
                    height = 280f;
                } else {
                    loginBinding.switchFormButton.setImageResource(R.drawable.ic_sign_in);
                    loginBinding.loginCollapsingToolbar.setTitle("Sign up");
                    loginBinding.signUpImage.setVisibility(View.VISIBLE);
                    loginBinding.signInImage.setVisibility(View.GONE);
                    transition.startTransition(200);
                    loginBinding.getRoot().setBackground(getResources().getDrawable(R.drawable.sign_up_background));
                    loginBinding.loginToolbar.setNavigationIcon(R.drawable.ic_sign_up);
                    changePagerState.signin_signup_state = true;
                    height = 200f;
                }
                changePagerState.changeAppHeight(loginBinding, mActivity, height);
            }
        });

        loginBinding.switchFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePagerState.changePager(loginBinding);
            }
        });

    }

    private void checkIfItIsSignedIn() {
        if (user==null )
            return;
        Gson gson = new Gson();
        String json = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext())
                .getString(getString(R.string.user_data_as_json), "");
        User userData = gson.fromJson(json, User.class);
        Object destination;
        Toast.makeText(getBaseContext(), String.valueOf(userData.getUserType()), Toast.LENGTH_SHORT).show();
        switch (Objects.requireNonNull(userData).getUserType()) {
            case 1:
                destination = RepresentativeActivity.class;
                break;
            case 2:
                destination = AgentActivity.class;
                break;
            case 3:
                destination = AqelActivity.class;
                break;
            default:
                destination = ManagerActivity.class;
                break;
        }
        Intent intent = new Intent(getBaseContext(), (Class) destination);
        startActivity(intent);
        finish();
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