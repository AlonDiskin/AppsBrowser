package com.diskin.alon.appsbrowser.browser.featuretest.steps;


import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.browser.featuretest.util.RecyclerViewMatcher;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import java.util.List;

import gherkin.ast.TableRow;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Step definitions for the 'list apps' feature rule.
 */
public class ListAppsSteps extends BackgroundSteps {

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

    @Then("^All device apps should be displayed sorted by name in ascending order$")
    public void allDeviceAppsShouldBeDisplayedSortedByNameInAscendingOrder() {
        // verify all user apps that was fetched from repository are displayed in a
        for (int i = 0;i < userAppEntities.size();i++) {

            UserAppEntity app = userAppEntities.get(i);

            onView(RecyclerViewMatcher.withRecyclerView(com.diskin.alon.appsbrowser.browser.R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withText(app.getName())),
                            hasDescendant(withText(app.getSize() + " MB")))));
        }
    }
}
