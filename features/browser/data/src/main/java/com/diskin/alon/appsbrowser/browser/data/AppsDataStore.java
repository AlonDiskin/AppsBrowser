package com.diskin.alon.appsbrowser.browser.data;

import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 */
public interface AppsDataStore {
    /**
     * @return
     */
    Observable<List<UserAppEntity>> getAll();
}
