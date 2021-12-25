package com.GMS.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.GMS.R;
import com.GMS.databinding.FragmentActionDetailsBinding;
import com.GMS.manager.models.Actions;

public class ActionDetailsFragment extends Fragment {

    FragmentActionDetailsBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentActionDetailsBinding.inflate(inflater, container, false);
        Actions action = ActionDetailsFragmentArgs.fromBundle(getArguments()).getActionData();
        mBinding.managerActionDetailsAgentName.setText(action.getAgentName());
        mBinding.managerActionDetailsRepresentativeName.setText(action.getRepresentativeName());
        mBinding.managerActionDetailsNeighborhoodName.setText(action.getNeighborhoodName());
        mBinding.managerActionDetailsDate.setText(action.getDate());
        mBinding.managerActionDetailsAqelName.setText(action.getAqelName());
        if (action.getDate().equals("Today")) {
            mBinding.managerActionDetailsActionState.setText("The action is still proceessing");
            mBinding.managerActionDetailsActionState.setBackgroundColor(getResources().getColor(R.color.English_vermillion));
        } else {
            mBinding.managerActionDetailsActionState.setText("The action has completed");
            mBinding.managerActionDetailsActionState.setBackgroundColor(getResources().getColor(R.color.green));
        }
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