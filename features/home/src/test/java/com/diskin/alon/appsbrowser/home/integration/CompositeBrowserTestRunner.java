package com.diskin.alon.appsbrowser.home.integration;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.diskin.alon.appsbrowser.home.integration.di.TestApp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

/**
 * 'composite browser' feature steps runner.
 */
@RunWith(AndroidJUnit4.class)
@Config(application = TestApp.class)
public class CompositeBrowserTestRunner {

    @Test
    public void runTest() {
        CompositeBrowserSteps steps = new CompositeBrowserSteps();

        steps.userOpenedHomeScreen();
        steps.browserFeatureUiShouldBeDisplayedInHomeScreen();
    }
}
