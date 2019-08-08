package com.diskin.alon.appsbrowser.browser.controller;

import androidx.annotation.NonNull;

/**
 * Browser feature navigator contract.
 */
public interface BrowserNavigator {

    /**
     * Open a screen that displays app detail.
     *
     * @param packageName the package name of the app tho show.
     */
    void openAppDetail(@NonNull String packageName);
}
