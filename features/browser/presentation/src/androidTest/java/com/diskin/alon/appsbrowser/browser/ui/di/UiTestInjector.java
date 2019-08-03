package com.diskin.alon.appsbrowser.browser.ui.di;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;

import com.diskin.alon.appsbrowser.browser.runner.TestApp;
import com.diskin.alon.appsbrowser.browser.ui.BrowserFragmentTest;

public class UiTestInjector {

    public static void inject(@NonNull BrowserFragmentTest test) {
        UiTestAppComponent component = DaggerUiTestAppComponent.create();
        TestApp testApp = ApplicationProvider.getApplicationContext();

        component.inject(testApp);
        component.inject(test);
    }

}
