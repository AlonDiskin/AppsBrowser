package com.diskin.alon.appsbrowser.home;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

/**
 * Home screen navigator
 */
public interface HomeNavigator {
    /**
     * Sets the navigation graph for home screen.
     *
     * @param navView navigation host view in home screen.
     */
    void setGraph(@NonNull View navView);

    /**
     * Opens the settings feature screen.
     *
     * @param navView navigation host view in home screen.
     */
    void openSettings(@NonNull View navView);

    /**
     * Add a toolbar instance to be updated when navigation in home screen performed.
     *
     * @param toolbar the toolbar to receive the updates.
     * @param navView navigation host view in home screen.
     */
    void setToolbarUpdates(@NonNull MainActivity activity, @NonNull Toolbar toolbar, @NonNull View navView);
}
