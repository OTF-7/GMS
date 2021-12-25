package com.GMS.manager.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.GMS.R;
import com.GMS.databinding.ActivityManagerBinding;
import com.GMS.manager.fragments.ActionsFragmentDirections;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManagerActivity extends AppCompatActivity {
    ActivityManagerBinding mManagerBinding;
    NavController mNavController;
    AppBarConfiguration appBarConfiguration;
    long currentFragmentId = R.id.actions_fragment;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.md_theme_light_primary));
        mManagerBinding = ActivityManagerBinding.inflate(getLayoutInflater());
        setContentView(mManagerBinding.getRoot());
        setSupportActionBar(mManagerBinding.actionToolbar);
        mManagerBinding.actionToolbar.setTitle("Actions");
        appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.employee_fragment, R.id.actions_fragment, R.id.station_fragment)
                        .setDrawerLayout(mManagerBinding.managerDrawerLayout)
                        .build();

        mNavController = Navigation.findNavController(this, R.id.fragmentContainerView);

        NavigationUI.setupWithNavController(mManagerBinding.managerBottomNavigationView, mNavController, false);
        NavigationUI.setupWithNavController(mManagerBinding.actionToolbar, mNavController, appBarConfiguration);
        NavigationUI.setupWithNavController(mManagerBinding.managerNavigationView, mNavController, false);

        mNavController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.actions_fragment ||
                    navDestination.getId() == R.id.employee_fragment ||
                    navDestination.getId() == R.id.station_fragment)
                currentFragmentId = navDestination.getId();
        });
        mManagerBinding.managerBottomNavigationView.setOnItemSelectedListener(item -> {
            mManagerBinding.managerNavigationView.setCheckedItem(item);
            mNavController.navigateUp();
            mNavController.navigate(item.getItemId());
            if (item.getItemId() == R.id.employee_fragment)
                mManagerBinding.managerAddFloatingActionButton.setImageResource(R.drawable.ic_baseline_message_24);
            else
                mManagerBinding.managerAddFloatingActionButton.setImageResource(R.drawable.ic_add);
            return true;
        });
        mManagerBinding.managerAddFloatingActionButton.setOnClickListener(v -> {
            if (currentFragmentId == R.id.actions_fragment) {
                @NonNull NavDirections navigationAction = ActionsFragmentDirections.actionActionsFragmentToCreateActionFragment();
                mNavController.navigate(navigationAction);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, mNavController)
                || super.onOptionsItemSelected(item);
    }
}