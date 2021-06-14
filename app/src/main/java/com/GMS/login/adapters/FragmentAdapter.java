package com.GMS.login.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.GMS.manager.fragments.EmployeeFragment;
import com.GMS.manager.fragments.FavoriteFragment;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new EmployeeFragment();

            case 1:
                return new FavoriteFragment();
        }
        return new EmployeeFragment();
    }

    @Override
    public int getItemCount()
    {
        return 2;
    }
}
