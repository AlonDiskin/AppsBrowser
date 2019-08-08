package com.diskin.alon.appsbrowser.home.integration;

import androidx.test.core.app.ActivityScenario;

import com.diskin.alon.appsbrowser.home.MainActivity;
import com.diskin.alon.appsbrowser.home.R;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Step definitions for the 'composite browser' feature scenarios.
 */
public class CompositeBrowserSteps {

    @Given("User opened home screen")
    public void userOpenedHomeScreen() {
        // launch main activity
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
    }

    @Then("Browser feature ui should be displayed in home screen")
    public void browserFeatureUiShouldBeDisplayedInHomeScreen() {
        // verify fake browser ui is displayed
        onView(withId(R.id.fake_browser_layout))
                .check(matches(isCompletelyDisplayed()));
    }
}
