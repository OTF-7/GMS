package com.GMS.agent.activities;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.agent.adapters.ItemClickListener;
import com.GMS.agent.adapters.RecyclerViewAdapterCitizen;
import com.GMS.agent.helperClasses.CitizenItem;
import com.GMS.databinding.ActivityAgentBinding;

import java.util.ArrayList;

public class AgentActivity extends AppCompatActivity {

    //MainAdapter adapter;
    public final static int FULL_LIST_ID = 1;
    public final static int ACCEPTED_LIST_ID = 2;
    private final int size = -1;
    private final ArrayList<CitizenItem> fullCitizenList = new ArrayList<>();
    private final ArrayList<CitizenItem> acceptedCitizenList = new ArrayList<>();
    private final int countOfAcceptedCitizens = 0;
    Color doneColor;
    private ActivityAgentBinding mBinding;
    private RecyclerViewAdapterCitizen fullRVAdapter;
    private RecyclerViewAdapterCitizen acceptedRVAdapter;
    private ItemClickListener mItemClickListener;
    public   static Drawable mAcceptedBackgroundImage ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAgentBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mAcceptedBackgroundImage=getDrawable(R.drawable.accpted_image_background_shape);
        // change color of status bar color
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        setSupportActionBar(mBinding.agentTopBar.toolBarAgent);
        setTitle("Agent");
        mBinding.cardViewHeaderContainer.setBackgroundColor(Color.TRANSPARENT);

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


        fullCitizenList.add(new CitizenItem("Abdulrahman Khalid", "45d55d45s55g", 3, 0, 3700.0, false));
        fullCitizenList.add(new CitizenItem("Abubaker Khalid", "45d55d45s55g", 4, 0, 3700.0, false));
        fullCitizenList.add(new CitizenItem("Omar Taha", "45d55d45s55g", 1, 0, 3700.0, false));
        fullCitizenList.add(new CitizenItem("Mohammed Shihab", "45d55d45s55g", 2, 0, 3700.0, false));
        fullCitizenList.add(new CitizenItem("Omar Swaid", "45d55d45s55g", 5, 0, 3700.0, false));
        fullCitizenList.add(new CitizenItem("hasan Someeri", "45d55d45s55g", 3, 0, 3700.0, false));
        mItemClickListener = new ItemClickListener() {
            @Override
            public void onClick(int position, boolean state) {
                if (state) {
                    isCylinderAccepted(position);
                } else {
                    isCylinderDenied(position);
                }
            }
        };
        intiFullRVList();
        intiAcceptedRVList();


    }

    private void intiFullRVList() {
        if (fullCitizenList.size() != 0) {
            mBinding.rvCitizen.setVisibility(View.VISIBLE);
            fullRVAdapter = new RecyclerViewAdapterCitizen(fullCitizenList, getBaseContext(), mItemClickListener, FULL_LIST_ID);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
            mBinding.rvCitizen.setHasFixedSize(true);
            mBinding.rvCitizen.setLayoutManager(layoutManager);
            mBinding.rvCitizen.setAdapter(fullRVAdapter);
        } else {
            mBinding.rvCitizen.setVisibility(View.GONE);
        }
    }

    private void intiAcceptedRVList() {
        if (acceptedCitizenList.size() != 0) {
            mBinding.rvAcceptedCitizen.setVisibility(View.VISIBLE);
            mBinding.tvAcceptedCitizen.setVisibility(View.VISIBLE);
            acceptedRVAdapter = new RecyclerViewAdapterCitizen(acceptedCitizenList, getBaseContext(), mItemClickListener, ACCEPTED_LIST_ID);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
            mBinding.rvAcceptedCitizen.setHasFixedSize(true);
            mBinding.rvAcceptedCitizen.setLayoutManager(layoutManager);
            mBinding.rvAcceptedCitizen.setAdapter(acceptedRVAdapter);
        } else {
            mBinding.rvAcceptedCitizen.setVisibility(View.GONE);
            mBinding.tvAcceptedCitizen.setVisibility(View.GONE);
        }
    }

    private void isCylinderAccepted(int position) {
        CitizenItem item = fullCitizenList.get(position);
        acceptedCitizenList.add(item);
        fullCitizenList.remove(position);
        mBinding.rvCitizen.setAdapter(null);
        intiFullRVList();
        intiAcceptedRVList();
        Toast.makeText(getBaseContext(), String.valueOf(countOfAcceptedCitizens), Toast.LENGTH_SHORT).show();
    }

    private void isCylinderDenied(int position) {
        CitizenItem item = acceptedCitizenList.get(position);
        fullCitizenList.add(item);
        acceptedCitizenList.remove(position);
        mBinding.rvAcceptedCitizen.setAdapter(null);
        intiFullRVList();
        intiAcceptedRVList();
        Toast.makeText(getBaseContext(), String.valueOf(countOfAcceptedCitizens), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding=null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_agent_top_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.search_ic);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fullRVAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fullRVAdapter.getFilter().filter(newText);
                acceptedRVAdapter.getFilter().filter(newText);
                return false;
            }

        });


        return true;
    }

}