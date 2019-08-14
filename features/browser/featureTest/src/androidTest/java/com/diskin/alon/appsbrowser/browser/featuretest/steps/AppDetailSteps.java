package com.diskin.alon.appsbrowser.browser.featuretest.steps;

import com.diskin.alon.appsbrowser.browser.controller.BrowserNavigator;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.mauriciotogneri.greencoffee.annotations.And;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import java.util.List;

import javax.inject.Inject;

import gherkin.ast.TableRow;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
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
        for (UserAppEntity entity : userAppEntities) {
            if (entity.getName().equals(appName)) {
                selectedAppId = entity.getId();
                break;
            }
        }

        onView(withText(appName))
                .perform(click());
    }

    @Then("^User should be redirected to app detail in settings application$")
    public void userShouldBeRedirectedToAppDetailInSettingsApplication() {
        // verify app detail screen is opened
        verify(navigator).openAppDetail(eq(selectedAppId));
    }
}
