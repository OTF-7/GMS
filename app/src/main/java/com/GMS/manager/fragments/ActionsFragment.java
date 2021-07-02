package com.GMS.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.GMS.databinding.FragmentActionsBinding;

public class ActionsFragment extends Fragment {
    FragmentActionsBinding mActionsBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mActionsBinding = FragmentActionsBinding.inflate(inflater, container, false);

        return mActionsBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActionsBinding = null;
    }
}