package com.GMS.representative.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.GMS.representative.fragments.NeedScanRepFragment;
import com.GMS.representative.fragments.VerifiedRepFragment;

import org.jetbrains.annotations.NotNull;

public class ViewPager2Adapter  extends FragmentStateAdapter {
    public ViewPager2Adapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0 :
                return new NeedScanRepFragment();
            case 1: return new VerifiedRepFragment();
        }
        return new NeedScanRepFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
