package com.diskin.alon.appsbrowser.browser;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 'list apps' feature steps runner.
 */
@RunWith(AndroidJUnit4.class)
public class ListAppsStepsRunner {

    @Test
    public void runSteps() {
        ListAppsSteps steps = new ListAppsSteps();
        steps.userHasTheNextAppsOnDevice();
        steps.userOpensBrowserScreen();
        steps.allDeviceAppsShouldBeDisplayed();
    }
}
