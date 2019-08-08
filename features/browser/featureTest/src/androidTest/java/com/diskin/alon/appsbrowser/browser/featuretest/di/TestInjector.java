package com.diskin.alon.appsbrowser.browser.featuretest.di;

import com.diskin.alon.appsbrowser.browser.featuretest.steps.AppDetailSteps;
import com.diskin.alon.appsbrowser.browser.featuretest.steps.ListAppsSteps;
import com.diskin.alon.appsbrowser.browser.featuretest.runner.FeatureTestApp;

public class TestInjector {

    private static AppComponent appComponent;

    public static void injectTestApp(FeatureTestApp testApp) {
        appComponent = DaggerAppComponent.create();

        appComponent.inject(testApp);
    }

    public static void injectTest(ListAppsSteps test) {
        appComponent.inject(test);
    }

    public static void injectTest(AppDetailSteps test) {
        appComponent.inject(test);
    }
}
