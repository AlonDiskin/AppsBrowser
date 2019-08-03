package com.diskin.alon.appsbrowser.browser.ui;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.diskin.alon.appsbrowser.browser.BrowserFragment;
import com.diskin.alon.appsbrowser.browser.BrowserViewModel;
import com.diskin.alon.appsbrowser.browser.R;
import com.diskin.alon.appsbrowser.browser.UserApp;
import com.diskin.alon.appsbrowser.browser.ui.di.UiTestInjector;
import com.diskin.alon.appsbrowser.browser.util.RecyclerViewMatcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link BrowserFragment} ui integration test class.
 */
@RunWith(AndroidJUnit4.class)
public class BrowserFragmentTest {
    // System under test
    private FragmentScenario<BrowserFragment> scenario;

    // SUT mocked dependent on components
    @Inject
    BrowserViewModel viewModel;

    // Test stubs
    private MutableLiveData<List<UserApp>> userAppsData;

    @Before
    public void setUp() {
        // inject test app and fragment test with fragment mocked dependents
        UiTestInjector.inject(this);

        // stub mocked view model
        userAppsData = new MutableLiveData<>();

        when(viewModel.getUserApps()).thenReturn(userAppsData);

        // launch fragment under test
        scenario = FragmentScenario.launchInContainer(BrowserFragment.class,
                null, R.style.AppTheme,null);
    }

    @Test
    public void shouldGetAppsFromViewModel() {
        // Given a resumed fragment

        // Then fragment should have asked its view model for user apps
        verify(viewModel).getUserApps();
    }

    @Test
    public void shouldDisplayAppsUponViewModelUpdate() {
        List<UserApp> apps = Arrays.asList(new UserApp("facebook","45", "fc", ""),
                new UserApp("youtube","31", "yt", ""),
                new UserApp("twitter","78.8", "tw", ""),
                new UserApp("whatsApp","24.6", "wa", ""));

        // Given a resumed fragment

        // When view model updates its user apps listing
        //scenario.onFragment(fragment -> fragment.getActivity().runOnUiThread())
        userAppsData.postValue(apps);

        // Then fragment should display updated apps list

        for (int i = 0;i < apps.size();i++) {
            UserApp app = apps.get(i);

            onView(RecyclerViewMatcher.withRecyclerView(R.id.userApps).atPosition(i))
                    .check(matches(allOf(
                            hasDescendant(withText(app.getName())),
                            hasDescendant(withText(app.getSize())))));
        }
    }
}
