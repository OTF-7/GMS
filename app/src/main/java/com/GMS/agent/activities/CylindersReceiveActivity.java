package com.GMS.agent.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.R;
import com.GMS.agent.adapters.ItemClickListener;
import com.GMS.agent.adapters.RecyclerViewAdapterCitizen;
import com.GMS.agent.helperClasses.CitizenItem;
import com.GMS.databinding.ActivityCylindersReceiveBinding;

import java.util.ArrayList;

public class CylindersReceiveActivity extends AppCompatActivity {

    ActivityCylindersReceiveBinding mBinding;
    ArrayList<CitizenItem> items = new ArrayList<>();
    RecyclerViewAdapterCitizen adapterCitizen;
    ItemClickListener mItemClickListener;
    public static final int CYLINDER_RECEIVE_LIST=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCylindersReceiveBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(mBinding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        items.add(new CitizenItem("Abdulrahman Khalid", "45ssad5", 3, 0, 3700.0, true));
        items.add(new CitizenItem("Abubaker Khalid", "32dsaa2", 4, 0, 3700.0, true));
        items.add(new CitizenItem("Omar Taha", "48000", 1, 0, 3700.0, true));
        items.add(new CitizenItem("Mohammed Shihab", "ahjtr5", 2, 0, 3700.0, true));
        items.add(new CitizenItem("Omar Swaid", "abcde1", 5, 0, 3700.0, true));
        items.add(new CitizenItem("hasan Someeri", "32514ad", 3, 0, 3700.0, true));
        mItemClickListener = new ItemClickListener() {
            @Override
            public void onClick(int position, boolean state) {
                Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

        };
        mBinding.progressLoading.setVisibility(View.GONE);
       // initRV();
    }
/*
    private void initRV() {
        RecyclerView.LayoutManager mLayoutManaager = new LinearLayoutManager(getBaseContext());
        adapterCitizen = new RecyclerViewAdapterCitizen(items, getBaseContext(), mItemClickListener, CYLINDER_RECEIVE_LIST);
        mBinding.rvCylindersReceive.setAdapter(adapterCitizen);
        mBinding.rvCylindersReceive.setLayoutManager(mLayoutManaager);
        mBinding.rvCylindersReceive.setHasFixedSize(true);
    }


 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_ic);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterCitizen.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}