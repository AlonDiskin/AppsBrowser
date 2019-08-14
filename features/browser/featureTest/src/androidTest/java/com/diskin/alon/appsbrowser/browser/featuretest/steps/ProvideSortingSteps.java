package com.diskin.alon.appsbrowser.browser.featuretest.steps;

import android.widget.CheckBox;

import com.diskin.alon.appsbrowser.browser.featuretest.R;
import com.diskin.alon.appsbrowser.browser.featuretest.util.RecyclerViewMatcher;
import com.mauriciotogneri.greencoffee.annotations.And;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gherkin.ast.TableRow;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.diskin.alon.appsbrowser.browser.featuretest.util.RecyclerViewMatcher.withRecyclerView;
import static junit.framework.TestCase.fail;
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
        // verify test data displayed as expected according to user sorting operation
        int nameCellIndex = 0;
        int sizeCellIndex = 1;
        List<TableRow> expectedAppsData = new ArrayList<>(this.appsTestData);

        switch (sort) {
            case "name":
                Collections.sort(expectedAppsData,(o1, o2) -> o1.getCells().get(nameCellIndex).getValue()
                        .compareTo(o2.getCells().get(nameCellIndex).getValue()));
                break;

            case "size":
                Collections.sort(expectedAppsData,(o1, o2) -> Double.compare(Double.parseDouble(o1.getCells()
                        .get(sizeCellIndex).getValue()),Double.parseDouble(o2.getCells()
                        .get(sizeCellIndex).getValue())));
                break;

            default:
                break;
        }

        if (order.equals("descending")) {
            Collections.reverse(expectedAppsData);
        }

        for (int i = 0; i < expectedAppsData.size();i++) {
            String name = expectedAppsData.get(i).getCells().get(nameCellIndex).getValue();
            double size = Double.parseDouble(expectedAppsData.get(i).getCells().get(sizeCellIndex).getValue());

            onView(withRecyclerView(com.diskin.alon.appsbrowser.browser.R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withText(name)),
                            hasDescendant(withText(size + " MB")))));
        }
    }
}
