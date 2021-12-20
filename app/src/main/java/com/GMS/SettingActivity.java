package com.GMS;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.GMS.databinding.ActivitySettingBinding;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding mBinding;
    private boolean editable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        setSupportActionBar(mBinding.toolBarSetting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.md_theme_light_primary));

        mBinding.constraintAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Account Constraint", Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.constraintBehavior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Behaviour Constraint", Toast.LENGTH_SHORT).show();

            }
        });
        mBinding.constraintNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Notifications Constraint", Toast.LENGTH_SHORT).show();

            }
        });
        mBinding.constraintHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Help Constraint", Toast.LENGTH_SHORT).show();

            }
        });
        mBinding.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editable) {
                    mBinding.ivEdit.setImageResource(R.drawable.ic_right);
                    mBinding.tvAccountName.setVisibility(View.GONE);
                    mBinding.accountNameLayout.setVisibility(View.VISIBLE);
                    mBinding.etAccountName.setText(mBinding.tvAccountName.getText());
                    mBinding.accountNameLayout.setFocusable(true);
                    mBinding.etAccountName.setFocusable(true);
                    editable = false;
                } else {
                    if (mBinding.etAccountName.getText().toString().trim().length() > 5) {
                        mBinding.ivEdit.setImageResource(R.drawable.ic_edit);
                        mBinding.accountNameLayout.setVisibility(View.GONE);
                        mBinding.tvAccountName.setVisibility(View.VISIBLE);
                        mBinding.tvAccountName.setText(mBinding.etAccountName.getText());
                        editable = true;
                    } else {
                        mBinding.ivEdit.setImageResource(R.drawable.ic_edit);
                        mBinding.accountNameLayout.setVisibility(View.GONE);
                        mBinding.tvAccountName.setVisibility(View.VISIBLE);
                        editable = true;
                        Toast.makeText(getBaseContext(), "should be contain at least 5 letters", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        mBinding.constraintBehavior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedDarkLightTheme = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext())
                        .getInt(getString(R.string.preferences_dark_light_mode_selected_key), AppCompatDelegate.MODE_NIGHT_NO);
                if (selectedDarkLightTheme == AppCompatDelegate.MODE_NIGHT_YES)
                    selectedDarkLightTheme = AppCompatDelegate.MODE_NIGHT_NO;
                else selectedDarkLightTheme = AppCompatDelegate.MODE_NIGHT_YES;
                PreferenceManager.getDefaultSharedPreferences(getBaseContext())
                        .edit()
                        .putInt(getString(R.string.preferences_dark_light_mode_selected_key), selectedDarkLightTheme)
                        .apply();
                GMS_Application.setDarkLightTheme(selectedDarkLightTheme);
            }
        });

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }
}