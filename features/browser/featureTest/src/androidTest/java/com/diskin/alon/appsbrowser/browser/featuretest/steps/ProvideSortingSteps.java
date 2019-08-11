package com.diskin.alon.appsbrowser.browser.featuretest.steps;

import androidx.fragment.app.testing.FragmentScenario;

import com.diskin.alon.appsbrowser.browser.applicationservices.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.controller.BrowserFragment;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;
import com.mauriciotogneri.greencoffee.annotations.And;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import gherkin.ast.TableRow;
import io.reactivex.Observable;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.when;

/**
 * 'Provide sorting' feature rule step definitions.
 */
public class ProvideSortingSteps extends GreenCoffeeSteps {

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
    public void userOpensBrowserScreen() {
        // launch browser screen
        FragmentScenario<BrowserFragment> scenario = FragmentScenario.launchInContainer(BrowserFragment.class,
                null, com.diskin.alon.appsbrowser.browser.R.style.AppTheme,null);
    }

    @And("^Sorts apps by \"([^\"]*)\" in \"([^\"]*)\" order$")
    public void sortsAppsByInOrder(String sort, String order) {
        // Write code here that turns the phrase above into concrete actions
        fail("not implemented yet");
    }

    @Then("^Browser should display apps sorted by \"([^\"]*)\" in \"([^\"]*)\" order$")
    public void browserShouldDisplayAppsSortedByInOrder(String sort, String order) {
        // Write code here that turns the phrase above into concrete actions
        fail("not implemented yet");
    }
}
