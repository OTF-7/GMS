package com.GMS.manager.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.SettingActivity;
import com.GMS.databinding.FragmentStationsBinding;
import com.GMS.login.activities.LoginActivity;
import com.GMS.manager.adapters.StationsAdapter;
import com.GMS.manager.helperClasses.ItemClickListener;
import com.GMS.manager.models.Stations;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StationsFragment extends Fragment {
    FragmentStationsBinding mBinding;
    StationsAdapter mStationsAdapter;
    List<Stations> mStationsList;
    NavController mNavController;
    private FirebaseAuth mAuth;
    StationsFragment thisFragment = this;
    private ItemClickListener mItemClickListener;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void fillStations() {
        mItemClickListener = position -> {
            onClick(position);
        };
        Stations station = new Stations();

        station.setStationName("Aleesi");
        station.setStationImage(R.drawable.station_1);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Shamsan");
        station.setStationImage(R.drawable.station_2);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Alrajehy");
        station.setStationImage(R.drawable.ic_petrol_pump);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Alnoor");
        station.setStationImage(R.drawable.station_3);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Alhuda");
        station.setStationImage(R.drawable.station_4);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Altayseer");
        station.setStationImage(R.drawable.ic_petrol_pump);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Hodaidah Center");
        station.setStationImage(R.drawable.station_1);
        mStationsList.add(station);

        station = new Stations();
        station.setStationName("Aleesi");
        station.setStationImage(R.drawable.ic_petrol_pump);
        mStationsList.add(station);

    }

    private void onClick(int position) {
        switch (getId()) {
            case R.id.manager_station_details_call_button:
                Intent call_intent = new Intent(Intent.ACTION_CALL);
                call_intent.setData(Uri.parse("tel:777118407"));
                if (ActivityCompat
                        .checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED)
                    requestPermission();
                else
                    requireContext().startActivity(call_intent);
                break;
            default:
                @NonNull NavDirections navigationAction = StationsFragmentDirections.actionStationFragmentToStationDetailsFragment(mStationsList.get(position), mStationsList.get(position).getStationName());
                mNavController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                mNavController.navigate(navigationAction);
                break;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        mBinding = FragmentStationsBinding.inflate(inflater, container, false);
        mStationsList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        fillStations();
        mStationsAdapter = new StationsAdapter(mStationsList, requireContext(), mItemClickListener);
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

    private void requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.CALL_PHONE}, 1);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_manager_top_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_manager_item_search);
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
            case R.id.menu_manager_item_notification:

                break;
            case R.id.menu_manager_item_setting:
                startActivity(new Intent(requireContext(), SettingActivity.class));
                break;

            case R.id.menu_manager_item_log_out:
                mAuth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                requireActivity().finish();
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