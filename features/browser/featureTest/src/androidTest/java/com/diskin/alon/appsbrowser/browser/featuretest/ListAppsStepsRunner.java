package com.diskin.alon.appsbrowser.browser.featuretest;

import com.mauriciotogneri.greencoffee.GreenCoffeeConfig;
import com.mauriciotogneri.greencoffee.GreenCoffeeTest;
import com.mauriciotogneri.greencoffee.ScenarioConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;

/**
 * 'list apps' feature steps runner.
 */
@RunWith(Parameterized.class)
public class ListAppsStepsRunner extends GreenCoffeeTest {

    public ListAppsStepsRunner(ScenarioConfig scenario) {
        super(scenario);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<ScenarioConfig> scenarios() throws IOException {
        return new GreenCoffeeConfig()
                .withFeatureFromAssets("assets/feature/browser.feature")
                .withTags("list-apps")
                .scenarios();
    }

    @Test
    public void runSteps() {
        start(new ListAppsSteps());
    }
}
