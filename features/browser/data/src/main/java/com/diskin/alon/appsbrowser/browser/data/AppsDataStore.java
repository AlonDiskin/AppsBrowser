package com.diskin.alon.appsbrowser.browser.data;

import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Device applications data store.
 */
public interface AppsDataStore {
    /**
     * Get a device user apps observable.
     */
    Observable<List<UserAppEntity>> getAll();
}
