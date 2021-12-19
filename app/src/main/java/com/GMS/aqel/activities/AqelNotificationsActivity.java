package com.GMS.aqel.activities;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.GMS.R;
import com.GMS.databinding.ActivityAqelNotificationsBinding;

public class AqelNotificationsActivity extends AppCompatActivity {

    ActivityAqelNotificationsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAqelNotificationsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.md_theme_light_primary));
        setSupportActionBar(mBinding.toolBarNotifications);
        setTitle(R.string.notifications);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.tvRepMobileNumber.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        mBinding.tvAgentMobileNumber.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        mBinding.tvDate.setText(getString(R.string.date)+" 12-12-2020");
        mBinding.tvDayTime.setText(getString(R.string.day_and_time)+" Saturday - 5:00am");
        mBinding.tvRepName.setText(getString(R.string.name)+" Mohammed Shihab");
        mBinding.tvAgentName.setText(getString(R.string.name)+" Omar Taha");
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