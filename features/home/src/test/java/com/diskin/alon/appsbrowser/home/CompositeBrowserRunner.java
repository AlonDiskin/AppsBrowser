package com.diskin.alon.appsbrowser.home;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 'composite browser' feature steps runner.
 */
@RunWith(AndroidJUnit4.class)
public class CompositeBrowserRunner {

    @Test
    public void runTest() {
        CompositeBrowserSteps steps = new CompositeBrowserSteps();

        steps.userOpenedHomeScreen();
        steps.browserFeatureUiShouldBeDisplayedInHomeScreen();
    }
}
