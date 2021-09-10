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
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        mManagerBinding = ActivityManagerBinding.inflate(getLayoutInflater());
        setContentView(mManagerBinding.getRoot());
        setSupportActionBar(mManagerBinding.actionToolbar);

        NavHostFragment mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (mNavHostFragment == null) {
            mNavController = mNavHostFragment.getNavController();
        }
        mNavController = Navigation.findNavController(this, R.id.fragmentContainerView);

        NavigationUI.setupWithNavController(mManagerBinding.managerBottomNavigationView, mNavController);
        mManagerBinding.managerBottomNavigationView.setOnItemSelectedListener(item -> {
            mManagerBinding.actionToolbar.setTitle(item.getTitle());
            mNavController.navigateUp();
            mNavController.navigate(item.getItemId());
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, mNavController)
                || super.onOptionsItemSelected(item);
    }
}