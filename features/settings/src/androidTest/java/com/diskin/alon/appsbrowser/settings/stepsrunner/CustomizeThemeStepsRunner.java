package com.diskin.alon.appsbrowser.settings.stepsrunner;

import com.diskin.alon.appsbrowser.settings.steps.CustomizeThemeSteps;
import com.mauriciotogneri.greencoffee.GreenCoffeeConfig;
import com.mauriciotogneri.greencoffee.GreenCoffeeTest;
import com.mauriciotogneri.greencoffee.ScenarioConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;

/**
 * 'Customize theme' rule steps runner
 */
@RunWith(Parameterized.class)
public class CustomizeThemeStepsRunner extends GreenCoffeeTest {

    public CustomizeThemeStepsRunner(ScenarioConfig scenario) {
        super(scenario);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Iterable<ScenarioConfig> scenarios() throws IOException {
        return new GreenCoffeeConfig()
                .withFeatureFromAssets("assets/feature/settings.feature")
                .withTags("@customize-theme")
                .scenarios();
    }

    @Test
    public void runSteps() {
        start(new CustomizeThemeSteps());
    }
}
