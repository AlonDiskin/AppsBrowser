package com.diskin.alon.appsbrowser.home.unit.di;

import android.app.Activity;
import android.app.Application;

import com.diskin.alon.appsbrowser.home.unit.MainActivityTest;

import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class TestApp extends Application implements HasActivityInjector,TestLifecycleApplication {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;
    TestAppComponent testAppComponent = DaggerTestAppComponent.create();

    @Override
    public void onCreate() {
        super.onCreate();
        testAppComponent.inject(this);
    }

    @Override
    public void beforeTest(Method method) {

    }

    @Override
    public void prepareTest(Object test) {
        if (test instanceof MainActivityTest) {
            testAppComponent.inject((MainActivityTest) test);
        }

    }

    @Override
    public void afterTest(Method method) {

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
