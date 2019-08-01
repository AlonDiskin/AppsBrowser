package com.diskin.alon.appsbrowser.browser.unit;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.diskin.alon.appsbrowser.browser.BrowserFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.fail;

/**
 * {@link BrowserFragment} unit test class.
 */
@RunWith(AndroidJUnit4.class)
//@Config(application = TestApp.class)
public class BrowserFragmentTest {
    // System under test
    private FragmentScenario<BrowserFragment> scenario;

    @Before
    public void setUp() {
        scenario = FragmentScenario.launch(BrowserFragment.class);
    }

    @Test
    public void shouldGetAppsFromViewModel() {
        fail("not implemented yet");
    }

    @Test
    public void shouldDisplayAppsUponViewModelUpdate() {
        fail("not implemented yet");
    }
}
