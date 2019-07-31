package com.diskin.alon.appsbrowser.home;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * Home screen navigator
 */
public interface HomeNavigator {

    /**
     * Opens the browser screen.
     *
     * @param navView
     */
    void openBrowser(@NonNull View navView);
}
