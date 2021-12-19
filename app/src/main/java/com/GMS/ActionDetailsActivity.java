package com.GMS;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.GMS.GeneralAdapters.RecyclerViewAdapterDetailsHistory;
import com.GMS.GeneralClasses.ActionDetailsCitizen;
import com.GMS.GeneralClasses.Report;
import com.GMS.databinding.ActivityActionDetailsBinding;

import java.util.ArrayList;

public class ActionDetailsActivity extends AppCompatActivity {

    ActivityActionDetailsBinding mBinding;
    ArrayList<ActionDetailsCitizen> items = new ArrayList<>();
    RecyclerViewAdapterDetailsHistory adapter;
    private final static int PERMISSION_EXTERNAL_STORAGE = 105;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityActionDetailsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolBarDetails);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.md_theme_light_primary));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        items.add(new ActionDetailsCitizen("done","Abdulrahman khalid" , 4 ,"Abubaker Khalid" ,"Omar Taha"));
        items.add(new ActionDetailsCitizen("done","Abdulrahman khalid" , 4 ,"Abubaker Khalid" ,"Omar Taha"));
        items.add(new ActionDetailsCitizen("done","Abdulrahman khalid" , 4 ,"Abubaker Khalid" ,"Omar Taha"));
        items.add(new ActionDetailsCitizen("done","Abdulrahman khalid" , 4 ,"Abubaker Khalid" ,"Omar Taha"));
        initRV();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_history, menu);
        MenuItem searchItem = menu.findItem(R.id.agent_search_ic);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.full_report:
                createPdf();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRV()
    {
        adapter = new RecyclerViewAdapterDetailsHistory(items , getBaseContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        mBinding.rvDetails.setLayoutManager(layoutManager);
        mBinding.rvDetails.setAdapter(adapter);
        mBinding.rvDetails.setHasFixedSize(true);
    }
    private void createPdf() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mBinding.getRoot().getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_STORAGE);
            return;
        }
        Report.Pdf.ActionDetails.createDetails(items , getBaseContext()); ;
    }

}