package com.diskin.alon.appsbrowser.settings.steps;

import android.content.Context;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.diskin.alon.appsbrowser.settings.R;
import com.diskin.alon.appsbrowser.settings.SettingsFragment;
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;
import com.mauriciotogneri.greencoffee.annotations.And;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 'Customize theme' rule step definitions.
 */
public class CustomizeThemeSteps extends GreenCoffeeSteps {

    @Given("^App theme is set as\"([^\"]*)\"$")
    public void appThemeIsSetAs(String theme) {
        // set app theme according to test theme arg
        Context context = ApplicationProvider.getApplicationContext();
        int nightMode;
        String themeValue;

        if (theme.equals("dark")) {
            nightMode = AppCompatDelegate.MODE_NIGHT_YES;
            themeValue = "1";

        } else {
            nightMode = AppCompatDelegate.MODE_NIGHT_NO;
            themeValue = "0";
        }

        AppCompatDelegate.setDefaultNightMode(nightMode);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(context.getString(R.string.theme_pref_key),themeValue)
                .commit();
    }

    @When("User open settings screen")
    public void userOpenSettingsScreen() {
        FragmentScenario.launchInContainer(SettingsFragment.class,
                null, R.style.Theme_AppCompat_DayNight_DarkActionBar,null);
    }

    @And("Open theme setting menu")
    public void openThemeSettingMenu() {
        onView(withClassName(equalTo(RecyclerView.class.getName())))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(R.string.theme_pref_title)),click()));
    }

    @When("^User selects a \"([^\"]*)\" theme$")
    public void userSelectsATheme(String theme) {
        String selectedThemeMenuItem;

        if (theme.equals("dark")) {
            selectedThemeMenuItem = "Dark";
        } else {
            selectedThemeMenuItem = "Light";
        }

        onView(withText(selectedThemeMenuItem))
                .inRoot(isDialog())
                .perform(click());
    }

    @Then("^App visual theme should change to \"([^\"]*)\"$")
    public void appVisualThemeShouldChangeTo(String theme) {
        int expectedNightMode;

        if (theme.equals("dark")) {
            expectedNightMode = AppCompatDelegate.MODE_NIGHT_YES;

        } else {
            expectedNightMode = AppCompatDelegate.MODE_NIGHT_NO;
        }

        assertThat(AppCompatDelegate.getDefaultNightMode(),equalTo(expectedNightMode));
    }
}
