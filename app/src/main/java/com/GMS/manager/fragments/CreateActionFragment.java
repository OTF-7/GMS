package com.GMS.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.GMS.R;
import com.GMS.databinding.FragmentCreateActionBinding;
import com.GMS.firebaseFireStore.CollectionName;
import com.GMS.firebaseFireStore.StationCollection;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CreateActionFragment extends Fragment {
    FragmentCreateActionBinding mBinding;

    List<String> neighborhoods, aqels, agents, reps;
    NavController mNavController;
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    CollectionReference stationCollectionRef = mFirestore.collection(CollectionName.STATIONS.name());
    CollectionReference neighborhoodCollection = mFirestore.collection(CollectionName.NEIGHBORHOODS.name());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentCreateActionBinding.inflate(inflater, container, false);
        addStation();
       neighborhoodCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    neighborhoods = new ArrayList<>();
                    for (DocumentSnapshot documnet : queryDocumentSnapshots) {
                        neighborhoods.add(documnet.getString(CollectionName.Fields.name.name()));
                    }
                }
            }
        });

        CollectionReference employeesCollection = mFirestore.collection(CollectionName.Employees.name());
        employeesCollection.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    aqels = new ArrayList<>();
                    agents = new ArrayList<>();
                    reps = new ArrayList<>();
                    for (DocumentSnapshot documnet : queryDocumentSnapshots) {
                        if (Objects.equals(documnet.getLong(CollectionName.Fields.userType.name()), Long.valueOf(1)))
                            reps.add(documnet.getString("firstName") + " " + documnet.getString("lastName"));
                        else if (Objects.equals(documnet.getLong(CollectionName.Fields.userType.name()), Long.valueOf(2)))
                            agents.add(documnet.getString("firstName") + " " + documnet.getString("lastName"));
                        else
                            aqels.add(documnet.getString("firstName") + " " + documnet.getString("lastName"));
                    }
                    fillData();
                }
            }
        });

        /*
        ArrayAdapter<String> neighborhoodsAdapter = new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, neighborhoods);

        ArrayAdapter mAdapter = new ArrayAdapter(mBinding.getRoot().getContext(), R.layout.spinner_item, reps);
        ArrayAdapter<String> aqelsAdapter = new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, aqels);

        ArrayAdapter<String> agentsAdapter = new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, agents);

        ArrayAdapter<String> repsAdapter = new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, reps);

        mBinding.managerCreateActionNeighborhoodEditText.setAdapter(neighborhoodsAdapter);
        mBinding.managerCreateActionAqelEditText.setAdapter(aqelsAdapter);
        mBinding.managerCreateActionAgentEditText.setAdapter(agentsAdapter);
        mBinding.managerCreateActionRepEditText.setAdapter(mAdapter);


         */

        mBinding.managerCreateActionCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
                mNavController.navigateUp();
            }
        });

        return mBinding.getRoot();
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

    private void fillData()
    {
        ArrayAdapter<String> neighborhoodsAdapter = new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, neighborhoods);

       ArrayAdapter<String> aqelsAdapter = new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, aqels);

        ArrayAdapter<String> agentsAdapter = new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, agents);

        ArrayAdapter<String> repsAdapter = new ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, reps);

        mBinding.managerCreateActionNeighborhoodEditText.setAdapter(neighborhoodsAdapter);
        mBinding.managerCreateActionAqelEditText.setAdapter(aqelsAdapter);
        mBinding.managerCreateActionAgentEditText.setAdapter(agentsAdapter);
        mBinding.managerCreateActionRepEditText.setAdapter(repsAdapter);


    }
    private void addStation()
    {
        List<String> tel = new ArrayList<>();
        tel.add("+9677755110000");
        tel.add("+9670000000000");
        HashMap<String , Object> location = new HashMap<>();
        location.put(CollectionName.Fields.address.name(), "zaid street");
        location.put(CollectionName.Fields.longitude.name(), "0");
        location.put(CollectionName.Fields.latitude.name(),"0" );
        StationCollection stationCollection = new StationCollection("Alslam"  ,"mohammed ahmed",tel ,location );
        stationCollectionRef.add(stationCollection).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(@NonNull DocumentReference documentReference) {
                Toast.makeText(mBinding.getRoot().getContext(), "station have added successfully", Toast.LENGTH_SHORT).show();
            }
        });

    }
}