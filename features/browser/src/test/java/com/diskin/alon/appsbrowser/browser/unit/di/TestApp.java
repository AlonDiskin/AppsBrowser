package com.diskin.alon.appsbrowser.browser.unit.di;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.Fragment;

import com.diskin.alon.appsbrowser.browser.unit.BrowserFragmentTest;

import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class TestApp extends Application implements HasSupportFragmentInjector, TestLifecycleApplication {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingFragmentInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingFragmentInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void beforeTest(Method method) {

    }

    @Override
    public void prepareTest(Object test) {
        TestAppComponent component = DaggerTestAppComponent.create();

        component.inject(this);
        component.inject((BrowserFragmentTest) test);
    }

    @Override
    public void afterTest(Method method) {

    }
}
