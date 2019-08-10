package com.diskin.alon.appsbrowser.settings;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;

/**
 * Application preferences fragment.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener =
            (prefs, key) -> {
                String appThemeKey = getString(R.string.theme_pref_key);

                if (key.equals(appThemeKey)) {
                    String themeDefault = getString(R.string.theme_pref_default_value);
                    String current = prefs.getString(appThemeKey, themeDefault);

                    //noinspection ConstantConditions
                    if (current.equals("0")) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                }
            };

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register a preference change mSharedPreferenceChangeListener
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the preference change mSharedPreferenceChangeListener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
    }
}
