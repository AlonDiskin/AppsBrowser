package com.diskin.alon.appsbrowser.browser.featuretest.steps;

import com.diskin.alon.appsbrowser.browser.applicationservices.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
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

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.when;

/**
 * Step definitions for the 'app detail' feature rule.
 */
public class AppDetailSteps extends GreenCoffeeSteps {

    private static final String TAG = "AppDetailSteps";
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

    @When("^User clicks on listed \"([^\"]*)\" entry$")
    public void userClicksOnListedEntry(String appName) {
        fail("not implemented yet");
    }

    @Then("^User should be redirected to app detail in settings application$")
    public void userShouldBeRedirectedToAppDetailInSettingsApplication() {
        fail("not implemented yet");
    }
}
