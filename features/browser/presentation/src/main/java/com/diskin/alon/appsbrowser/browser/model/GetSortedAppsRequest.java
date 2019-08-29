package com.diskin.alon.appsbrowser.browser.model;

import androidx.annotation.Nullable;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.common.presentation.ServiceRequest;

import java.util.List;

import io.reactivex.Observable;

/**
 * Data wrapper for sorted apps retrieval request data.
 */
public class GetSortedAppsRequest extends ServiceRequest<AppsSorting, Observable<List<UserApp>>> {
    /**
     * Creates a new {@link GetSortedAppsRequest} instance.
     *
     * @param requestParam {@link AppsSorting} instance containing sorting required data.
     */
    public GetSortedAppsRequest(@Nullable AppsSorting requestParam) {
        super(requestParam);
    }
}
