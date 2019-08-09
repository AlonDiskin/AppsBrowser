package com.diskin.alon.appsbrowser.settings;

import com.mauriciotogneri.greencoffee.GreenCoffeeConfig;
import com.mauriciotogneri.greencoffee.GreenCoffeeTest;
import com.mauriciotogneri.greencoffee.ScenarioConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;

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
        // Start steps test
        start(new SettingsSteps());
    }
}
