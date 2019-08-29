package com.diskin.alon.appsbrowser.browser.featuretest.steps;

import androidx.test.espresso.contrib.RecyclerViewActions;

import com.diskin.alon.appsbrowser.browser.controller.BrowserFragment;
import com.diskin.alon.appsbrowser.browser.controller.BrowserNavigator;
import com.diskin.alon.appsbrowser.browser.featuretest.R;
import com.mauriciotogneri.greencoffee.annotations.And;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import java.util.List;

import javax.inject.Inject;

import gherkin.ast.TableRow;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Step definitions for the 'app detail' feature rule.
 */
public class AppDetailSteps extends BackgroundSteps {
    @Inject
    public BrowserNavigator navigator;
    private String selectedAppId;

    @Override
    @Given("^User has the next apps on device$")
    public void userHasTheNextAppsOnDevice(List<TableRow> userAppsData) {
        super.userHasTheNextAppsOnDevice(userAppsData);
    }

    @Override
    @When("^User opens browser screen$")
    public void userOpensBrowserScreen() {
        super.userOpensBrowserScreen();
    }

    @And("^User clicks on listed \"([^\"]*)\" entry$")
    public void userClicksOnListedEntry(String appName) {
        for (int i = 0;i < getTestUserApp().size();i++) {
            // scroll until entry to click is present
            onView(withId(R.id.userApps))
                    .perform(RecyclerViewActions.scrollToPosition(i));

            if (getTestUserApp().get(i).getName().equals(appName)) {
                selectedAppId = getTestUserApp().get(i).getId();
            }
        }


        onView(withText(appName))
                .perform(click());
    }

    @Then("^User should be redirected to app detail in settings application$")
    public void userShouldBeRedirectedToAppDetailInSettingsApplication() {
        // verify app detail screen is opened
        scenario.onActivity(activity -> {
            BrowserFragment fragment = (BrowserFragment) activity.getSupportFragmentManager()
                    .findFragmentByTag(TAG);

            verify(navigator).openAppDetail(eq(fragment),eq(selectedAppId));
        });

    }
}
