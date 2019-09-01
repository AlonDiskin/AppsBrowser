package com.diskin.alon.appsbrowser.browser.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.model.UserApp;

import java.util.List;

/**
 * Browser screen view model contract.
 */
public interface BrowserViewModel {
    /**
     * Get {@link UserApp}s observable live data.
     */
    @NonNull
    LiveData<List<UserApp>> getUserApps();

    /**
     * Sorts the current {@link UserApp}s list.
     *
     * @param sorting user apps list sorting value.
     */
    void sortApps(@NonNull AppsSorting sorting);

    /**
     * Filters the {@link UserApp}s list.
     *
     * @param query search query by witch user apps list wil be filtered.
     */
    void searchApps(@NonNull String query);

    /**
     * Gets the {@link UserApp}s list current search filtering query.
     */
    @NonNull
    String getSearchQuery();

    /**
     * Gets the current sorting value of user apps list.
     */
    @Nullable
    AppsSorting getAppsSorting();
}
