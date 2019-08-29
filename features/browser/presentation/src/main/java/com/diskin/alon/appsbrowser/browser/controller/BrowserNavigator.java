package com.diskin.alon.appsbrowser.browser.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Browser feature navigator contract.
 */
public interface BrowserNavigator {
    /**
     * Open a screen that displays app detail.
     *
     * @param packageName the package name of the app tho show.
     * @param fragment the browser screen fragment.
     */
    void openAppDetail(@NonNull Fragment fragment, @NonNull String packageName);
}
