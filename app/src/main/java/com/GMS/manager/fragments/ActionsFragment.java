package com.GMS.manager.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.SettingActivity;
import com.GMS.aqel.activities.AddCitizenActivity;
import com.GMS.databinding.FragmentActionsBinding;
import com.GMS.manager.adapters.ActionsAdapter;
import com.GMS.manager.models.Actions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ActionsFragment extends Fragment {
    FragmentActionsBinding mActionsBinding;
    ArrayList<Actions> mActionsList;
    ActionsAdapter adapter;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mActionsBinding = FragmentActionsBinding.inflate(inflater, container, false);
        mActionsList = new ArrayList<>();
        fillActions();
        adapter = new ActionsAdapter(mActionsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mActionsBinding.actionsRecyclerView.setAdapter(adapter);
        mActionsBinding.actionsRecyclerView.setLayoutManager(layoutManager);
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
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_action_bar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.ic_action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }

        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification_addition:
                Intent mAddCitizenIntent = new Intent(this.getActivity(), AddCitizenActivity.class);
                startActivity(mAddCitizenIntent);
                break;
            case R.id.setting_item:
                Intent mSettingIntent = new Intent(this.getActivity(), SettingActivity.class);
                startActivity(mSettingIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActionsBinding = null;
    }
}