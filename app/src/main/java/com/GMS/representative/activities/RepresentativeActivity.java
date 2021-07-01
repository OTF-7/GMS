package com.GMS.representative.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.GMS.R;
import com.GMS.databinding.ActivityRepresentativeBinding;
import com.GMS.representative.adapters.MainFragmentAdapter;
import com.GMS.representative.fragments.NeedScanFragment;
import com.GMS.representative.fragments.VerifiedFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RepresentativeActivity extends AppCompatActivity {

    ActivityRepresentativeBinding mbindig ;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbindig = ActivityRepresentativeBinding.inflate(getLayoutInflater());
        setContentView(mbindig.getRoot());

         setSupportActionBar(mbindig.repTopBar.toolBarRep);
         setTitle("Representative");
        mbindig.cardViewHeaderContainer.setBackgroundColor(Color.TRANSPARENT);
        mbindig.cardViewHeaderContainer.setAlpha(0);
        mbindig.cardViewHeaderContainer.setTranslationY(-400);
        final  int HEIGHT_HEADER_VIEW=mbindig.backgroundHeader.getHeight();
        adapter = new MainAdapter(getSupportFragmentManager());
        adapter.addFragment(new NeedScanFragment() , "need scan");
        adapter.addFragment(new VerifiedFragment() , "Verified");
        mbindig.viewPager2.setAdapter(adapter);
        mbindig.tabLayout.setupWithViewPager(mbindig.viewPager2);
        mbindig.moreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mbindig.moreTextView.getText().equals("hide")) {
                    ViewGroup.LayoutParams params = mbindig.cardViewHeaderContainer.getLayoutParams();

                    params.height =mbindig.textViews.getHeight()*2;
                    mbindig.cardViewHeaderContainer.setLayoutParams(params);
                    mbindig.moreTextView.setText("more");
                    // view
                    ViewGroup.LayoutParams pView = mbindig.backgroundHeader.getLayoutParams();
                    double height = mbindig.textViews.getHeight()*1;
                    pView.height = (int) height ;
                    mbindig.backgroundHeader.setLayoutParams(pView);
                }
                else
                {
                    ViewGroup.LayoutParams params = mbindig.cardViewHeaderContainer.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    mbindig.cardViewHeaderContainer.setLayoutParams(params);
                    mbindig.moreTextView.setText("hide");

                    ViewGroup.LayoutParams pView = mbindig.backgroundHeader.getLayoutParams();
                    double height =mbindig.repTopBar.toolBarRep.getHeight()*1.7;
                    pView.height = (int) height;
                    mbindig.backgroundHeader.setLayoutParams(pView);
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mbindig.cardViewHeaderContainer.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(200);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_representative_top_bar, menu);
        return true;
    }
}
 class  MainAdapter extends FragmentPagerAdapter
 {
    ArrayList<Fragment> fragmentsList = new ArrayList<>();
    ArrayList<String> stringList = new ArrayList<>();

    public void addFragment(Fragment frg , String s)
    {
        fragmentsList.add(frg);
        stringList.add(s);
    }

     public MainAdapter(@NonNull @NotNull FragmentManager fm) {
         super(fm);
     }

     @NonNull
     @NotNull
     @Override
     public Fragment getItem(int position) {
         return fragmentsList.get(position);
     }

     @Override
     public int getCount() {
         return fragmentsList.size();
     }

     @Nullable
     @org.jetbrains.annotations.Nullable
     @Override
     public CharSequence getPageTitle(int position) {
         return stringList.get(position);
     }
 }