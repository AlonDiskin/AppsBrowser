package com.diskin.alon.appsbrowser.browser.model;

import androidx.annotation.Nullable;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSearch;
import com.diskin.alon.appsbrowser.common.presentation.ServiceRequest;

import java.util.List;

import io.reactivex.Observable;

public class SearchAppsRequest extends ServiceRequest<AppsSearch, Observable<List<UserApp>>> {
    public SearchAppsRequest(@Nullable AppsSearch requestParam) {
        super(requestParam);
    }

}
