package com.diskin.alon.appsbrowser.browser.data;

import android.app.Application;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * {@link UserAppsRepositoryImpl} integration test class.
 */
@RunWith(AndroidJUnit4.class)
public class UserAppsRepositoryImplTest {

    private static final String TAG = "UserAppsRepoImplTest";

    private UserAppsRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        repository = new UserAppsRepositoryImpl((Application) ApplicationProvider.getApplicationContext());
    }

    @Test
    public void name() {
        List<UserAppEntity> userApps = repository.getUserAppsByName().blockingFirst();

        Log.d(TAG, "name: " + userApps.size());
        for (UserAppEntity userApp : userApps) {
            Log.d(TAG, userApp.toString());
        }

    }
}
