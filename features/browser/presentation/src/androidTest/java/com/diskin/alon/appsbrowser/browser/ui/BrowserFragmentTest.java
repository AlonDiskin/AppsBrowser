package com.diskin.alon.appsbrowser.browser.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.diskin.alon.appsbrowser.browser.R;
import com.diskin.alon.appsbrowser.browser.controller.BrowserFragment;
import com.diskin.alon.appsbrowser.browser.controller.BrowserNavigator;
import com.diskin.alon.appsbrowser.browser.di.TestInjector;
import com.diskin.alon.appsbrowser.browser.applicationservices.AppsSorting;
import com.diskin.alon.appsbrowser.browser.applicationservices.AppsSorting.SortingType;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.browser.util.RecyclerViewMatcher;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModel;
import com.diskin.alon.appsbrowser.common.presentation.FragmentTestActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link BrowserFragment} hermetic ui integration test class.
 */
@RunWith(AndroidJUnit4.class)
public class BrowserFragmentTest {

    private static final String TAG = "BrowserFragmentTest";

    // Fragment under test test host
    private ActivityScenario<FragmentTestActivity> scenario;

    // SUT mocked dependent on components
    @Inject
    BrowserViewModel viewModel;
    @Inject
    BrowserNavigator navigator;

    // Test stubs
    private MutableLiveData<List<UserApp>> userAppsData = new MutableLiveData<>();
    private MutableLiveData<AppsSorting> appsSortingData = new MutableLiveData<>();

    @Before
    public void setUp() {
        // inject test app and fragment test with fragment mocked dependents
        TestInjector.inject(this);

        // stub mocked view model
        when(viewModel.getUserApps()).thenReturn(userAppsData);
        when(viewModel.getSorting()).thenReturn(appsSortingData);
        doAnswer(invocation -> {
            runOnMainThread(() -> appsSortingData.setValue((AppsSorting) invocation.getArguments()[0]));
            return null;
        }).when(viewModel).sortApps(any(AppsSorting.class));

        // launch fragment under test
        scenario = ActivityScenario.launch(FragmentTestActivity.class);
        scenario.onActivity(activity -> activity.setFragment(new BrowserFragment(),TAG));
    }

