package com.diskin.alon.appsbrowser.home.integration;

import androidx.appcompat.widget.Toolbar;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import com.diskin.alon.appsbrowser.home.FakeSettingsFragment;
import com.diskin.alon.appsbrowser.home.MainActivity;
import com.diskin.alon.appsbrowser.home.R;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class CompositeSettingsSteps {

    private ActivityScenario<MainActivity> scenario;

    @Given("User is in home screen")
    public void userIsInHomeScreen() {
        // launch home screen activity
        scenario = ActivityScenario.launch(MainActivity.class);
    }

    @When("User navigates to settings screen")
    public void userNavigatesToSettingsScreen() {
        // open options menu and click on 'settings' item
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.title_action_settings))
                .perform(click());
    }

    @Then("Settings screen should be displayed inside home screen")
    public void settingsScreenShouldBeDisplayedInsideHomeScreen() {
        // verify settings fragment title is displayed in home activity toolbar
        scenario.onActivity(activity -> {
            Toolbar toolbar = activity.findViewById(R.id.toolbar);

            assertThat(toolbar.getTitle().toString(),
                    equalTo(ApplicationProvider.getApplicationContext()
                            .getString(R.string.title_fake_settings_fragment)));

            onView(withId(R.id.toolbar))
                    .check(matches(isDisplayed()));
        });

        // verify settings fragment is displayed in layout
        onView(withId(R.id.fake_settings_layout))
                .check(matches(isDisplayed()));

        // verify browser fragment is not displayed in layout
        onView(withId(R.id.fake_browser_layout))
                .check(doesNotExist());
    }
}
