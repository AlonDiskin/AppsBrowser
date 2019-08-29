package com.diskin.alon.appsbrowser.steps;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.CheckBox;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting.SortingType;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.util.Device;
import com.diskin.alon.appsbrowser.util.RecyclerViewMatcher;
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
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.diskin.alon.appsbrowser.util.TextViewContainsMatcher.withTextContainsIgnoreCase;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Browser feature acceptance criteria step definitions.
 */
public class BrowserSteps extends BackgroundSteps {
    private List<UserApp> expectedDisplayedApps;
    @Override
    @Given("^User is in device home screen$")
    public void userIsInDeviceHomeScreen() {
        super.userIsInDeviceHomeScreen();
    }

    @Override
    @When("^User launch application$")
    public void userLaunchApplication() {
        super.userLaunchApplication();
    }

    @Then("^All device apps should be listed in home screen sorted by name$")
    public void allDeviceAppsShouldBeListedInHomeScreenSortedByName() {
        expectedDisplayedApps = getExpectedDisplayedApps("",
                new AppsSorting(SortingType.NAME,true));

        for (int i = 0;i < expectedDisplayedApps.size();i++) {
            UserApp userApp = expectedDisplayedApps.get(i);

            onView(ViewMatchers.withId(com.diskin.alon.appsbrowser.browser.R.id.userApps))
                    .perform(scrollToPosition(i));
            onView(RecyclerViewMatcher.withRecyclerView(com.diskin.alon.appsbrowser.browser.R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withText(userApp.getName())),
                            hasDescendant(withText(userApp.getSize())))));
        }
    }

    @When("^User clicks on app entry in middle of displayed list$")
    public void userClicksOnAppEntryInMiddleOfDisplayedList() {
        int position = expectedDisplayedApps.size() / 2;
        onView(ViewMatchers.withId(com.diskin.alon.appsbrowser.browser.R.id.userApps))
                .perform(scrollToPosition(position));

        onView(ViewMatchers.withId(com.diskin.alon.appsbrowser.browser.R.id.userApps))
                .perform(actionOnItemAtPosition(position, click()));
    }

    @Then("^User should be redirected to app detail screen of device settings app$")
    public void userShouldBeRedirectedToAppDetailScreenOfDeviceSettingsApp() {
        String clickedAppPackage = expectedDisplayedApps.get(expectedDisplayedApps.size() / 2)
                .getPackageName();

        intended(allOf(hasData(Uri.parse("package:" + clickedAppPackage)),
                hasAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)));
    }

    @When("^User return to browser screen$")
    public void userReturnToBrowserScreen() {
        Device.pressBack();
    }

    @And("^Sort apps by size in descending order$")
    public void sortAppsBySizeInDescendingOrder() {
        onView(ViewMatchers.withId(com.diskin.alon.appsbrowser.browser.R.id.action_sort))
                .perform(click());

        onView(ViewMatchers.withText(com.diskin.alon.appsbrowser.browser.R.string.action_sort_by_size_title))
                .perform(click());

        onView(ViewMatchers.withId(com.diskin.alon.appsbrowser.browser.R.id.action_sort))
                .perform(click());

        onView(isAssignableFrom(CheckBox.class))
                .perform(click());
    }

    @Then("^Apps should be displayed sorted by size in descending order$")
    public void appsShouldBeDisplayedSortedBySizeInDescendingOrder() {
        expectedDisplayedApps = getExpectedDisplayedApps("",
                new AppsSorting(SortingType.SIZE,false));

        for (int i = 0;i < expectedDisplayedApps.size();i++) {
            UserApp userApp = expectedDisplayedApps.get(i);

            onView(ViewMatchers.withId(com.diskin.alon.appsbrowser.browser.R.id.userApps))
                    .perform(scrollToPosition(i));
            onView(RecyclerViewMatcher.withRecyclerView(com.diskin.alon.appsbrowser.browser.R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withText(userApp.getName())),
                            hasDescendant(withText(userApp.getSize())))));
        }
    }

    @When("^User searches for apps that begins with letter 'a'$")
    public void userSearchesForAppsThatBeginsWithLetterA() {
        onView(ViewMatchers.withId(com.diskin.alon.appsbrowser.browser.R.id.action_search))
                .perform(click());
        onView(ViewMatchers.withHint(com.diskin.alon.appsbrowser.browser.R.string.search_hint))
                .perform(typeText("a"));
    }

    @Then("^All apps that begin with letter 'a' displayed sorted by size in descending order$")
    public void allAppsThatBeginWithLetterADisplayedSortedBySizeInDescendingOrder() {
        expectedDisplayedApps = getExpectedDisplayedApps("a",
                new AppsSorting(SortingType.SIZE,false));

        for (int i = 0;i < expectedDisplayedApps.size();i++) {
            UserApp userApp = expectedDisplayedApps.get(i);

            onView(ViewMatchers.withId(com.diskin.alon.appsbrowser.browser.R.id.userApps))
                    .perform(scrollToPosition(i));
            onView(RecyclerViewMatcher.withRecyclerView(com.diskin.alon.appsbrowser.browser.R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withTextContainsIgnoreCase(userApp.getName(),"a")),
                            hasDescendant(withText(userApp.getSize())))));
        }


    }

    private List<UserApp> getExpectedDisplayedApps(String query, AppsSorting sorting) {
        PackageManager pm = ApplicationProvider.getApplicationContext().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<UserApp> expectedDisplayed = new ArrayList<>();

        switch (sorting.getType()) {
            case NAME:
                Collections.sort(packages,(o1, o2) -> {
                    String appName1 = pm.getApplicationLabel(o1).toString();
                    String appName2 = pm.getApplicationLabel(o2).toString();

                    return appName1.compareTo(appName2);
                });
                break;

            case SIZE:
                Collections.sort(packages,(o1, o2) -> {
                    File file1 = new File(o1.publicSourceDir);
                    File file2 = new File(o2.publicSourceDir);
                    double appSize1 = Long.valueOf(file1.length()).doubleValue() / (1024 * 1024);
                    double appSize2 = Long.valueOf(file2.length()).doubleValue() / (1024 * 1024);

                    return Double.compare(appSize1,appSize2);
                });
                break;

            default:
                break;
        }

        for (int i = 0;i < packages.size();i++) {
            if( pm.getLaunchIntentForPackage(packages.get(i).packageName) != null ){
                String appName = pm.getApplicationLabel(packages.get(i)).toString();
                String packageName = packages.get(i).packageName;
                File file = new File(packages.get(i).publicSourceDir);
                double appSize = Long.valueOf(file.length()).doubleValue() / (1024 * 1024);
                String appSizeStr = String.format(Locale.getDefault(),"%.1f", appSize) + " MB";

                expectedDisplayed.add(new UserApp(packageName,appName,appSizeStr,""));
            }
        }


        if (!sorting.isAscending()) {
            Collections.reverse(expectedDisplayed);
        }

        if (!query.isEmpty()) {
            List<UserApp> filtered = new ArrayList<>();
            for (UserApp userApp : expectedDisplayed) {
                if (userApp.getName().toLowerCase().contains(query.toLowerCase())) {
                    filtered.add(userApp);
                }
            }

            expectedDisplayed = filtered;
        }

        return expectedDisplayed;
    }
}
