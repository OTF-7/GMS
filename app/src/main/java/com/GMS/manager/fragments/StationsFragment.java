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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.SettingActivity;
import com.GMS.aqel.activities.AddCitizenActivity;
import com.GMS.databinding.FragmentStationsBinding;
import com.GMS.manager.adapters.StationsAdapter;
import com.GMS.manager.models.Stations;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StationsFragment extends Fragment {
    FragmentStationsBinding mBinding;
    StationsAdapter mStationsAdapter;
    List<Stations> mStationsList;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void fillStations() {
        Stations station = new Stations();

        station.setStationName("Aleesi");
        station.setStationIcon(R.drawable.station_1);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Shamsan");
        station.setStationIcon(R.drawable.station_2);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Alrajehy");
        station.setStationIcon(R.drawable.ic_petrol_pump);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Alnoor");
        station.setStationIcon(R.drawable.station_3);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Alhuda");
        station.setStationIcon(R.drawable.station_4);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Altayseer");
        station.setStationIcon(R.drawable.ic_petrol_pump);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Hodaidah Center");
        station.setStationIcon(R.drawable.station_1);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Aleesi");
        station.setStationIcon(R.drawable.ic_petrol_pump);
        mStationsList.add(station);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentStationsBinding.inflate(inflater, container, false);
        mStationsList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        fillStations();
        mStationsAdapter = new StationsAdapter(mStationsList);
        mBinding.stationsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mBinding.stationsRecyclerView.setAdapter(mStationsAdapter);
        mBinding.stationsRecyclerView.setLayoutManager(layoutManager);
        mBinding.stationsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    requireActivity().findViewById(R.id.manager_add_floating_action_button)
                            .animate()
                            .alpha(0);

                    requireActivity().findViewById(R.id.manager_bottomAppBar)
                            .animate()
                            .translationY(requireActivity().findViewById(R.id.manager_bottomAppBar).getHeight())
                            .alpha(0);
                } else {
                    requireActivity().findViewById(R.id.manager_add_floating_action_button)
                            .animate()
                            .alpha(1);

                    requireActivity().findViewById(R.id.manager_bottomAppBar)
                            .animate()
                            .translationY(0)
                            .alpha(1);
                }
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_action_bar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.ic_action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mStationsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mStationsAdapter.getFilter().filter(newText);
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
        mBinding = null;
    }
}