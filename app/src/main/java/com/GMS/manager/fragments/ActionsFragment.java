package com.GMS.manager.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.MapsActivity;
import com.GMS.R;
import com.GMS.SettingActivity;
import com.GMS.databinding.FragmentActionsBinding;
import com.GMS.login.activities.LoginActivity;
import com.GMS.manager.adapters.ActionsAdapter;
import com.GMS.manager.helperClasses.ItemClickListener;
import com.GMS.manager.models.Actions;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ActionsFragment extends Fragment {
    FragmentActionsBinding mActionsBinding;
    ArrayList<Actions> mActionsList;
    ActionsAdapter adapter;
    FirebaseAuth mAuth;
    NavController mNavController;
    ItemClickListener mItemClickListener;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        mActionsBinding = FragmentActionsBinding.inflate(inflater, container, false);
        mActionsList = new ArrayList<>();
        fillActions();
        adapter = new ActionsAdapter(mActionsList, mItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mActionsBinding.actionsRecyclerView.setAdapter(adapter);
        mActionsBinding.actionsRecyclerView.setLayoutManager(layoutManager);
        mActionsBinding.actionsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        return mActionsBinding.getRoot();
    }

    private void fillActions() {
        Actions action = new Actions();

        action.setActionState(false);
        action.setAgentName("Omar Swaid");
        action.setRepresentativeName("Mohammed shehab");
        action.setAqelName("Ali ahmed");
        action.setNeighborhoodName("Almadena");
        action.setDate("2022/7/11");
        mActionsList.add(action);

        action = new Actions();
        action.setActionState(true);
        action.setAgentName("Salah Doos");
        action.setAqelName("Ali ahmed");
        action.setRepresentativeName("Mohammed shehab");
        action.setNeighborhoodName("Mosa ST");
        action.setDate("Today");
        mActionsList.add(action);

        action = new Actions();
        action.setActionState(false);
        action.setAqelName("Ali ahmed");
        action.setAgentName("Abdulrahman khaled");
        action.setRepresentativeName("Naseem Ahmed");
        action.setNeighborhoodName("Palastine ST");
        action.setDate("2022/7/11");
        mActionsList.add(action);

        action = new Actions();
        action.setAqelName("Ali ahmed");
        action.setActionState(true);
        action.setAgentName("Ammar sharqy");
        action.setRepresentativeName("Haitham Taresh");
        action.setNeighborhoodName("Aldamgha");
        action.setDate("Today");
        mActionsList.add(action);

        action = new Actions();
        action.setAqelName("Ali ahmed");
        action.setActionState(true);
        action.setAgentName("Omar Swaid");
        action.setRepresentativeName("Mohammed shehab");
        action.setNeighborhoodName("Gamal ST");
        action.setDate("Today");
        mActionsList.add(action);

        action = new Actions();
        action.setAqelName("Ali ahmed");
        action.setActionState(false);
        action.setAgentName("Omar Swaid");
        action.setRepresentativeName("Mohammed shehab");
        action.setNeighborhoodName("Alhamdy");
        action.setDate("2022/7/11");
        mActionsList.add(action);

        mItemClickListener = position -> {
            @NonNull NavDirections navigationAction = ActionsFragmentDirections.actionActionsFragmentToActionDetailsFragment(mActionsList.get(position));
            mNavController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
            mNavController.navigate(navigationAction);
        };
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_manager_top_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_manager_item_search);
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

            case R.id.menu_manager_item_map:
                startActivity(new Intent(getActivity(), MapsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActionsBinding = null;
    }
}