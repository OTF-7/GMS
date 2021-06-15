package com.GMS.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.databinding.FragmentActionsMgrBinding;
import com.GMS.manager.adapters.RecyclerViewAdapterActions;
import com.GMS.manager.helperClasses.ActionItem;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment {
    FragmentActionsMgrBinding mBinding;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentActionsMgrBinding.inflate(inflater, container, false);

        ArrayList<com.GMS.manager.helperClasses.ActionItem> items = new ArrayList<>();
        items.add(new ActionItem("Abdurahman", "Omar Taha", "Mohmmed shihab", 200, 3800, 150, "Alkornish"));
        items.add(new ActionItem("Abdurahman", "Omar Taha", "Mohmmed shihab", 200, 3800, 150, "Alkornish"));
        items.add(new ActionItem("Abdurahman", "Omar Taha", "Mohmmed shihab", 200, 3800, 150, "Alkornish"));
        items.add(new ActionItem("Abdurahman", "Omar Taha", "Mohmmed shihab", 200, 3800, 150, "Alkornish"));
        items.add(new ActionItem("Abdurahman", "Omar Taha", "Mohmmed shihab", 200, 3800, 150, "Alkornish"));
        items.add(new ActionItem("Abdurahman", "Omar Taha", "Mohmmed shihab", 200, 3800, 150, "Alkornish"));
        items.add(new ActionItem("Abdurahman", "Omar Taha", "Mohmmed shihab", 200, 3800, 150, "Alkornish"));

        RecyclerViewAdapterActions adapter = new RecyclerViewAdapterActions(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvActions.setHasFixedSize(true);
        mBinding.rvActions.setLayoutManager(layoutManager);
        mBinding.rvActions.setAdapter(adapter);
        return mBinding.getRoot();
    }
}