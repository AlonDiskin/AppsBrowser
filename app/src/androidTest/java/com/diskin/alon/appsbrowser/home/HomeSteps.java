package com.diskin.alon.appsbrowser.home;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;

import com.diskin.alon.appsbrowser.browser.R;
import com.diskin.alon.appsbrowser.util.Device;
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;
import com.mauriciotogneri.greencoffee.annotations.And;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.fail;

/**
 * Home feature acceptance criteria step definitions.
 */
class HomeSteps extends GreenCoffeeSteps {

    @Given("^User is in device home screen$")
    public void userIsInDeviceHomeScreen() {
        Device.openHomeScreen();
    }

    @When("^User launch application$")
    public void userLaunchApplication() {
        Device.launchApp();
    }

    @Then("^Home screen should be displayed$")
    public void homeScreenShouldBeDisplayed() {
        onView(withId(com.diskin.alon.appsbrowser.R.id.main_activity_layout))
                .check(matches(isCompletelyDisplayed()));
    }

    @And("^Browser feature ui should be shown inside home screen$")
    public void browserFeatureUiShouldBeShownInsideHomeScreen() {
        onView(ViewMatchers.withId(R.id.userApps))
                .check(matches(isCompletelyDisplayed()));
    }

    @When("^User navigates to settings screen$")
    public void userNavigatesToSettingsScreen() {
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(com.diskin.alon.appsbrowser.home.R.string.title_action_settings))
                .perform(click());
    }

    @Then("^Settings ui should be shown as a composition in home screen$")
    public void settingsUiShouldBeShownAsACompositionInHomeScreen() {
//        onView(withId(com.diskin.alon.appsbrowser.settings.R.id.fragment_settings_layout))
//        .check(matches(isDisplayed()));
        fail("not implemented yet");
    }
}
