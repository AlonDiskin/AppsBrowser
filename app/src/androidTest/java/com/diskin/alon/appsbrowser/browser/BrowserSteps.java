package com.diskin.alon.appsbrowser.browser;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.CheckBox;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;

import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.util.Device;
import com.diskin.alon.appsbrowser.util.RecyclerViewMatcher;
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;
import com.mauriciotogneri.greencoffee.annotations.And;
import com.mauriciotogneri.greencoffee.annotations.Given;
import com.mauriciotogneri.greencoffee.annotations.Then;
import com.mauriciotogneri.greencoffee.annotations.When;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasFlag;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.fail;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Browser feature acceptance criteria step definitions.
 */
public class BrowserSteps extends GreenCoffeeSteps {

    private static final String TAG = "BrowserStepsTagTag";

    private List<UserApp> userApps = new ArrayList<>();

    @Given("^User is in device home screen$")
    public void userIsInDeviceHomeScreen() {
        // set device to home screen
        Device.openHomeScreen();
    }

    @When("^User launch application$")
    public void userLaunchApplication() {
        // launch application
        Device.launchApp();
        Intents.init();
    }

    @Then("^All device apps should be listed in home screen sorted by name$")
    public void allDeviceAppsShouldBeListedInHomeScreenSortedByName() {
        // verify all device non system apps are displayed on screen as expected
        PackageManager pm = ApplicationProvider.getApplicationContext().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (int i = 0;i < packages.size();i++) {
            if( pm.getLaunchIntentForPackage(packages.get(i).packageName) != null ){
                String appName = pm.getApplicationLabel(packages.get(i)).toString();
                String packageName = packages.get(i).packageName;
                File file = new File(packages.get(i).publicSourceDir);
                double appSize = Long.valueOf(file.length()).doubleValue() / (1024 * 1024);
                String appSizeStr = String.format(Locale.getDefault(),"%.1f", appSize) + " MB";

                userApps.add(new UserApp(packageName,appName,appSizeStr,""));
            }
        }

        Collections.sort(userApps,(o1, o2) -> o1.getName().compareTo(o2.getName()));

        for (int i = 0;i < userApps.size();i++) {
            onView(withId(R.id.userApps))
                    .perform(scrollToPosition(i));
            onView(RecyclerViewMatcher.withRecyclerView(R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withText(userApps.get(i).getName())),
                            hasDescendant(withText(userApps.get(i).getSize())))));
        }
    }

    @When("^User clicks on app entry in middle of displayed list$")
    public void userClicksOnAppEntryInMiddleOfDisplayedList() {
        int position = userApps.size() / 2;
        onView(withId(R.id.userApps))
                .perform(scrollToPosition(position));

        onView(withId(R.id.userApps))
                .perform(actionOnItemAtPosition(position, click()));
    }

    @Then("^User should be redirected to app detail screen of device settings app$")
    public void userShouldBeRedirectedToAppDetailScreenOfDeviceSettingsApp() {
        String clickedAppPackage = userApps.get(userApps.size() / 2).getPackageName();
        intended(allOf(
                hasData(Uri.parse("package:" + clickedAppPackage)),
                hasAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS),
                hasFlag(Intent.FLAG_ACTIVITY_NEW_TASK)));
    }

    @When("^User return to browser screen$")
    public void userReturnToBrowserScreen() {
        Device.pressBack();
    }

    @And("^Sort apps by size in descending order$")
    public void sortAppsBySizeInDescendingOrder() {
        onView(withId(R.id.action_sort))
                .perform(click());

        onView(withText(R.string.action_sort_by_size_title))
                .perform(click());

        onView(withId(R.id.action_sort))
                .perform(click());

        onView(isAssignableFrom(CheckBox.class))
                .perform(click());
    }

    @Then("^Apps should be displayed sorted by size in descending order$")
    public void appsShouldBeDisplayedSortedBySizeInDescendingOrder() {
        // verify all device non system apps are displayed on screen as expected
        PackageManager pm = ApplicationProvider.getApplicationContext().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        Collections.sort(packages,(o1, o2) -> {
            File file1 = new File(o1.publicSourceDir);
            File file2 = new File(o2.publicSourceDir);
            double appSize1 = Long.valueOf(file1.length()).doubleValue() / (1024 * 1024);
            double appSize2 = Long.valueOf(file2.length()).doubleValue() / (1024 * 1024);

            return Double.compare(appSize1,appSize2);
        });

        Collections.reverse(packages);
        userApps.clear();

        for (int i = 0;i < packages.size();i++) {
            if( pm.getLaunchIntentForPackage(packages.get(i).packageName) != null ){
                String appName = pm.getApplicationLabel(packages.get(i)).toString();
                String packageName = packages.get(i).packageName;
                File file = new File(packages.get(i).publicSourceDir);
                double appSize = Long.valueOf(file.length()).doubleValue() / (1024 * 1024);
                String appSizeStr = String.format(Locale.getDefault(),"%.1f", appSize) + " MB";

                userApps.add(new UserApp(packageName,appName,appSizeStr,""));
            }
        }

        for (int i = 0;i < userApps.size();i++) {
            onView(withId(R.id.userApps))
                    .perform(scrollToPosition(i));
            onView(RecyclerViewMatcher.withRecyclerView(R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withText(userApps.get(i).getName())),
                            hasDescendant(withText(userApps.get(i).getSize())))));
        }
    }
}
