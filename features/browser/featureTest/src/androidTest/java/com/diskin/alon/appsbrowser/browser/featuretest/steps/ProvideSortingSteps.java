package com.diskin.alon.appsbrowser.browser.featuretest.steps;

import android.widget.CheckBox;

import androidx.test.espresso.contrib.RecyclerViewActions;

import com.diskin.alon.appsbrowser.browser.R;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.mauriciotogneri.greencoffee.annotations.And;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import gherkin.ast.TableRow;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.diskin.alon.appsbrowser.browser.featuretest.util.RecyclerViewMatcher.withRecyclerView;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.AllOf.allOf;

/**
 * 'Provide sorting' feature rule step definitions.
 */
public class ProvideSortingSteps extends BackgroundSteps {
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

    @And("^Sorts apps by \"([^\"]*)\" in \"([^\"]*)\" order$")
    public void sortsAppsByInOrder(String sort, String order) {
        // sort via screen sorting menu according to test data params
        onView(withId(R.id.action_sort))
                .perform(click());

        if (sort.equals("name")) {
            onView(withText(R.string.action_sort_by_name_title))
                    .perform(click());

        } else if (sort.equals("size")) {
            onView(withText(R.string.action_sort_by_size_title))
                    .perform(click());
        }

        // each scenario begins with 'ascending' checked by default, so check only
        // if test data check 'descending' case
        if (order.equals("descending")) {
            onView(withId(R.id.action_sort))
                    .perform(click());

            onView(isAssignableFrom(CheckBox.class))
                    .perform(click());
        }
    }

    @Then("^Browser should display apps sorted by \"([^\"]*)\" in \"([^\"]*)\" order$")
    public void browserShouldDisplayAppsSortedByInOrder(String sort, String order) {
        AppsSorting selectedSort = new AppsSorting(sort.equals("name") ? AppsSorting.SortingType.NAME : AppsSorting.SortingType.SIZE,
                order.equals("ascending"));
        List<UserApp> expectedDisplayedApps = getExpectedShownUserApps(selectedSort);

        assertThat(expectedDisplayedApps.size(),equalTo(getTestUserApp().size()));

        for (int i = 0; i < expectedDisplayedApps.size();i++) {
            String name = expectedDisplayedApps.get(i).getName();
            String size = expectedDisplayedApps.get(i).getSize();

            onView(withId(R.id.userApps))
                    .perform(RecyclerViewActions.scrollToPosition(i));
            onView(withRecyclerView(R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withText(name)),
                            hasDescendant(withText(size)))));
        }
    }

    private List<UserApp> getExpectedShownUserApps(AppsSorting sorting) {
        List<UserAppEntity> testUserApps = getTestUserApp();
        List<UserApp> expectedShownUserApps = new ArrayList<>(testUserApps.size());

        switch (sorting.getType()) {
            case NAME:
                Collections.sort(testUserApps,(o1, o2) -> o1.getName().compareTo(o2.getName()));
                break;

            case SIZE:
                Collections.sort(testUserApps,(o1, o2) -> Double.compare(o1.getSize(),o2.getSize()));
                break;

            default:
                break;
        }

        if (!sorting.isAscending()) {
            Collections.reverse(testUserApps);
        }

        for (UserAppEntity userAppEntity : testUserApps) {
            expectedShownUserApps.add(new UserApp(userAppEntity.getId(),
                    userAppEntity.getName(),
                    String.format(Locale.getDefault(),"%.1f", userAppEntity.getSize()) + " MB",
                    userAppEntity.getIconUri()));
        }

        return expectedShownUserApps;
    }
}
