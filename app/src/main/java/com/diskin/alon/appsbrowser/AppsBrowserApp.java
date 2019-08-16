package com.diskin.alon.appsbrowser;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.diskin.alon.appsbrowser.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class AppsBrowserApp extends Application implements HasActivityInjector , HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingFragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        setDayNightMode();
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);
    }

    private void setDayNightMode() {
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        String themePrefKey = getString(R.string.theme_pref_key);
        String themeDefaultValue = getString(R.string.theme_pref_default_value);
        String currentThemeValue = sh.getString(themePrefKey,themeDefaultValue);

        if (currentThemeValue.equals("0")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingFragmentInjector;
    }
}
