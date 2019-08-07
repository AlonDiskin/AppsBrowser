package com.diskin.alon.appsbrowser.browser.featuretest.steps;


import androidx.fragment.app.testing.FragmentScenario;

import com.diskin.alon.appsbrowser.browser.applicationservices.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.controller.BrowserFragment;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.browser.featuretest.util.RecyclerViewMatcher;
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import gherkin.ast.TableRow;
import io.reactivex.Observable;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.when;

/**
 * Step definitions for the 'list apps' feature scenarios.
 */
public class ListAppsSteps extends GreenCoffeeSteps {

    private static final String TAG = "ListAppsSteps";
    private List<UserAppEntity> userAppEntities = new ArrayList<>();

    @Inject
    public UserAppsRepository repository;

    @Given("^User has the next apps on device$")
    public void userHasTheNextAppsOnDevice(List<TableRow> userAppsData) {
        // set given user apps as the existing repository apps
        int nameCellIndex = 0;
        int sizeCellIndex = 1;
        int iconCellIndex = 2;
        int idCellIndex = 3;

        for (int i = 1;i < userAppsData.size();i++) {
            String id = userAppsData.get(i).getCells().get(idCellIndex).getValue();
            String name = userAppsData.get(i).getCells().get(nameCellIndex).getValue();
            double size = Double.parseDouble(userAppsData.get(i).getCells().get(sizeCellIndex).getValue());
            String iconUri = userAppsData.get(i).getCells().get(iconCellIndex).getValue();

            userAppEntities.add(new UserAppEntity(id,name,size,iconUri));
        }

        // stub repository mock to return test data apps when invoked
        Collections.sort(userAppEntities,(o1, o2) -> o1.getName().compareTo(o2.getName()));
        when(repository.getUserAppsByName()).thenReturn(Observable.just(userAppEntities));
    }

    @When("^User opens browser screen$")
    public void userOpensBrowserScreen() throws InterruptedException {
        // launch browser screen
        FragmentScenario<BrowserFragment> scenario = FragmentScenario.launchInContainer(BrowserFragment.class,
                null, com.diskin.alon.appsbrowser.browser.R.style.AppTheme,null);
        Thread.sleep(6000);
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
