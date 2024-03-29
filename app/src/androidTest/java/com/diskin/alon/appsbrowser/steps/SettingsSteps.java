package com.diskin.alon.appsbrowser.steps;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import com.diskin.alon.appsbrowser.R;
import com.diskin.alon.appsbrowser.util.Device;
import com.mauriciotogneri.greencoffee.annotations.And;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 'Settings' feature acceptance criteria tests steps
 */
public class SettingsSteps extends BackgroundSteps {

    @Override
    @Given("^User is in device home screen$")
    public void userIsInDeviceHomeScreen() {
        super.userIsInDeviceHomeScreen();
    }

    @Override
    @When("^User launch application$")
    public void userLaunchApplication() {
        super.userLaunchApplication();
    }

    @And("^Navigates to settings screen$")
    public void navigatesToSettingsScreen() {
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.title_action_settings))
                .perform(click());
    }

    @And("^Set theme to dark$")
    public void setThemeToDark() {
        onView(withClassName(equalTo(RecyclerView.class.getName())))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(ViewMatchers.withText(com.diskin.alon.appsbrowser.settings.R.string.theme_pref_title)),click()));

        onView(withText("Dark"))
                .inRoot(isDialog())
                .perform(click());
    }

    @Then("^App visual theme should be changed to dark$")
    public void appVisualThemeShouldBeChangedToDark() {
        assertThat(AppCompatDelegate.getDefaultNightMode(),equalTo(AppCompatDelegate.MODE_NIGHT_YES));
    }

    @When("^User exist app and returns$")
    public void userExistAppAndReturns() {
        Device.pressBack();
        Device.pressBack();
        Device.launchApp();
    }

    @Then("^App theme should be dark$")
    public void appThemeShouldBeDark() {
        assertThat(AppCompatDelegate.getDefaultNightMode(),equalTo(AppCompatDelegate.MODE_NIGHT_YES));
    }
}
