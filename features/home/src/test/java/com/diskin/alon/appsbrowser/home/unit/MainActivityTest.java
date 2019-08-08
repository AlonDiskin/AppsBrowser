package com.diskin.alon.appsbrowser.home.unit;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.diskin.alon.appsbrowser.home.HomeNavigator;
import com.diskin.alon.appsbrowser.home.MainActivity;
import com.diskin.alon.appsbrowser.home.R;
import com.diskin.alon.appsbrowser.home.unit.di.TestApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import javax.inject.Inject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * {@link MainActivity} unit test class.
 */
@RunWith(AndroidJUnit4.class)
@Config(application = TestApp.class)
public class MainActivityTest {

    // System under test
    private ActivityScenario<MainActivity> scenario;

    // SUT mocked dependent components
    @Inject
    HomeNavigator navigator;

    @Before
    public void setUp() {
        // setup activity for testing
        scenario = ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void shouldDisplayAppNameInToolbar() {
        // Given a resumed activity

        // Then app name should be displayed as activity toolbar title
        scenario.onActivity(activity -> {
            Toolbar toolbar = activity.findViewById(R.id.toolbar);

            assertThat(toolbar.getTitle().toString(),
                    equalTo(ApplicationProvider.getApplicationContext().getString(R.string.app_name)));

            onView(withId(R.id.toolbar))
                    .check(matches(isDisplayed()));
        });
    }

    @Test
    public void shouldDisplayBrowserFeatureUi() {
        // Given a resumed activity

        // Then activity should ask navigator to open browser
        scenario.onActivity(activity -> {
            View navHost = activity.findViewById(R.id.nav_host_fragment);
            verify(navigator).openBrowser(eq(navHost));
        });
    }

    @Test
    public void shouldNavigateToSettings_whenUserClicksOnSettingsOptionItem() {
        // Given a resumed activity

        // When user clicks on settings option menu item
        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext());
        onView(withText(R.string.title_action_settings))
                .perform(click());

        // Then navigator should open settings
        scenario.onActivity(activity -> {
            View navHost = activity.findViewById(R.id.nav_host_fragment);
            verify(navigator).openSettings(navHost);
        });
    }

    @Test
    public void shouldRegisterToolbarToNavigatorUpdates() {
        // Given a resumed activity

        // Then activity should register its toolbar to be updated by navigator
        scenario.onActivity(activity -> {
            View navHost = activity.findViewById(R.id.nav_host_fragment);
            Toolbar toolbar = activity.findViewById(R.id.toolbar);

            verify(navigator).addToolbar(eq(toolbar),eq(navHost));
        });
    }
}
