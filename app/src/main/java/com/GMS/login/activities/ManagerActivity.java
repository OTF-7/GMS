package com.GMS.login.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.GMS.R;
import com.GMS.login.adapters.FragmentAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class ManagerActivity extends AppCompatActivity   {

   BottomAppBar bottomAppBar ;
   BottomNavigationView bottomNavigationView ;
   long animationDuration = 500 ;
   Toolbar mToolBar ;
    ViewPager2 viewPager2;
    FragmentAdapter fragmentAdapter;
     static int height , width ;

     DrawerLayout drawerLayout ;
     ActionBarDrawerToggle toggle ;
      NavigationView navigationView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        final FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "fab", Toast.LENGTH_SHORT).show();


            }
        });
          // inflate and set action bar
        mToolBar = findViewById(R.id.tool_bar_id);
        setSupportActionBar(mToolBar);
        /*
        // inflate the elements of navigation view and set them
         drawerLayout = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.navigation_view);
         toggle = new ActionBarDrawerToggle(this , drawerLayout ,mToolBar , R.string.open , R.string.close);
         drawerLayout.addDrawerListener(toggle);
         toggle.setDrawerIndicatorEnabled(true);
         toggle.syncState();
       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

               switch (item.getItemId()) {
                   case R.id.account:
                   Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                     break;
                   case R.id.emp_reports:
                       Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                       break;
                   case R.id.action_reports:
                       Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                       break;
                   case R.id.setting:
               }

               return true;
           }
       });
    */


        // to take the matrix of height and width
        DisplayMetrics matrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(matrics);
        height = matrics.heightPixels;
        width = matrics.widthPixels;
        // inflate viewPager element
        viewPager2 = findViewById(R.id.view_pager2);
        // set the fragment into adapter
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fragmentManager, getLifecycle());
        viewPager2.setAdapter(fragmentAdapter);

        // inflate and set the bottom navigation view

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(1).setEnabled(false);
        bottomNavigationView.getMenu().getItem(1).setTitle("");
        bottomNavigationView.getMenu().getItem(1).setIcon(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.emp) {
                    setTitle("Employess");
                    viewPager2.setCurrentItem(0);

                }
                if (item.getItemId() == R.id.space) {
                    setTitle("not yet");

                    viewPager2.setCurrentItem(1);

                }
                if (item.getItemId() == R.id.actions) {

                    setTitle("Actions");


                    viewPager2.setCurrentItem(1);

                }
                return false;
            }
        });



    }

    // to inflate the menu for action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.top_action_bar_menu , menu);
        return true;
    }

    // method for selected item on action bar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.search_id)
        {
            Toast.makeText(getBaseContext() , "search" ,Toast.LENGTH_SHORT ).show();
        }
        else
        {
            Toast.makeText(getBaseContext() ,  height+"" ,Toast.LENGTH_SHORT ).show();
        }
        return super.onOptionsItemSelected(item);
    }

}