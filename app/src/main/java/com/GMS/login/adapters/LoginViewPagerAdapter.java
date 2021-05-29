package com.GMS.login.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.GMS.login.fragments.SignUpFragment;
import com.GMS.login.fragments.SingInFragment;

public class LoginViewPagerAdapter extends FragmentStateAdapter {
    private static final int LOG_IN_SCREENS = 2;

    public LoginViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new SingInFragment();
            case 1: return new SignUpFragment();
        }
        return  null;
    }

    @Override
    public int getItemCount() {
        return LOG_IN_SCREENS;
    }
}
