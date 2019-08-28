package com.diskin.alon.appsbrowser.stepsrunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BrowserStepsRunner.class,
        SettingsStepsRunner.class})
public class AppStepsRunner {}
