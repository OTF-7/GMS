package com.GMS.representative.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.GMS.representative.fragments.NeedScanRepFragment;
import com.GMS.representative.fragments.VerifiedRepFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> lstsFragments = new ArrayList<>();
    ArrayList<String> lstsStrings = new ArrayList<>();

    public MainAdapter(@NonNull @NotNull FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment frg , String str)
    {
        lstsFragments.add(frg);
        lstsStrings.add(str);
    }
    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0 :
                return new NeedScanRepFragment();
            case 1: return new VerifiedRepFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lstsStrings.get(position);
    }
}
