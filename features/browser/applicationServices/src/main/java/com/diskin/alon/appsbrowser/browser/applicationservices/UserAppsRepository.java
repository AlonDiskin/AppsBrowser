package com.diskin.alon.appsbrowser.browser.applicationservices;

import com.diskin.alon.appsbrowser.browser.domain.UserApp;

import java.util.List;

import io.reactivex.Observable;

/**
 * {@link UserApp} repository contract.
 */
public interface UserAppsRepository {

    /**
     * Get an observable list of {@link UserApp}s sorted by name
     * in ascending order.
     */
    Observable<List<UserApp>> getUserAppsByName();
}
