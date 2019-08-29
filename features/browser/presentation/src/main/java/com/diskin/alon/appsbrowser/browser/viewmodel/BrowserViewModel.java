package com.diskin.alon.appsbrowser.browser.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.model.UserApp;

import java.util.List;

/**
 * Browser screen view model contract.
 */
public interface BrowserViewModel {
    /**
     * Get a {@link UserApp}s observable.
     */
    @NonNull
    LiveData<List<UserApp>> getUserApps();

    /**
     * Get a {@link AppsSorting} observable.
     */
    @NonNull
    LiveData<AppsSorting> getSorting();

    /**
     * Sorts the {@link UserApp}s list by given sort value,
     *
     * @param sorting sorting param.
     */
    void sortApps(@NonNull AppsSorting sorting);

    /**
     * Filters the {@link UserApp}s list by given query.
     *
     * @param query search query.
     */
    void searchApps(@NonNull String query);

    /**
     * Gets the {@link UserApp}s list current search filtering query.
     */
    @NonNull
    String getSearchQuery();
}
