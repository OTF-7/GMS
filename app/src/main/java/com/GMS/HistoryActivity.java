package com.GMS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.GMS.GeneralAdapters.RecyclerViewAdapterHistory;
import com.GMS.GeneralClasses.HistoryItem;
import com.GMS.GeneralClasses.SingleItemClickListener;
import com.GMS.databinding.ActivityHistoryBinding;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding mBinding;
    RecyclerViewAdapterHistory adapter ;
    SingleItemClickListener mSingleItemClickListener;
    ArrayList<HistoryItem> mHistoryItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolBarHistory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        mHistoryItems.add(new HistoryItem("Al-kornish","12/10/2020" , "Abubaker khalid" , "General" , 3650 , 252));
        mHistoryItems.add(new HistoryItem("Al-kornish","12/10/2020" , "Abubaker khalid" , "General" , 3650 , 252));
        mHistoryItems.add(new HistoryItem("Al-kornish","12/10/2020" , "Abubaker khalid" , "General" , 3650 , 252));
        mHistoryItems.add(new HistoryItem("Al-kornish","15/10/2020" , "Abubaker khalid" , "General" , 3650 , 252));
        mHistoryItems.add(new HistoryItem("Al-kornish","14/10/2020" , "Abubaker khalid" , "General" , 3650 , 252));

        mSingleItemClickListener = new SingleItemClickListener() {
            @Override
            public void onClick(String location, String date) {

                Toast.makeText(getBaseContext() ,"go to the details activity" , Toast.LENGTH_SHORT).show();
                // here you write the code which take us to the details activity
            }
        };
        initRV();



    }

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

                    adapter.getFilter().filter(newText);

                return false;
            }

        });


        return  true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private  void initRV()
    {
        adapter = new RecyclerViewAdapterHistory(getBaseContext() , mHistoryItems , mSingleItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        mBinding.rvHistory.setLayoutManager(layoutManager);
        mBinding.rvHistory.setAdapter(adapter);
        mBinding.rvHistory.setHasFixedSize(true);
    }


}