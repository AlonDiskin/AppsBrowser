package com.diskin.alon.appsbrowser.browser.ui;

import android.app.Activity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.diskin.alon.appsbrowser.browser.R;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting.SortingType;
import com.diskin.alon.appsbrowser.browser.controller.BrowserFragment;
import com.diskin.alon.appsbrowser.browser.controller.BrowserNavigator;
import com.diskin.alon.appsbrowser.browser.di.TestInjector;
import com.diskin.alon.appsbrowser.browser.model.QueriedApp;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModel;
import com.diskin.alon.appsbrowser.common.presentation.FragmentTestActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.diskin.alon.appsbrowser.browser.util.RecyclerViewMatcher.withRecyclerView;
import static com.diskin.alon.appsbrowser.browser.util.SpannableTextMatcher.withSpannableText;
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
    private List<UserApp> apps = Arrays.asList(
            new UserApp("facebook","45 MB", "fc", "file:///android_asset/facebookicon.png"),
            new UserApp("youtube","31 MB", "yt", "file:///android_asset/youtubeicon.png"),
            new UserApp("twitter","78.8 MB", "tw", "file:///android_asset/twittericon.jpeg"),
            new UserApp("whatsApp","24.6 MB", "wa", "file:///android_asset/whatsappicon.png"));

    @Before
    public void setUp() {
        // inject test app and fragment test with fragment mocked dependents
        TestInjector.inject(this);

        // stub mocked view model
        when(viewModel.getUserApps()).thenReturn(userAppsData);
        when(viewModel.getSorting()).thenReturn(appsSortingData);
        doAnswer(invocation -> {
            appsSortingData.postValue((AppsSorting) invocation.getArguments()[0]);
            return null;
        }).when(viewModel).sortApps(any(AppsSorting.class));
        doAnswer(invocation -> {
            when(viewModel.getSearchQuery()).thenReturn(invocation.getArgument(0));
            return null;
        }).when(viewModel).searchApps(any(String.class));
        viewModel.searchApps("");

        // launch fragment under test
        scenario = ActivityScenario.launch(FragmentTestActivity.class);
        scenario.onActivity(activity -> activity.setFragment(new BrowserFragment(),TAG));
    }

    @Test
    public void shouldDisplayAppsList_whenViewModelUpdated() {
        // Given a resumed fragment

        // When view model updates its user apps listing
        userAppsData.postValue(apps);

        // Then fragment should display updated apps list
        for (int i = 0;i < apps.size();i++) {
            UserApp app = apps.get(i);

            onView(withRecyclerView(R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withText(app.getName())),
                            hasDescendant(withText(app.getSize())))));
        }

        // When apps list changes
        List<UserApp> updatedApps = new ArrayList<>(apps);
        updatedApps.remove(0);
        updatedApps.remove(updatedApps.size() - 1);
        userAppsData.postValue(updatedApps);

        // Then fragment should display updated apps list
        for (int i = 0;i < updatedApps.size();i++) {
            UserApp app = updatedApps.get(i);

            onView(withRecyclerView(R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withText(app.getName())),
                            hasDescendant(withText(app.getSize())))));
        }
    }

    @Test
    public void shouldNavigateToAppDetail_whenAppEntryClicked() {
        // Given a resumed fragment with displayed user apps
        userAppsData.postValue(apps);

        for (int i = 0; i < apps.size();i++) {
            UserApp selectedApp = apps.get(i);

            // When user clicks on an user app view in shown layout
            onView(withId(R.id.userApps))
                    .perform(RecyclerViewActions.scrollToPosition(i))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i,click()));

            // Then navigator should open clicked app detail
            scenario.onActivity(activity -> {
                BrowserFragment fragment = (BrowserFragment) activity.getSupportFragmentManager()
                        .findFragmentByTag(TAG);

                //noinspection ConstantConditions
                verify(navigator).openAppDetail(eq(fragment),eq(selectedApp.getPackageName()));
            });
        }
    }

    @Test
    public void shouldSortAppsByNameInAscendingOrder_whenCreated() {
        // Given a resumed fragment with no prev state

        // Then ask view model to sort them by name in ascending order
        verify(viewModel).sortApps(eq(new AppsSorting(SortingType.NAME,true)));
    }

    @Test
    public void shouldSortAppsAccordingToSavedState_whenRecreated() {
        // Given a resumed fragment with no prev state (sort is selected as ascending by name)

        // When user selects sort by size
        onView(withId(R.id.action_sort))
                .perform(click());

        onView(withText(R.string.action_sort_by_size_title))
                .perform(click());

        // And fragment is recreated
        scenario.recreate();

        // Then fragment should ask view model to sort apps according to prev sorting state

        // get user apps 2 times per on create call
        verify(viewModel,times(2)).getUserApps();

        // sort with selected value 2 times: upon selection,and upon recreation
        verify(viewModel,times(2)).sortApps(eq(new AppsSorting(SortingType.SIZE,true)));
    }

    @Test
    public void shouldCheckSortMenuAscendingByName_whenCreated() {
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
    public void shouldCheckSortMenu_whenUserSelectSorting() {
        // Given a resumed fragment with default sort set

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

            assertThat(isSizeSortChecked,equalTo(true));
            assertThat(isAscendingChecked,equalTo(false));
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

    @Test
    public void shouldShowQueryHint_whenSearchViewExpanded() {
        // Given a resumed fragment

        // When user expand search view
        onView(withId(R.id.action_search))
                .perform(click());

        // Then search query should by displayed
        onView(withHint(R.string.search_hint))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldHighlightQueryInAppsName_whenDisplaySearchResults() {
        // Given a resumed fragment
        int color = ApplicationProvider.getApplicationContext()
                .getColor(R.color.colorAccent);
        SpannableString youtubeSpan = new SpannableString("Youtube");
        youtubeSpan.setSpan(new ForegroundColorSpan(color), 0, 4, 0);
        SpannableString twitterSpan = new SpannableString("Twitter");
        twitterSpan.setSpan(new ForegroundColorSpan(color), 0, 1, 0);
        SpannableString whatsAppSpan = new SpannableString("WhatsApp");
        whatsAppSpan.setSpan(new ForegroundColorSpan(color), 1, 4, 0);

        List<UserApp> queriedApps = Arrays.asList(
                new QueriedApp("","Youtube", "31 MB", "file:///android_asset/youtubeicon.png",youtubeSpan,""),
                new QueriedApp("","Twitter", "78.8 MB", "file:///android_asset/twittericon.jpeg",twitterSpan,""),
                new QueriedApp("","WhatsApp", "24.6 MB", "file:///android_asset/whatsappicon.png",whatsAppSpan,""));

        // When view model is updated with search results
        userAppsData.postValue(queriedApps);

        // Then fragment should display all results with search query highlighted in apps name
        for (int i = 0;i <queriedApps.size();i++) {
            QueriedApp queriedApp = (QueriedApp) queriedApps.get(i);

            // scroll to matched position
            onView(withId(R.id.userApps))
                    .perform(RecyclerViewActions.scrollToPosition(i));

            // verify position contains expected item
            onView(withRecyclerView(R.id.userApps).atPosition(i))
                    .check(matches(allOf(hasDescendant(withText(queriedApp.getName())),
                            hasDescendant(withSpannableText(queriedApp.getHighlightedName())))));
        }
    }

    @Test
    public void shouldSearchApps_whenSearchPerformed() {
        // Given a resumed fragment
        String query = "que";

        // When user performs a searchApps
        onView(withId(R.id.action_search))
                .perform(click());

        onView(withHint(R.string.search_hint))
                .perform(typeText(query));

        // Then fragment should searchApps view model with entered query
        verify(viewModel).searchApps(eq("q"));
        verify(viewModel).searchApps(eq("qu"));
        verify(viewModel).searchApps(eq("que"));
    }

    @Test
    public void shouldSearchAppsWithRestoredQuery_whenRecreated() {
        // Given a resumed fragment with no prev state
        String query = "query";

        // When user enters a query text
        onView(withId(R.id.action_search))
                .perform(click());

        onView(withHint(R.string.search_hint))
                .perform(typeText(query));

        // And fragment is recreated
        scenario.recreate();

        // Then fragment should ask view model to search apps with prev entered query
        // expected 2 invocations for whole query text: when last letter entered,
        // end when fragment recreated and restored from saved instance
        verify(viewModel,times(2)).searchApps(eq(query));
    }

    @Test
    public void shouldRestoreSearchViewQuery_whenRecreated() {
        // Given a resumed fragment with no prev state
        String query = "query";

        // When user enters a query text
        onView(withId(R.id.action_search))
                .perform(click());

        onView(withHint(R.string.search_hint))
                .perform(typeText(query));

        // And fragment is recreated
        scenario.onActivity(Activity::recreate);

        // Then search query should be restored and displayed in search view
        onView(withHint(R.string.search_hint))
                .check(matches(isDisplayed()));

        onView(withText(query))
                .check(matches(isDisplayed()));
    }
}
