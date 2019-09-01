package com.diskin.alon.appsbrowser.stepsrunner;

import android.preference.PreferenceManager;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.IdlingRegistry;

import com.diskin.alon.appsbrowser.browser.data.AppsDataStoreImpl;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModelImpl;
import com.diskin.alon.appsbrowser.common.espressoidlingresource.EspressoIdlingResource;
import com.diskin.alon.appsbrowser.home.SplashActivity;
import com.diskin.alon.appsbrowser.steps.SettingsSteps;
import com.mauriciotogneri.greencoffee.GreenCoffeeConfig;
import com.mauriciotogneri.greencoffee.GreenCoffeeTest;
import com.mauriciotogneri.greencoffee.Scenario;
import com.mauriciotogneri.greencoffee.ScenarioConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.Locale;

/**
 * Settings feature acceptance criteria test runner.
 */
@RunWith(Parameterized.class)
public class SettingsStepsRunner extends GreenCoffeeTest {
    public SettingsStepsRunner(ScenarioConfig scenario) {
        super(scenario);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<ScenarioConfig> scenarios() throws IOException {
        return new GreenCoffeeConfig()
                .withFeatureFromAssets("assets/feature/apps_browser.feature")
                .withTags("@settings")
                .scenarios();
    }

    @Test
    public void test() {
        // indicate idling resource usage in app implementation
        BrowserViewModelImpl.TEST = true;
        AppsDataStoreImpl.TEST = true;
        SplashActivity.TEST = true;

        // register espresso idlingResource
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
        // instrumentation test cary state between runs,so reset shared preferences before run
        PreferenceManager.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext())
                .edit()
                .clear()
                .commit();
        // Start steps test
        start(new SettingsSteps());
    }

    @Override
    protected void afterScenarioEnds(Scenario scenario, Locale locale) {
        super.afterScenarioEnds(scenario, locale);
        // unregister espresso idlingResource
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }
}
