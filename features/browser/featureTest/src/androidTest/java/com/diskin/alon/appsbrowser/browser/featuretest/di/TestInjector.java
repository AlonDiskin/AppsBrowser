package com.diskin.alon.appsbrowser.browser.featuretest.di;

import androidx.test.core.app.ApplicationProvider;

import com.diskin.alon.appsbrowser.browser.featuretest.steps.AppDetailSteps;
import com.diskin.alon.appsbrowser.browser.featuretest.steps.AppsSearchSteps;
import com.diskin.alon.appsbrowser.browser.featuretest.steps.ListAppsSteps;
import com.diskin.alon.appsbrowser.browser.featuretest.runner.FeatureTestApp;
import com.diskin.alon.appsbrowser.browser.featuretest.steps.ProvideSortingSteps;

public class TestInjector {

    private static AppComponent appComponent;

    public static void injectTestApp(FeatureTestApp testApp) {
        appComponent = DaggerAppComponent.builder()
        .application(ApplicationProvider.getApplicationContext())
        .build();

        appComponent.inject(testApp);
    }

    public static void injectTest(ListAppsSteps test) {
        appComponent.inject(test);
    }

    public static void injectTest(ProvideSortingSteps test) {
        appComponent.inject(test);
    }

    public static void injectTest(AppDetailSteps test) {
        appComponent.inject(test);
    }

    public static void injectTest(AppsSearchSteps test) {
        appComponent.inject(test);
    }
}
