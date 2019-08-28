package com.diskin.alon.appsbrowser.browser.featuretest.stepsrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ListAppsStepsRunner.class,
        ProvideSortingStepsRunner.class,
        AppDetailStepsRunner.class,
        AppSearchStepsRunner.class})
public class FeatureStepsRunner {}
