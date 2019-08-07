package com.diskin.alon.appsbrowser.browser;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;

import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.util.Device;
import com.diskin.alon.appsbrowser.util.RecyclerViewMatcher;
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.fail;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Browser feature acceptance criteria step definitions.
 */
public class BrowserSteps extends GreenCoffeeSteps {

    private static final String TAG = "BrowserStepsTagTag";

    @Given("^User is in device home screen$")
    public void userIsInDeviceHomeScreen() {
        // set device to home screen
        Device.openHomeScreen();
    }

    @When("^User open application$")
    public void userOpenApplication() {
        // launch application
        Device.launchApp();
    }

    @Then("^All device apps should be listed in home screen sorted by name$")
    public void allDeviceAppsShouldBeListedInHomeScreenSortedByName() {
        // verify all device non system apps are displayed on screen as expected
        PackageManager pm = ApplicationProvider.getApplicationContext().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<UserApp> userApps = new ArrayList<>();

        for (int i = 0;i < packages.size();i++) {
            if( pm.getLaunchIntentForPackage(packages.get(i).packageName) != null ){
                String appName = pm.getApplicationLabel(packages.get(i)).toString();
                File file = new File(packages.get(i).publicSourceDir);
                double appSize = Long.valueOf(file.length()).doubleValue() / (1024 * 1024);
                String appSizeStr = String.format(Locale.getDefault(),"%.1f", appSize) + " MB";

                userApps.add(new UserApp("",appName,appSizeStr,""));
            }
        }

        Collections.sort(userApps,(o1, o2) -> o1.getName().compareTo(o2.getName()));

        for (int i = 0;i < userApps.size();i++) {
            onView(withId(R.id.userApps))
                    .perform(RecyclerViewActions.scrollToPosition(i));
            onView(RecyclerViewMatcher.withRecyclerView(R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withText(userApps.get(i).getName())),
                            hasDescendant(withText(userApps.get(i).getSize())))));
        }
    }

    @When("^User clicks on app entry in middle of displayed list$")
    public void userClicksOnAppEntryInMiddleOfDisplayedList() {
        fail("not implemented yet");
    }

    @Then("^User should be redirected to app detail screen of device settings app$")
    public void userShouldBeRedirectedToAppDetailScreenOfDeviceSettingsApp() {
        fail("not implemented yet");
    }
}
