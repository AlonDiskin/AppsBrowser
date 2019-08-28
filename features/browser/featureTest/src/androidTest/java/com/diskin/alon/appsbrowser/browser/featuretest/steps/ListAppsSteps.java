package com.diskin.alon.appsbrowser.browser.featuretest.steps;

import androidx.test.espresso.contrib.RecyclerViewActions;

import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.browser.featuretest.R;
import com.diskin.alon.appsbrowser.browser.featuretest.util.RecyclerViewMatcher;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import gherkin.ast.TableRow;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
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
        List<UserApp> expectedShownApps = getExpectedShownUserApps();

        assertThat(expectedShownApps.size(),equalTo(getTestUserApp().size()));

        for (int i = 0; i < expectedShownApps.size(); i++) {

            UserApp app = expectedShownApps.get(i);

            onView(withId(R.id.userApps))
                    .perform(RecyclerViewActions.scrollToPosition(i));
            onView(RecyclerViewMatcher.withRecyclerView(R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withText(app.getName())),
                            hasDescendant(withText(app.getSize())))));
        }
    }

    private List<UserApp> getExpectedShownUserApps() {
        List<UserAppEntity> testUserApps = getTestUserApp();
        List<UserApp> expectedShownUserApps = new ArrayList<>(testUserApps.size());

        Collections.sort(testUserApps,(o1, o2) -> o1.getName().compareTo(o2.getName()));

        for (UserAppEntity userAppEntity : testUserApps) {
            expectedShownUserApps.add(new UserApp(userAppEntity.getId(),
                    userAppEntity.getName(),
                    String.format(Locale.getDefault(),"%.1f", userAppEntity.getSize()) + " MB",
                    userAppEntity.getIconUri()));
        }

        return expectedShownUserApps;
    }
}
