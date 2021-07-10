package com.GMS.aqel.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.GMS.aqel.fragments.NeedScanAqelFragment;
import com.GMS.aqel.fragments.VerifiedAqelFragment;

import org.jetbrains.annotations.NotNull;

public class ViewPager2AqelAdapter extends FragmentStateAdapter {
    public ViewPager2AqelAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0 :
                return new NeedScanAqelFragment();
            case 1: return new VerifiedAqelFragment();
        }
        return new NeedScanAqelFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
