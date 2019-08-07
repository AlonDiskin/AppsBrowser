package com.diskin.alon.appsbrowser.browser;

import androidx.test.espresso.IdlingRegistry;

import com.diskin.alon.appsbrowser.common.EspressoIdlingResource;
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
                .withFeatureFromAssets("assets/feature/browser.feature")
                .scenarios();
    }

    @Test
    public void test() {
        // register espresso idlingResource
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());

        // start test
        start(new BrowserSteps());
    }

    @Override
    protected void afterScenarioEnds(Scenario scenario, Locale locale) {
        super.afterScenarioEnds(scenario, locale);
        // unregister espresso idlingResource
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }
}
