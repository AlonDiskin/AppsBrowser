package com.diskin.alon.appsbrowser.settings.steps;

import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;
import com.mauriciotogneri.greencoffee.annotations.And;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import java.util.List;

import gherkin.ast.TableRow;

import static junit.framework.TestCase.fail;

/**
 * 'Customize theme' rule step definitions.
 */
public class CustomizeThemeSteps extends GreenCoffeeSteps {

    @Given("User open settings screen")
    public void userOpenSettingsScreen() {
        fail("not implemented yet");
    }

    @And("Open theme setting menu")
    public void openThemeSettingMenu() {
        fail("not implemented yet");
    }

    @Then("App has the next theme setting selections:")
    public void appHasTheNextThemeSettingSelections(List<TableRow> data) {
        fail("not implemented yet");
    }

    @Then("Existing preference are displayed")
    public void existingPreferenceAreDisplayed() {
        fail("not implemented yet");
    }

    @When("^User selects a \"([^\"]*)\" theme$")
    public void userSelectsATheme(String theme) {
        fail("not implemented yet");
    }

    @Then("^App visual theme should change to \"([^\"]*)\"$")
    public void appVisualThemeShouldChangeTo(String theme) {
        fail("not implemented yet");
    }
}
