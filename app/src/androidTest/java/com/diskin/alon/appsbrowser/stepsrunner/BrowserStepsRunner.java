package com.diskin.alon.appsbrowser.stepsrunner;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.intent.Intents;

import com.diskin.alon.appsbrowser.browser.data.AppsDataStoreImpl;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModelImpl;
import com.diskin.alon.appsbrowser.common.espressoidlingresource.EspressoIdlingResource;
import com.diskin.alon.appsbrowser.steps.BrowserSteps;
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
 * Browser feature acceptance criteria step definitions runner.
 */
@RunWith(Parameterized.class)
public class BrowserStepsRunner extends GreenCoffeeTest {
    public BrowserStepsRunner(ScenarioConfig scenario) {
        super(scenario);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<ScenarioConfig> scenarios() throws IOException {
        return new GreenCoffeeConfig()
                .withFeatureFromAssets("assets/feature/apps_browser.feature")
                .withTags("@browser")
                .scenarios();
    }

    @Test
    public void test() {
        // indicate idling resource usage in app implementation
        BrowserViewModelImpl.DECREMENT_TEST = true;
        AppsDataStoreImpl.INCREMENT_TEST = true;

        // register espresso idlingResource
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
        // register sent intents observer
        Intents.init();
        // start test
        start(new BrowserSteps());
    }

    @Override
    protected void afterScenarioEnds(Scenario scenario, Locale locale) {
        super.afterScenarioEnds(scenario, locale);
        // unregister espresso idlingResource
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
        // unregister sent intents observer
        Intents.release();
    }
}
