package com.diskin.alon.appsbrowser.browser.featuretest.stepsrunner;

import com.diskin.alon.appsbrowser.browser.featuretest.di.TestInjector;
import com.diskin.alon.appsbrowser.browser.featuretest.steps.AppsSearchSteps;
import com.mauriciotogneri.greencoffee.GreenCoffeeConfig;
import com.mauriciotogneri.greencoffee.GreenCoffeeTest;
import com.mauriciotogneri.greencoffee.ScenarioConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;

/**
 * 'Apps searchApps' feature rule steps runner.
 */
@RunWith(Parameterized.class)
public class AppSearchStepsRunner extends GreenCoffeeTest {
    public AppSearchStepsRunner(ScenarioConfig scenario) {
        super(scenario);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<ScenarioConfig> scenarios() throws IOException {
        return new GreenCoffeeConfig()
                .withFeatureFromAssets("assets/feature/browser.feature")
                .withTags("@apps-search")
                .scenarios();
    }

    @Test
    public void runSteps() {
        // create the test to run
        AppsSearchSteps steps = new AppsSearchSteps();

        // inject test with needed dependencies
        TestInjector.injectTest(steps);

        // run test
        start(steps);
    }
}
