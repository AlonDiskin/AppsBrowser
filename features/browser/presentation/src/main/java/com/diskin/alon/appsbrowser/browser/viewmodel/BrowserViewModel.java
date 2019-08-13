package com.diskin.alon.appsbrowser.browser.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.diskin.alon.appsbrowser.browser.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.model.UserApp;

import java.util.List;

/**
 * Browser screen view model contract.
 */
public interface BrowserViewModel {

    /**
     * Get an observable list of all existing user apps installed on his device.
     */
    LiveData<List<UserApp>> getUserApps();

    /**
     * Get an observable list of all existing user apps installed on his device.
     *
     * @param sorting apps sorting type.
     * @param ascending whether apps should be sorted in ascending order.
     */
    @NonNull
    LiveData<List<UserApp>> getUserApps(@NonNull AppsSorting sorting, boolean ascending);

    @NonNull
    LiveData<AppsSorting> getAppsSorting();

    @NonNull
    LiveData<Boolean> getAscending();

    void sortApps(@NonNull AppsSorting sorting);

    void orderApps(boolean ascending);
}
