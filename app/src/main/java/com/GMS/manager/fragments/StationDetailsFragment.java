package com.GMS.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.GMS.R;
import com.GMS.databinding.FragmentStationDetailsBinding;
import com.GMS.manager.models.Stations;

public class StationDetailsFragment extends Fragment {
    FragmentStationDetailsBinding mBinding;
    int visibility = View.VISIBLE;
    int visibilityEdit = View.GONE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentStationDetailsBinding.inflate(inflater, container, false);
        Stations stationData = StationDetailsFragmentArgs.fromBundle(getArguments()).getStationData();
        mBinding.managerStationManagerName.setText(stationData.getStationManagerName());
        mBinding.managerStationDetailsName.setText(stationData.getStationName());
        mBinding.managerStationNeighborhoodName.setText(stationData.getStationNeighborhoodName());
        mBinding.managerStationManagerPhone.setText(stationData.getStationManagerPhone());


        mBinding.managerStationDetailsEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return mBinding.getRoot();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().findViewById(R.id.manager_add_floating_action_button)
                .animate()
                .alpha(0);

        requireActivity().findViewById(R.id.manager_bottomAppBar)
                .animate()
                .translationY(requireActivity().findViewById(R.id.manager_bottomAppBar).getHeight())
                .alpha(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().findViewById(R.id.manager_add_floating_action_button)
                .animate()
                .alpha(1);

        requireActivity().findViewById(R.id.manager_bottomAppBar)
                .animate()
                .translationY(0)
                .alpha(1);
    }

}