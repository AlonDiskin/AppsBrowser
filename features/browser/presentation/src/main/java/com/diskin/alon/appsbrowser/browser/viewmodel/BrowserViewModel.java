package com.diskin.alon.appsbrowser.browser.viewmodel;

import androidx.lifecycle.LiveData;

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
}
