package com.GMS.manager.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.GMS.R;
import com.GMS.databinding.ActivityManagerBinding;

public class ManagerActivity extends AppCompatActivity {
    ActivityManagerBinding mManagerBinding;

    NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManagerBinding = ActivityManagerBinding.inflate(getLayoutInflater());
        setContentView(mManagerBinding.getRoot());
        NavHostFragment mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (mNavHostFragment == null) {
            mNavController = mNavHostFragment.getNavController();
        }
        mNavController = Navigation.findNavController(this, R.id.fragmentContainerView);

        NavigationUI.setupWithNavController(mManagerBinding.managerBottomNavigationView, mNavController);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, mNavController)
                || super.onOptionsItemSelected(item);
    }
}