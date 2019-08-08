package com.diskin.alon.appsbrowser.home.integration;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.diskin.alon.appsbrowser.home.integration.di.TestApp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

/**
 * 'composite settings' feature rule steps runner.
 */
@RunWith(AndroidJUnit4.class)
@Config(application = TestApp.class)
public class CompositeSettingsTestRunner {

    @Test
    public void runTest() {
        CompositeSettingsSteps steps = new CompositeSettingsSteps();

        steps.userIsInHomeScreen();
        steps.userNavigatesToSettingsScreen();
        steps.settingsScreenShouldBeDisplayedInsideHomeScreen();
    }
}
