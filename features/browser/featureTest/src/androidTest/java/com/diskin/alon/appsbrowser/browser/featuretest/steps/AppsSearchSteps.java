package com.diskin.alon.appsbrowser.browser.featuretest.steps;

import android.widget.CheckBox;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting.SortingType;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.browser.featuretest.R;
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
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.diskin.alon.appsbrowser.browser.featuretest.util.RecyclerViewMatcher.withRecyclerView;
import static com.diskin.alon.appsbrowser.browser.featuretest.util.TextViewContainsMatcher.withTextContainsIgnoreCase;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Step definitions for the 'apps searchApps' feature rule.
 */
public class AppsSearchSteps extends BackgroundSteps{
    private AppsSorting lastSort;

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

    @When("^User performs search with \"([^\"]*)\" query$")
    public void userPerformsSearchWithQuery(String query) {
        onView(withId(R.id.action_search))
                .perform(click());
        onView(withHint(R.string.search_hint))
                .perform(typeText(query));
    }

    @Then("^Only apps that contains \"([^\"]*)\" in name are displayed sorted and ordered by default values$")
    public void onlyAppsThatContainsInNameAreDisplayedSortedAndOrderedByDefaultValues(String query) {
        AppsSorting defaultSort = new AppsSorting(SortingType.NAME,true);

        verifyAppsShownAccordingToQueryAndSort(query,defaultSort);
    }

    @When("^User sort apps by \"([^\"]*)\" in \"([^\"]*)\" order$")
    public void userSortAppsByInOrder(String sort, String order) {
        onView(withId(com.diskin.alon.appsbrowser.browser.R.id.action_sort))
                .perform(click());

        if (sort.equals("name")) {
            onView(withText(com.diskin.alon.appsbrowser.browser.R.string.action_sort_by_name_title))
                    .perform(click());

        } else if (sort.equals("size")) {
            onView(withText(com.diskin.alon.appsbrowser.browser.R.string.action_sort_by_size_title))
                    .perform(click());
        }

        // each scenario begins with 'ascending' checked by default, so check only
        // if test 'order' param is 'descending'
        if (order.equals("descending")) {
            onView(withId(com.diskin.alon.appsbrowser.browser.R.id.action_sort))
                    .perform(click());
            onView(isAssignableFrom(CheckBox.class))
                    .perform(click());
        }
    }

    @Then("^Apps that contains \"([^\"]*)\" in name are displayed sorted by \"([^\"]*)\" in \"([^\"]*)\" order$")
    public void appsThatContainsInNameAreDisplayedSortedByInOrder(String query, String sort, String order) {
        AppsSorting selectedSort = new AppsSorting(sort.equals("name") ? SortingType.NAME : SortingType.SIZE,
                order.equals("ascending"));

        verifyAppsShownAccordingToQueryAndSort(query,selectedSort);
        lastSort = selectedSort;
    }

    @When("^User close search$")
    public void userCloseSearch() {
        Espresso.pressBack();
        Espresso.pressBack();
    }

    @Then("^All apps should be shown according to last sorting values$")
    public void allAppsShouldBeShownAccordingToLastSortingValues() {
        verifyAppsShownAccordingToQueryAndSort("",lastSort);
    }

    private void verifyAppsShownAccordingToQueryAndSort(String query,AppsSorting sorting) {
        List<UserApp> expectedDisplayedApps = getExpectedShownUserApps(query, sorting);

        for (int i = 0; i < expectedDisplayedApps.size(); i++) {
            UserApp userApp = expectedDisplayedApps.get(i);

            if (!query.isEmpty()) {
                onView(withId(R.id.userApps))
                        .perform(RecyclerViewActions.scrollToPosition(i));
                onView(withRecyclerView(R.id.userApps).atPosition(i))
                        .check(matches(allOf(
                                hasDescendant(withTextContainsIgnoreCase(userApp.getName(),query)),
                                hasDescendant(withText(userApp.getSize())))));

            } else {
                onView(withId(R.id.userApps))
                        .perform(RecyclerViewActions.scrollToPosition(i));
                onView(withRecyclerView(R.id.userApps).atPosition(i))
                        .check(matches(allOf(hasDescendant(withText(userApp.getName())),
                                hasDescendant(withText(userApp.getSize())))));
            }
        }
    }

    private List<UserApp> getExpectedShownUserApps(String query, AppsSorting sorting) {
        List<UserAppEntity> testUserApp = getTestUserApp();
        List<UserApp> expectedShownUserApps = new ArrayList<>(testUserApp.size());

        switch (sorting.getType()) {
            case NAME:
                Collections.sort(testUserApp,(o1, o2) -> o1.getName().compareTo(o2.getName()));
                break;

            case SIZE:
                Collections.sort(testUserApp,(o1, o2) -> Double.compare(o1.getSize(),o2.getSize()));
                break;

            default:
                break;
        }

        if (!sorting.isAscending()) {
            Collections.reverse(testUserApp);
        }

        if (!query.isEmpty()) {
            for (UserAppEntity userAppEntity : testUserApp) {
                if (userAppEntity.getName().toLowerCase().contains(query)) {
                    expectedShownUserApps.add(new UserApp(userAppEntity.getId(),
                            userAppEntity.getName(),
                            String.format(Locale.getDefault(),"%.1f", userAppEntity.getSize()) + " MB",
                            userAppEntity.getIconUri()));
                }
            }
        }

        return expectedShownUserApps;
    }
}
