package com.diskin.alon.appsbrowser.browser.di;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;

import com.diskin.alon.appsbrowser.browser.runner.TestApp;
import com.diskin.alon.appsbrowser.browser.ui.BrowserFragmentTest;

public class TestInjector {

    public static void inject(@NonNull BrowserFragmentTest test) {
        TestAppComponent component = DaggerTestAppComponent.create();
        TestApp testApp = ApplicationProvider.getApplicationContext();

        component.inject(testApp);
        component.inject(test);
    }

}
