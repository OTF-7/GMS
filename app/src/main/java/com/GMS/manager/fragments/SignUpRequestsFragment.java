package com.GMS.manager.fragments;

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

import com.GMS.R;
import com.GMS.databinding.FragmentSignUpRequestsBinding;

import org.jetbrains.annotations.NotNull;


public class SignUpRequestsFragment extends Fragment {
    private FragmentSignUpRequestsBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentSignUpRequestsBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_manager_top_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_manager_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                employeesAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                employeesAdapter.getFilter().filter(newText);
                return false;
            }

        });*/
        super.onCreateOptionsMenu(menu, inflater);
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