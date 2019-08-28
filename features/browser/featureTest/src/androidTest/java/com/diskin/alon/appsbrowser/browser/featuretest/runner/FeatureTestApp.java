package com.diskin.alon.appsbrowser.browser.featuretest.runner;

import android.app.Application;

import androidx.fragment.app.Fragment;

import com.diskin.alon.appsbrowser.browser.featuretest.di.TestInjector;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class FeatureTestApp extends Application implements HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingFragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        TestInjector.injectTestApp(this);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingFragmentInjector;
    }
}
