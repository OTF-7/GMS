package com.GMS.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.databinding.FragmentActionsBinding;
import com.GMS.manager.adapters.ActionsAdapter;
import com.GMS.manager.models.Actions;

import java.util.ArrayList;
import java.util.List;

public class ActionsFragment extends Fragment {
    FragmentActionsBinding mActionsBinding;
    List<Actions> mActionsList;
    ActionsAdapter adapter;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActionsBinding = FragmentActionsBinding.inflate(getLayoutInflater());
        mActionsList = new ArrayList<>();
        fillActions();
        adapter = new ActionsAdapter(mActionsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mActionsBinding.actionsRecyclerView.setAdapter(adapter);
        mActionsBinding.actionsRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return mActionsBinding.getRoot();
    }

    private void fillActions() {
        Actions action = new Actions();

        action.setActionState(false);
        action.setAgentName("Omar Swaid");
        action.setRepresentativeName("Mohammed shehab");
        action.setNeighborhoodName("Almadena");
        action.setDate("2022/7/11");
        mActionsList.add(action);

        action = new Actions();
        action.setActionState(true);
        action.setAgentName("Salah Doos");
        action.setRepresentativeName("Mohammed shehab");
        action.setNeighborhoodName("Mosa ST");
        action.setDate("Today");
        mActionsList.add(action);

        action = new Actions();
        action.setActionState(false);
        action.setAgentName("Abdulrahman khaled");
        action.setRepresentativeName("Naseem Ahmed");
        action.setNeighborhoodName("Palastine ST");
        action.setDate("2022/7/11");
        mActionsList.add(action);

        action = new Actions();
        action.setActionState(true);
        action.setAgentName("Ammar sharqy");
        action.setRepresentativeName("Haitham Taresh");
        action.setNeighborhoodName("Aldamgha");
        action.setDate("Today");
        mActionsList.add(action);

        action = new Actions();
        action.setActionState(true);
        action.setAgentName("Omar Swaid");
        action.setRepresentativeName("Mohammed shehab");
        action.setNeighborhoodName("Gamal ST");
        action.setDate("Today");
        mActionsList.add(action);

        action = new Actions();
        action.setActionState(false);
        action.setAgentName("Omar Swaid");
        action.setRepresentativeName("Mohammed shehab");
        action.setNeighborhoodName("Alhamdy");
        action.setDate("2022/7/11");
        mActionsList.add(action);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActionsBinding = null;
    }
}