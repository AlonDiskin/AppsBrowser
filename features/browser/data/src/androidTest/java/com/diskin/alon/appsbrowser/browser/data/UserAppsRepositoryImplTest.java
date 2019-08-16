package com.diskin.alon.appsbrowser.browser.data;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.diskin.alon.appsbrowser.browser.applicationservices.AppsSorting;
import com.diskin.alon.appsbrowser.browser.applicationservices.AppsSorting.SortingType;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * {@link UserAppsRepositoryImpl} integration test class.
 */
@RunWith(AndroidJUnit4.class)
public class UserAppsRepositoryImplTest {

    private static final String TAG = "UserAppsRepoImplTest";

    // System under test
    private UserAppsRepositoryImpl repository;

    @Before
    public void setUp() {
        repository = new UserAppsRepositoryImpl(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void shouldFetchSortedApps() {
        List<AppsSorting> sortingParams = Arrays.asList(
                new AppsSorting(SortingType.SIZE,false),
                new AppsSorting(SortingType.NAME,true));

        for (AppsSorting sorting : sortingParams) {
            List<UserAppEntity> userApps = repository.getUserApps(sorting).blockingFirst();

            assertThat(userApps,equalTo(expectedApps(sorting)));
        }
    }

    private static List<UserAppEntity> expectedApps(AppsSorting sorting) {
        PackageManager pm = ApplicationProvider.getApplicationContext().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<UserAppEntity> userApps = new ArrayList<>(packages.size());

        for (ApplicationInfo appInfo : packages) {
            // get info for non system apps only
            if( pm.getLaunchIntentForPackage(appInfo.packageName) != null ){
                // extract installed app information
                String appId = appInfo.packageName;
                String appName = pm.getApplicationLabel(appInfo).toString();
                File file = new File(appInfo.publicSourceDir);
                double appSize = Long.valueOf(file.length()).doubleValue() / (1024 * 1024);
                Uri uri = Uri.parse("android.resource://" + appInfo.packageName + "/"
                        + appInfo.icon);

                // add to user apps result list
                userApps.add(new UserAppEntity(appId,
                        appName,
                        appSize,
                        uri.toString()));
            }
        }

        switch (sorting.getType()) {
            case NAME:
                Collections.sort(userApps,(o1, o2) -> o1.getName().compareTo(o2.getName()));
                break;

            case SIZE:
                Collections.sort(userApps,(o1, o2) -> Double.compare(o1.getSize(),o2.getSize()));
                break;

            default:
                break;
        }

        if (!sorting.isAscending()) {
            Collections.reverse(userApps);
        }

        return userApps;
    }
}
