package com.diskin.alon.appsbrowser.home.integration;

import androidx.test.core.app.ActivityScenario;

import com.diskin.alon.appsbrowser.home.MainActivity;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static junit.framework.TestCase.fail;

class CompositeSettingsSteps {
    @Given("User is in home screen")
    public void userIsInHomeScreen() {
        // launch home screen activity
        ActivityScenario.launch(MainActivity.class);
    }

    @When("User navigates to settings screen")
    public void userNavigatesToSettingsScreen() {
        fail("not implemented yet");
    }

    @Then("Settings screen should be displayed inside home screen")
    public void settingsScreenShouldBeDisplayedInsideHomeScreen() {
        fail("not implemented yet");
    }
}