    @Test
    public void shouldDisplayApps_whenViewModelUpdated() {
        List<UserApp> apps = Arrays.asList(
                new UserApp("facebook","45 MB", "fc", "file:///android_asset/facebookicon.png"),
                new UserApp("youtube","31 MB", "yt", "file:///android_asset/youtubeicon.png"),
                new UserApp("twitter","78.8 MB", "tw", "file:///android_asset/twittericon.jpeg"),
                new UserApp("whatsApp","24.6 MB", "wa", "file:///android_asset/whatsappicon.png"));

        // Given a resumed fragment

        // When view model updates its user apps listing
        runOnMainThread(() -> userAppsData.setValue(apps));

        // Then fragment should display updated apps list

        for (int i = 0;i < apps.size();i++) {
            UserApp app = apps.get(i);

            onView(RecyclerViewMatcher.withRecyclerView(R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withText(app.getName())),
                            hasDescendant(withText(app.getSize())))));
        }
    }

    @Test
    public void shouldNavigateToAppDetail_whenAppEntryClicked() {
        // Given a resumed fragment with displayed user apps
        List<UserApp> apps = Arrays.asList(
                new UserApp("facebook","45 MB", "fc", "file:///android_asset/facebookicon.png"),
                new UserApp("youtube","31 MB", "yt", "file:///android_asset/youtubeicon.png"),
                new UserApp("twitter","78.8 MB", "tw", "file:///android_asset/twittericon.jpeg"),
                new UserApp("whatsApp","24.6 MB", "wa", "file:///android_asset/whatsappicon.png"));

        runOnMainThread(() -> userAppsData.setValue(apps));

        for (int i = 0; i < apps.size();i++) {
            UserApp selectedApp = apps.get(i);

            // When user clicks on an user app view in shown layout
            onView(withId(R.id.userApps))
                    .perform(RecyclerViewActions.scrollToPosition(i))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i,click()));

            // Then navigator should open clicked app detail
            verify(navigator).openAppDetail(eq(selectedApp.getPackageName()));
        }
    }

    @Test
    public void shouldFetchAscendingAppsSortedByName_whenResumedWithNoState() {
        // Given a resumed fragment with no prev state

        // Then fragment should fetch observable listing of user apps
        verify(viewModel).getUserApps();
        // And sort them by name in ascending order
        verify(viewModel).sortApps(eq(new AppsSorting(SortingType.NAME,true)));
    }

    @Test
    public void shouldDisplaySortingMenuAsAscendingSortedByName_whenResumedWithNoState() {
        // Given a resumed fragment with no prev state

        // When user opens the sorting menu
        onView(withId(R.id.action_sort))
                .perform(click());

        // Then sort by name in ascending order options should be selected
        scenario.onActivity(activity -> {
            Toolbar toolbar = activity.findViewById(R.id.toolbar);
            boolean isNameSortChecked = toolbar.getMenu()
                    .findItem(R.id.action_sort_by_name).isChecked();
            boolean isAscendingChecked = toolbar.getMenu()
                    .findItem(R.id.action_ascending).isChecked();

            assertThat(isNameSortChecked,equalTo(true));
            assertThat(isAscendingChecked,equalTo(true));
        });
    }

    @Test
    public void shouldChangeSortingMenu_whenUserSelectsSorting() {
        // Given a resumed fragment with no prev state (sort is selected as ascending by name)

        // When user selects sort type from menu
        onView(withId(R.id.action_sort))
                .perform(click());

        onView(withText(R.string.action_sort_by_size_title))
                .perform(click());

        // And select descending order
        onView(withId(R.id.action_sort))
                .perform(click());

        onView(withText(R.string.action_ascending_title))
                .perform(click());

        // Then fragment should change sorting menu according to user selections
        scenario.onActivity(activity -> {
            Toolbar toolbar = activity.findViewById(R.id.toolbar);
            boolean isSizeSortChecked = toolbar.getMenu()
                    .findItem(R.id.action_sort_by_size).isChecked();
            boolean isAscendingChecked = toolbar.getMenu()
                    .findItem(R.id.action_ascending).isChecked();

            assertThat("size sort should be checked",isSizeSortChecked,equalTo(true));
            assertThat("sort order should be descending",isAscendingChecked,equalTo(false));
        });
    }

    @Test
    public void shouldNotPassSortingToViewModel_whenUserSelectsSameSortingType() {
        // Given a resumed fragment with no prev state (sort is selected as ascending by name)

        // When user re selects sort by name
        onView(withId(R.id.action_sort))
                .perform(click());

        onView(withText(R.string.action_sort_by_name_title))
                .perform(click());

        // Then view model should not receive a request to sort by name
        verify(viewModel).sortApps(any(AppsSorting.class));
    }

    @Test
    public void shouldPassSortingToViewModel_whenUserSelectsDifferentSorting() {
        // Given a resumed fragment with no prev state (sort is selected as ascending by name)

        // When user selects sort by size
        onView(withId(R.id.action_sort))
                .perform(click());

        onView(withText(R.string.action_sort_by_size_title))
                .perform(click());

        // Then view model should receive requests to re sort and reorder as selected
        verify(viewModel).sortApps(eq(new AppsSorting(SortingType.SIZE,true)));
    }

    @Test
    public void shouldFetchAppsAccordingToSavedState_whenRecreated() {
        // Given a resumed fragment with no prev state (sort is selected as ascending by name)

        // When user selects sort by size
        onView(withId(R.id.action_sort))
                .perform(click());

        onView(withText(R.string.action_sort_by_size_title))
                .perform(click());

        // And fragment is recreated
        scenario.recreate();

        // Then fragment should fetch apps according to prev sorting state

        // get user apps 2 times per on create call
        verify(viewModel,times(2)).getUserApps();

        // sort with selected value 2 times: upon selection,and upon recreation
        verify(viewModel,times(2)).sortApps(eq(new AppsSorting(SortingType.SIZE,true)));
    }

    @Test
    public void shouldDisplaySortingMenuAccordingToSavedState_whenRecreated() {
        // Given a resumed fragment with no prev state (sort is selected as ascending by name)

        // When user selects sort by size
        onView(withId(R.id.action_sort))
                .perform(click());

        onView(withText(R.string.action_sort_by_size_title))
                .perform(click());

        // And select descending order
        onView(withId(R.id.action_sort))
                .perform(click());

        onView(withText(R.string.action_ascending_title))
                .perform(click());

        // And fragment is recreated
        scenario.recreate();

        // Then fragment should display sorting menu according to prev sorting state
        scenario.onActivity(activity -> {
            Toolbar toolbar = activity.findViewById(R.id.toolbar);
            boolean isSizeSortChecked = toolbar.getMenu()
                    .findItem(R.id.action_sort_by_size).isChecked();
            boolean isAscendingChecked = toolbar.getMenu()
                    .findItem(R.id.action_ascending).isChecked();

            assertThat("size sort should be checked",isSizeSortChecked,equalTo(true));
            assertThat("sort order should be descending",isAscendingChecked,equalTo(false));
        });
    }

    private void runOnMainThread(Runnable runnable) {
        scenario.onActivity(activity -> activity.runOnUiThread(runnable));
    }
}
