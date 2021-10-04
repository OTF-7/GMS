package com.GMS;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.GMS.GeneralAdapters.RecyclerViewAdapterHistory;
import com.GMS.GeneralClasses.HistoryItem;
import com.GMS.GeneralClasses.Report;
import com.GMS.GeneralClasses.SingleItemClickListener;
import com.GMS.databinding.ActivityHistoryBinding;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding mBinding;
    RecyclerViewAdapterHistory adapter;
    SingleItemClickListener mSingleItemClickListener;
    ArrayList<HistoryItem> mHistoryItems = new ArrayList<>();
    private final static int PERMISSION_EXTERNAL_STORAGE = 105;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolBarHistory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        mHistoryItems.add(new HistoryItem("Al-kornish", "12/10/2020", "Abubaker khalid", "General", 3650, 252));
        mHistoryItems.add(new HistoryItem("Al-kornish", "12/10/2020", "Abubaker khalid", "General", 3650, 252));
        mHistoryItems.add(new HistoryItem("Al-kornish", "12/10/2020", "Abubaker khalid", "General", 3650, 252));
        mHistoryItems.add(new HistoryItem("Al-kornish", "15/10/2020", "Abubaker khalid", "General", 3650, 252));
        mHistoryItems.add(new HistoryItem("Al-kornish", "14/10/2020", "Abubaker khalid", "General", 3650, 252));

        mSingleItemClickListener = new SingleItemClickListener() {
            @Override
            public void onClick(String location, String date) {

                Toast.makeText(getBaseContext(), "go to the details activity", Toast.LENGTH_SHORT).show();
                // here you write the code which take us to the details activity
                Intent ADAIntent = new Intent(getBaseContext(), ActionDetailsActivity.class);
                startActivity(ADAIntent);
            }
        };
        initRV();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_history, menu);
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


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.full_report:
                createPdf();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRV() {
        adapter = new RecyclerViewAdapterHistory(getBaseContext(), mHistoryItems, mSingleItemClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        mBinding.rvHistory.setLayoutManager(layoutManager);
        mBinding.rvHistory.setAdapter(adapter);
        mBinding.rvHistory.setHasFixedSize(true);
    }

    private void createPdf() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mBinding.getRoot().getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_STORAGE);
            return;
        }
        Report.Pdf.Actions.createActions(mHistoryItems , getBaseContext()); ;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_EXTERNAL_STORAGE:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getBaseContext(), "granted external storage", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "dismiss external storage", Toast.LENGTH_SHORT).show();

                }
        }
    }

}