package com.diskin.alon.appsbrowser.settings;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

/**
 * 'App theme setting' rule steps runner
 */
@RunWith(AndroidJUnit4.class)
public class ThemeSettingDisplayedStepsRunner {

    @Test
    public void run() {

        ThemeSettingDisplayedSteps steps = new ThemeSettingDisplayedSteps();

        steps.appHasTheNextThemePreference(Arrays.asList("dark","light"));
        steps.userOpenSettingsScreen();
        steps.openThemeSettingMenu();
        steps.existingPreferenceAreDisplayed();
    }
}
