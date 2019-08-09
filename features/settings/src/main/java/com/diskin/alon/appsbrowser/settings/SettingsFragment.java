package com.diskin.alon.appsbrowser.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

/**
 * Application preferences fragment.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

}
