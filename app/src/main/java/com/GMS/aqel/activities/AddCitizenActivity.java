package com.GMS.aqel.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.GMS.R;
import com.GMS.databinding.ActivityAddCitizenBinding;

public class AddCitizenActivity extends AppCompatActivity {

    ActivityAddCitizenBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddCitizenBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(mBinding.toolBarAddCitizen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String  []array = getResources().getStringArray(R.array.items_array);
        ArrayAdapter mAdapter = new ArrayAdapter(getBaseContext() , R.layout.spinner_item , array);
        mBinding.actvNeighborhoodName.setAdapter(mAdapter);

        mBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext() , "sucess" , Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding=null;
    }
}