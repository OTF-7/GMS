package com.GMS;

import android.app.Application;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

public class GMS_Application extends Application {
    public static void setDarkLightTheme(int selectedDarkLightTheme) {
        AppCompatDelegate.setDefaultNightMode(selectedDarkLightTheme);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int selectedDarkLightTheme = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getInt(getString(R.string.preferences_dark_light_mode_selected_key), AppCompatDelegate.MODE_NIGHT_NO);
        AppCompatDelegate.setDefaultNightMode(selectedDarkLightTheme);
    }
}
