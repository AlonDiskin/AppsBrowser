package com.diskin.alon.appsbrowser;

import com.mauriciotogneri.greencoffee.GreenCoffeeConfig;
import com.mauriciotogneri.greencoffee.GreenCoffeeTest;
import com.mauriciotogneri.greencoffee.ScenarioConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;

/**
 * {@link HomeStepdefs} test runner.
 */
@RunWith(Parameterized.class)
public class HomeStepsRunner extends GreenCoffeeTest {
    public HomeStepsRunner(ScenarioConfig scenario) {
        super(scenario);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<ScenarioConfig> scenarios() throws IOException {
        return new GreenCoffeeConfig()
                .withFeatureFromAssets("assets/feature/home.feature")
                .scenarios();
    }

    @Test
    public void test() {
        // Start steps test
        start(new HomeStepdefs());
    }
}
