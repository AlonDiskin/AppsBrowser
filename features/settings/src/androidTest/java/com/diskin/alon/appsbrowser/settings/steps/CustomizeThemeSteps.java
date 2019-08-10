package com.diskin.alon.appsbrowser.settings.steps;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.recyclerview.widget.RecyclerView;
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
    public void appThemeIsSetAs(String arg0) throws Throwable {


    }

    @When("User open settings screen")
    public void userOpenSettingsScreen() {
//        FragmentScenario.launchInContainer(SettingsFragment.class,
//                null, R.style.Theme_AppCompat_DayNight_DarkActionBar,null);
    }

    @And("Open theme setting menu")
    public void openThemeSettingMenu() {
//        onView(withClassName(equalTo(RecyclerView.class.getName())))
//                .perform(RecyclerViewActions.actionOnItem(
//                        hasDescendant(withText(R.string.theme_pref_title)),click()));
    }

    @When("^User selects a \"([^\"]*)\" theme$")
    public void userSelectsATheme(String theme) throws InterruptedException {
//        if (theme.equals("dark")) {
//            onView(withText("Dark"))
//                    .inRoot(isDialog())
//                    .perform(click());
//        } else {
//            onView(withText("Light"))
//                    .inRoot(isDialog())
//                    .perform(click());
//        }
//
//        Thread.sleep(4000);
    }

    @Then("^App visual theme should change to \"([^\"]*)\"$")
    public void appVisualThemeShouldChangeTo(String theme) {
//        if (theme.equals("dark")) {
//            assertThat(AppCompatDelegate.getDefaultNightMode(),equalTo(AppCompatDelegate.MODE_NIGHT_YES));
//
//        } else {
//            assertThat(AppCompatDelegate.getDefaultNightMode(),equalTo(AppCompatDelegate.MODE_NIGHT_NO));
//        }

        // verify current theme matches night values
        fail("not implemented yet");
    }
}
