package com.GMS.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.databinding.FragmentStationsBinding;
import com.GMS.manager.adapters.StationsAdapter;
import com.GMS.manager.models.Stations;

import java.util.ArrayList;
import java.util.List;

public class StationsFragment extends Fragment {
    FragmentStationsBinding mBinding;
    StationsAdapter mStationsAdapter;
    List<Stations> mStationsList;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = FragmentStationsBinding.inflate(getLayoutInflater());
        mStationsList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        fillStations();
        mStationsAdapter = new StationsAdapter(mStationsList);
        mBinding.stationsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        mBinding.stationsRecyclerView.setAdapter(mStationsAdapter);
        mBinding.stationsRecyclerView.setLayoutManager(layoutManager);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return mBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }
}