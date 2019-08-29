package com.diskin.alon.appsbrowser.browser.applicationservices.interfaces;

import androidx.annotation.NonNull;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * {@link UserAppEntity} repository contract.
 */
public interface UserAppsRepository {
    /**
     * Get an observable list of {@link UserAppEntity}s sorted by given sorting values.
     */
    Observable<List<UserAppEntity>> getSortedApps(@NonNull AppsSorting sorting);

    /**
     * Search for apps matching given query.
     *
     * @param sorting sorting param for returned results
     * @param query search query
     * @return observable list of matching apps.
     */
    Observable<List<UserAppEntity>> search(@NonNull AppsSorting sorting, @NonNull String query);
}
