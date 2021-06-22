package com.GMS.representative.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.GMS.R;
import com.GMS.databinding.ActivityRepresentativeBinding;
import com.GMS.representative.adapters.MainFragmentAdapter;

public class RepresentativeActivity extends AppCompatActivity {

    ActivityRepresentativeBinding mbindig ;
    MainFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbindig = ActivityRepresentativeBinding.inflate(getLayoutInflater());
        setContentView(mbindig.getRoot());
        mbindig.cardViewHeaderContainer.setBackgroundColor(Color.TRANSPARENT);
        mbindig.cardViewHeaderContainer.setAlpha(0);
        mbindig.cardViewHeaderContainer.setTranslationY(-400);
        mbindig.moreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mbindig.moreTextView.getText().equals("hide")) {
                    ViewGroup.LayoutParams params = mbindig.cardViewHeaderContainer.getLayoutParams();
                    params.height = mbindig.textTitle.getHeight() + 32;
                    mbindig.cardViewHeaderContainer.setLayoutParams(params);
                    mbindig.moreTextView.setText("more");
                }
                else
                {
                    ViewGroup.LayoutParams params = mbindig.cardViewHeaderContainer.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    mbindig.cardViewHeaderContainer.setLayoutParams(params);
                    mbindig.moreTextView.setText("hide");
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mbindig.cardViewHeaderContainer.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(200);

    }
}