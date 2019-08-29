package com.diskin.alon.appsbrowser.browser.model;

import androidx.annotation.Nullable;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSearch;
import com.diskin.alon.appsbrowser.common.presentation.ServiceRequest;

import java.util.List;

import io.reactivex.Observable;

/**
 * Data wrapper for apps search request data.
 */
public class SearchAppsRequest extends ServiceRequest<AppsSearch, Observable<List<UserApp>>> {
    /**
     * Creates a new {@link SearchAppsRequest} instance.
     *
     * @param requestParam {@link AppsSearch} instance containing needed data for performing the search.
     */
    public SearchAppsRequest(@Nullable AppsSearch requestParam) {
        super(requestParam);
    }

}
