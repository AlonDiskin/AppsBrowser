package com.diskin.alon.appsbrowser.browser.model;

import androidx.annotation.Nullable;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.common.presentation.ServiceRequest;

import java.util.List;

import io.reactivex.Observable;

public class GetSortedAppsRequest extends ServiceRequest<AppsSorting, Observable<List<UserApp>>> {
    public GetSortedAppsRequest(@Nullable AppsSorting requestParam) {
        super(requestParam);
    }
}
