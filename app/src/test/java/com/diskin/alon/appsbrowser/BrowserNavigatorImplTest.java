package com.diskin.alon.appsbrowser;

import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.Fragment;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

/**
 * {@link BrowserNavigatorImpl} unit test class.
 */
@RunWith(AndroidJUnit4.class)
public class BrowserNavigatorImplTest {
    
    // System under test 
    private BrowserNavigatorImpl navigator;

    @Captor
    public ArgumentCaptor<Intent> intentArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        navigator = new BrowserNavigatorImpl();
    }

    @Test
    public void shouldOpenSettingsApp_whenNavigatingToAppDetail() {
        String pcg = "package";
        Fragment fragment = Mockito.mock(Fragment.class);

        // Given an initialized navigator

        // When navigator is asked to open app detail screen
        navigator.openAppDetail(fragment,pcg);

        // Then navigator should redirect user to settings application app detail screen
        verify(fragment).startActivity(intentArgumentCaptor.capture());

        assertThat(intentArgumentCaptor.getValue().getAction(),equalTo(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS));
        assertThat(intentArgumentCaptor.getValue().getData(),equalTo(Uri.parse("package:" + pcg)));
    }
}
