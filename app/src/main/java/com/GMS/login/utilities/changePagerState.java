package com.GMS.login.utilities;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.GMS.R;
import com.GMS.databinding.ActivityLoginBinding;
import com.GMS.login.activities.LoginActivity;

public class changePagerState {
    public static boolean signin_signup_state = false;
    public static int appBarHeight = 0;
    public static Animation appBarLayoutAnimation;
    public static ViewGroup.LayoutParams appBarLayoutParams;

    public static void changePager(ActivityLoginBinding binding) {
        if (!signin_signup_state) {
            binding.loginPager.setCurrentItem(1);
        } else {
            binding.loginPager.setCurrentItem(0);
        }
    }

    public static void gotoSignUp(ActivityLoginBinding binding) {
        binding.loginPager.setCurrentItem(1);
    }

    public static void gotoSignIn(ActivityLoginBinding binding) {
        binding.loginPager.setCurrentItem(0);
    }

    public static void changeAppHeight(ActivityLoginBinding loginBinding,
                                       LoginActivity activity,
                                       float height) {
        appBarLayoutParams = loginBinding.loginAppbar.getLayoutParams();
        final float scale = activity.getResources().getDisplayMetrics().density;
        appBarLayoutAnimation = AnimationUtils.loadAnimation(activity.getApplicationContext(),
                R.anim.zooming);
        appBarHeight = (int) (height * scale + 0.5f);
        if (appBarLayoutParams.height != changePagerState.appBarHeight) {
            appBarLayoutParams.height = changePagerState.appBarHeight;
            loginBinding.loginAppbar.startAnimation(appBarLayoutAnimation);
            loginBinding.loginAppbar.setLayoutParams(appBarLayoutParams);
        }
        loginBinding.loginAppbar.setExpanded(true);
    }
}

