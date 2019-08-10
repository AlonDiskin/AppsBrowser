package com.diskin.alon.appsbrowser.settings;

import androidx.test.core.app.ApplicationProvider;

import com.diskin.alon.appsbrowser.util.Device;
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;
import com.mauriciotogneri.greencoffee.annotations.And;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.fail;

/**
 * 'Settings' feature acceptance criteria tests steps
 */
public class SettingsSteps extends GreenCoffeeSteps {

    @Given("^User is in device home screen$")
    public void userIsInDeviceHomeScreen() {
        Device.openHomeScreen();
    }

    @When("^User launch application$")
    public void userLaunchApplication() {
        Device.launchApp();
    }

    @And("^Navigates to settings screen$")
    public void navigatesToSettingsScreen() {
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(com.diskin.alon.appsbrowser.home.R.string.title_action_settings))
                .perform(click());
    }

    @And("^Set theme to dark$")
    public void setThemeToDark() {
        fail("not implemented yet");
    }

    @Then("^App visual theme should be changed to dark$")
    public void appVisualThemeShouldBeChangedToDark() {
        fail("not implemented yet");
    }

    @When("^User exist app and returns$")
    public void userExistAppAndReturns() {
        fail();
    }

    @Then("^App theme should be dark$")
    public void appThemeShouldBeDark() {
        fail();
    }
}
