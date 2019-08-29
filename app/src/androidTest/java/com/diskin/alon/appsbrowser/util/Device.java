package com.diskin.alon.appsbrowser.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 *
 */
public class Device {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String APP_PACKAGE = "com.diskin.alon.appsbrowser";

    /**
     *
     */
    public static void openHomeScreen() {
        // Start from the home screen
        UiDevice.getInstance(getInstrumentation()).pressHome();
    }

    /**
     *
     */
    public static void launchApp() {
        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        UiDevice.getInstance(getInstrumentation()).wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(APP_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        UiDevice.getInstance(getInstrumentation()).wait(Until.hasObject(By.pkg(APP_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private static String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = getInstrumentation().getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }

    public static void pressBack() {
        UiDevice.getInstance(getInstrumentation()).pressBack();
    }

    public static void removeFromRecents() throws RemoteException, UiObjectNotFoundException {
        UiDevice.getInstance(getInstrumentation()).pressRecentApps();
        //app = uiDevice.findObject(new UiSelector().textContains("SDK Test App"));
        UiObject closeAllButton = UiDevice.getInstance(getInstrumentation()).findObject(new UiSelector().textContains("Close"));
        closeAllButton.click();
    }
}
