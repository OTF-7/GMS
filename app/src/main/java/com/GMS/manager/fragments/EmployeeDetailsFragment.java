package com.GMS.manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.GMS.R;
import com.GMS.databinding.FragmentEmployeeDetailsBinding;
import com.GMS.manager.models.Employees;

public class EmployeeDetailsFragment extends Fragment {

    FragmentEmployeeDetailsBinding mBinding;
    boolean editMode = false;
    int visibility = View.VISIBLE;
    int visibilityEdit = View.GONE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentEmployeeDetailsBinding.inflate(inflater, container, false);
        Employees employeeData = EmployeeDetailsFragmentArgs.fromBundle(getArguments()).getEmployeeData();
        mBinding.employeeDetailsAge.setText(employeeData.getEmployeeAge());
        mBinding.employeeDetailsDate.setText(employeeData.getEmployeeHiringDate());
        mBinding.employeeDetailsEmail.setText(employeeData.getEmployeeEmail());
        mBinding.employeeDetailsFullName.setText(employeeData.getEmployeeFirstName() + " " + employeeData.getEmployeeMiddleName() + " " + employeeData.getEmployeeLastName());
        mBinding.employeeDetailsNeighborhood.setText(employeeData.getEmployeeNeighborhood());
        mBinding.employeeDetailsUserName.setText(employeeData.getEmployeeUserName());
        mBinding.employeeDetailsPhone.setText(employeeData.getEmployeePhone());
        mBinding.employeeDetailsImage.setImageResource(employeeData.getEmployeeImage());
        mBinding.employeeDetailsTypeOfJop.setText(employeeData.getEmployeeJopType());

        mBinding.employeeDetailsEditButton.setOnClickListener(v -> {
            if (!editMode) {
                visibility = View.GONE;
                visibilityEdit = View.VISIBLE;
            } else {
                visibility = View.VISIBLE;
                visibilityEdit = View.GONE;
            }
            editMode = !editMode;
            mBinding.employeeDetailsAge.setVisibility(visibility);
            mBinding.employeeDetailsDate.setVisibility(visibility);
            mBinding.employeeDetailsEmail.setVisibility(visibility);
            mBinding.employeeDetailsFullName.setVisibility(visibility);
            mBinding.employeeDetailsNeighborhood.setVisibility(visibility);
            mBinding.employeeDetailsUserName.setVisibility(visibility);
            mBinding.employeeDetailsPhone.setVisibility(visibility);
            mBinding.employeeDetailsTypeOfJop.setVisibility(visibility);
            mBinding.employeeDetailsFireButton.setVisibility(visibility);
            mBinding.employeeDetailsChatButton.setVisibility(visibility);
            mBinding.employeeDetailsCallButton.setVisibility(visibility);

            mBinding.employeeDetailsAgeEditText.setVisibility(visibilityEdit);
            mBinding.employeeDetailsDateEditText.setVisibility(visibilityEdit);
            mBinding.employeeDetailsEmailEditText.setVisibility(visibilityEdit);
            mBinding.employeeDetailsFullNameEditText.setVisibility(visibilityEdit);
            mBinding.employeeDetailsNeighborhoodEditText.setVisibility(visibilityEdit);
            mBinding.employeeDetailsUserNameEditText.setVisibility(visibilityEdit);
            mBinding.employeeDetailsPhoneEditText.setVisibility(visibilityEdit);
            mBinding.employeeDetailsTypeOfJopEditText.setVisibility(visibilityEdit);
            mBinding.employeeDetailsChangeImageButton.setVisibility(visibilityEdit);
            mBinding.employeeDetailsCancelButton.setVisibility(visibilityEdit);

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
}