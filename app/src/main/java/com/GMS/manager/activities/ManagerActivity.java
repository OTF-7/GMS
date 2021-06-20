package com.GMS.manager.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;

import com.GMS.R;
import com.GMS.databinding.ActivityManagerBinding;
import com.GMS.login.adapters.FragmentAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class ManagerActivity extends AppCompatActivity {

    // variable for height and width of screen
    static int height, width;
    // for using dataBinding instead of inflate each view separately
    ActivityManagerBinding mBinding;
    Toolbar mToolBar;
    FragmentAdapter fragmentAdapter;
    // an array to get items from Resource
    String[] itemsSpinner;
    ArrayAdapter adapter;
    ActionBarDrawerToggle toggle;
    // dialog of add action to the date base
    Dialog actionDialog;
    // to get the date of date picker
    String dateFrom, dateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityManagerBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        final FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.viewPager2.getCurrentItem() == 0) {
                    Toast.makeText(getBaseContext(), "Employees", Toast.LENGTH_SHORT).show();
                } else {
                    showActionDialogForAdd();
                }
            }

        });
        // inflate and set action bar
        mToolBar = findViewById(R.id.tool_bar_id);
        setSupportActionBar(mToolBar);
        // inflate the elements of navigation view and set them

        toggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout, mToolBar, R.string.Open, R.string.Close);
        mBinding.drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        mBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.account:
                        Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.emp_reports:

                        MaterialDatePicker.Builder builderFrom = MaterialDatePicker.Builder.datePicker();
                        builderFrom.setTitleText("From this date");
                        MaterialDatePicker materialTimePickerFrom = builderFrom.build();
                        materialTimePickerFrom.show(getSupportFragmentManager(), "DATE_PICKER");
                        materialTimePickerFrom.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick(Object selection) {
                                dateFrom = materialTimePickerFrom.getHeaderText();
                                MaterialDatePicker.Builder builderTo = MaterialDatePicker.Builder.datePicker();
                                builderTo.setTitleText("To this date");
                                MaterialDatePicker materialTimePickerTo = builderTo.build();
                                materialTimePickerTo.show(getSupportFragmentManager(), "DATE_PICKER");
                                materialTimePickerTo.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                                    @Override
                                    public void onPositiveButtonClick(Object selection) {
                                        dateTo = materialTimePickerTo.getHeaderText();
                                        Toast.makeText(getBaseContext(), dateFrom + "\n" + dateTo, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });


                        break;
                    case R.id.action_reports:
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.setting:
                        Intent settingintent = new Intent(mBinding.getRoot().getContext(), SettingActivity.class);
                        startActivity(settingintent);
                        break;
                }

                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        // to take the matrix of height and width
        DisplayMetrics matrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(matrics);
        height = matrics.heightPixels;
        width = matrics.widthPixels;
        // set the fragment into adapter
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fragmentManager, getLifecycle());
        mBinding.viewPager2.setAdapter(fragmentAdapter);

        // inflate and set the bottom navigation view
        mBinding.bottomNavigationView.setBackground(null);
        mBinding.bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        mBinding.bottomNavigationView.getMenu().getItem(1).setTitle("");
        mBinding.bottomNavigationView.getMenu().getItem(1).setIcon(null);
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.emp) {
                    setTitle("Employess");
                    mBinding.viewPager2.setCurrentItem(0);

                }
                if (item.getItemId() == R.id.space) {
                    setTitle("not yet");

                    mBinding.viewPager2.setCurrentItem(1);

                }
                if (item.getItemId() == R.id.actions) {

                    setTitle("Actions");


                    mBinding.viewPager2.setCurrentItem(1);

                }
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    // to inflate the menu for action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_action_bar_menu, menu);
        return true;
    }

    // method for selected item on action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.search_id) {
            Toast.makeText(getBaseContext(), "search", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), height + "", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showActionDialogForAdd() {
        itemsSpinner = getResources().getStringArray(R.array.items_array);
        adapter = new ArrayAdapter(getBaseContext(), R.layout.spinner_item, itemsSpinner);

        Toast.makeText(getBaseContext(), "Actions", Toast.LENGTH_SHORT).show();

        actionDialog = new Dialog(mBinding.getRoot().getContext());
        actionDialog.setContentView(R.layout.add_dialoge);
        AutoCompleteTextView txt = actionDialog.findViewById(R.id.neighborhood_name_auto_complete);
        Button btn = actionDialog.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "will done", Toast.LENGTH_LONG).show();
            }
        });
        txt.setAdapter(adapter);
        ImageView closeIcon = actionDialog.findViewById(R.id.close_icon);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionDialog.cancel();
            }
        });
        actionDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        Window window = actionDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        actionDialog.setCancelable(false);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        actionDialog.show();
    }
}