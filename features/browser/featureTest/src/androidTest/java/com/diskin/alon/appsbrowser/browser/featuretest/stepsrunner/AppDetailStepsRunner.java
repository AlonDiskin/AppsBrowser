package com.diskin.alon.appsbrowser.browser.featuretest.stepsrunner;

import androidx.test.espresso.IdlingRegistry;

import com.diskin.alon.appsbrowser.browser.featuretest.di.TestInjector;
import com.diskin.alon.appsbrowser.browser.featuretest.steps.AppDetailSteps;
import com.diskin.alon.appsbrowser.browser.featuretest.steps.ListAppsSteps;
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
 * 'list apps' feature rule steps runner.
 */
@RunWith(Parameterized.class)
public class AppDetailStepsRunner extends GreenCoffeeTest {

    public AppDetailStepsRunner(ScenarioConfig scenario) {
        super(scenario);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<ScenarioConfig> scenarios() throws IOException {
        return new GreenCoffeeConfig()
                .withFeatureFromAssets("assets/feature/browser.feature")
                .withTags("@app-detail")
                .scenarios();
    }

    @Test
    public void runSteps() {
        // create the test to run
        AppDetailSteps steps = new AppDetailSteps();

        // inject test with needed dependencies
        TestInjector.injectTest(steps);

        // register espresso idlingResource
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());

        // run test
        start(steps);
    }

    @Override
    protected void afterScenarioEnds(Scenario scenario, Locale locale) {
        super.afterScenarioEnds(scenario, locale);
        // unregister espresso idlingResource
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }
}
