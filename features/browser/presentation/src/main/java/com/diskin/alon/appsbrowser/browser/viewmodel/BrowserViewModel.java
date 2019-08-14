package com.diskin.alon.appsbrowser.browser.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.diskin.alon.appsbrowser.browser.applicationservices.AppsSorting;
import com.diskin.alon.appsbrowser.browser.model.UserApp;

import java.util.List;

/**
 * Browser screen view model contract.
 */
public interface BrowserViewModel {

    @NonNull
    LiveData<List<UserApp>> getUserApps();

    @NonNull
    LiveData<AppsSorting> getSorting();

    void sortApps(@NonNull AppsSorting sorting);
}
