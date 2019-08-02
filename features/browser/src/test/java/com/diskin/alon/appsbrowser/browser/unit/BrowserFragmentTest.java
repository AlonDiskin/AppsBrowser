package com.diskin.alon.appsbrowser.browser.unit;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.diskin.alon.appsbrowser.browser.BrowserFragment;
import com.diskin.alon.appsbrowser.browser.BrowserViewModel;
import com.diskin.alon.appsbrowser.browser.UserApp;
import com.diskin.alon.appsbrowser.browser.unit.di.TestApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link BrowserFragment} unit test class.
 */
@RunWith(AndroidJUnit4.class)
@Config(application = TestApp.class)
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
        // stub mocked view model
        userAppsData = new MutableLiveData<>();

        when(viewModel.getUserApps()).thenReturn(userAppsData);

        // launch fragment under test
        scenario = FragmentScenario.launch(BrowserFragment.class);
    }

    @Test
    public void shouldGetAppsFromViewModel() {
        // Given a resumed fragment


        // Then fragment should have asked its view model for user apps
        verify(viewModel).getUserApps();
    }

    @Test
    public void shouldDisplayAppsUponViewModelUpdate() {
        List<UserApp> apps = Arrays.asList(new UserApp("facebook","45",""),
                new UserApp("youtube","31",""),
                new UserApp("twitter","78.8",""),
                new UserApp("whatsApp","24.6",""));


        // Given a resumed fragment

        // When view model updates its user apps listing



        // Then fragment should display updated apps list

        fail("not implemented yet");
    }
}
