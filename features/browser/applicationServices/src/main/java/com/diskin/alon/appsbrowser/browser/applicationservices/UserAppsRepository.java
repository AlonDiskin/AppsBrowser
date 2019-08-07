package com.diskin.alon.appsbrowser.browser.applicationservices;

import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * {@link UserAppEntity} repository contract.
 */
public interface UserAppsRepository {

    /**
     * Get an observable list of {@link UserAppEntity}s sorted by name
     * in ascending order.
     */
    Observable<List<UserAppEntity>> getUserAppsByName();
}
