package com.GMS.agent.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.databinding.ActivityAgentBinding;
import com.GMS.agent.adapters.RecyclerViewAdapterCitizen;
import com.GMS.agent.helperClasses.CitizenItem;

import java.util.ArrayList;

public class AgentActivity extends AppCompatActivity {

    ActivityAgentBinding mBinding;
    //MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAgentBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.agentTopBar.toolBarAgent);
        setTitle("Agent");
        mBinding.cardViewHeaderContainer.setBackgroundColor(Color.TRANSPARENT);
        mBinding.cardViewHeaderContainer.setAlpha(0);
        mBinding.cardViewHeaderContainer.setTranslationY(-400);
        final int HEIGHT_HEADER_VIEW = mBinding.backgroundHeader.getHeight();



        mBinding.moreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.moreTextView.getText().equals("hide")) {
                    ViewGroup.LayoutParams params = mBinding.cardViewHeaderContainer.getLayoutParams();

                    params.height = mBinding.textViews.getHeight() * 2;
                    mBinding.cardViewHeaderContainer.setLayoutParams(params);
                    mBinding.moreTextView.setText("more");
                    // view
                    ViewGroup.LayoutParams pView = mBinding.backgroundHeader.getLayoutParams();
                    double height = mBinding.textViews.getHeight() * 1;
                    pView.height = (int) height;
                    mBinding.backgroundHeader.setLayoutParams(pView);
                } else {
                    ViewGroup.LayoutParams params = mBinding.cardViewHeaderContainer.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    mBinding.cardViewHeaderContainer.setLayoutParams(params);
                    mBinding.moreTextView.setText("hide");

                    ViewGroup.LayoutParams pView = mBinding.backgroundHeader.getLayoutParams();
                    double height = mBinding.agentTopBar.toolBarAgent.getHeight() * 1.7;
                    pView.height = (int) height;
                    mBinding.backgroundHeader.setLayoutParams(pView);
                }

            }
        });


        ArrayList<CitizenItem> items = new ArrayList<>();
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));
        items.add(new CitizenItem("Abdulrahman Khalid" , "45d55d45s55g" , 3));

        RecyclerViewAdapterCitizen adapter = new RecyclerViewAdapterCitizen(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        mBinding.rvCitizen.setHasFixedSize(true);
        mBinding.rvCitizen.setLayoutManager(layoutManager);
        mBinding.rvCitizen.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mBinding.cardViewHeaderContainer.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(200);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_agent_top_bar, menu);
        return true;
    }
}