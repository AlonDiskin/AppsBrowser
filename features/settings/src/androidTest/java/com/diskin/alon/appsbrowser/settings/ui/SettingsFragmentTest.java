package com.diskin.alon.appsbrowser.settings.ui;

import android.content.Context;
import android.view.Menu;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.diskin.alon.appsbrowser.settings.R;
import com.diskin.alon.appsbrowser.settings.SettingsFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.verify;

/**
 * {@link SettingsFragment} unit test.
 */
@RunWith(AndroidJUnit4.class)
public class SettingsFragmentTest {

    // System under test
    private FragmentScenario<SettingsFragment> scenario;

    // Global test data
    private Context context = ApplicationProvider.getApplicationContext();

    @Before
    public void setUp() {
        // launch fragment, in tes activity container with app compat theme,for pref dialog
        // inflation during test cases
        scenario = FragmentScenario.launchInContainer(SettingsFragment.class,
                null, R.style.Theme_AppCompat_DayNight_DarkActionBar,null);
    }

    @Test
    public void shouldShowThemePreference() {
        // Given a resumed fragment with configured theme pref
        String currentValue = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.theme_pref_key),
                        context.getString(R.string.theme_pref_default_value));
        String summary = currentValue.equals("0") ? "Light" : "Dark";

        // Then theme preference should be displayed with current pref in summary
        onView(withClassName(equalTo(RecyclerView.class.getName())))
                .perform(RecyclerViewActions.scrollTo(allOf(
                        hasDescendant(withText(R.string.theme_pref_title)),
                        hasDescendant(withText(summary)))))
                .check(matches(isCompletelyDisplayed()));
    }

    @Test
    public void shouldDisplayAppThemes_whenThemePrefOpened() {
        // Given a resumed fragment and configured theme pref
        String[] themeEntries = ApplicationProvider
                .getApplicationContext()
                .getResources()
                .getStringArray(R.array.theme_pref_entries);

        // When user clicks on theme pref
        onView(withClassName(equalTo(RecyclerView.class.getName())))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(R.string.theme_pref_title)),click()));

        // Theme pref entries should be shown
        for (String entry : themeEntries) {
            onView(withText(entry))
                    .inRoot(isDialog())
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void shouldDisplayThemeTitleInDialog_whenThemePrefOpened() {
        // Given a resumed fragment and configured theme pref

        // When user clicks on theme pref
        onView(withClassName(equalTo(RecyclerView.class.getName())))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(R.string.theme_pref_title)),click()));

        onView(withText(R.string.theme_pref_title))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldChangeAppTheme_whenNewThemeSelected() {
        // Test case fixture
        String currentThemeValue = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.theme_pref_key),
                        context.getString(R.string.theme_pref_default_value));
        String selectedThemeEntry = currentThemeValue.equals("0") ? "Dark" : "Light";
        int expectedNightMode = currentThemeValue.equals("0") ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        final FragmentActivity[] fragmentActivity = {null};

        scenario.onFragment(fragment -> fragmentActivity[0] = fragment.getActivity());

        // Given a resumed fragment and configured theme pref

        // When user selects a new theme
        onView(withClassName(equalTo(RecyclerView.class.getName())))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(R.string.theme_pref_title)),click()));

        onView(withText(selectedThemeEntry))
                .inRoot(isDialog())
                .perform(click());

        // Then fragment should change app visual theme
        assertThat(AppCompatDelegate.getDefaultNightMode(),equalTo(expectedNightMode));
    }

    @Test
    public void shouldClearMenu_whenResumed() {
        // Given a resumed fragment
        Menu menu = Mockito.mock(Menu.class);

        // Then fragment contributes to host activity menu
        scenario.onFragment(fragment -> {
            assertThat(fragment.hasOptionsMenu(),equalTo(true));
        });

        // When fragment is called to create its options menu items
        scenario.onFragment(fragment -> {
            fragment.onCreateOptionsMenu(menu,null);
        });

        // Then fragment should clear its menu from all items
        verify(menu).clear();
    }
}
