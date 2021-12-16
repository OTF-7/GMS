package com.GMS.manager.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.GMS.R;
import com.GMS.databinding.ActivityManagerBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class ManagerActivity extends AppCompatActivity {
    ActivityManagerBinding mManagerBinding;
    NavController mNavController;
    AppBarConfiguration mAppBarConfiguration;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        mManagerBinding = ActivityManagerBinding.inflate(getLayoutInflater());
        setContentView(mManagerBinding.getRoot());
        setSupportActionBar(mManagerBinding.actionToolbar);

//        mAppBarConfiguration = new AppBarConfiguration(Set.of(R.id.employee_fragment, R.id.actions_fragment, R.id.station_fragment), );
        NavHostFragment mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (mNavHostFragment == null) {
            mNavController = mNavHostFragment.getNavController();
        }
        mNavController = Navigation.findNavController(this, R.id.fragmentContainerView);

//        NavigationUI.setupActionBarWithNavController(this, mNavController);
        NavigationUI.setupWithNavController(mManagerBinding.managerBottomNavigationView, mNavController);
        mManagerBinding.managerBottomNavigationView.setOnItemSelectedListener(item -> {
            mManagerBinding.actionToolbar.setTitle(item.getTitle());
            mNavController.navigateUp();
            mNavController.navigate(item.getItemId());
            return true;
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuItem notification = menu.findItem(R.id.menu_manager_item_notification);
//        notification.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                return true;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, mNavController)
                || super.onOptionsItemSelected(item);
    }
}