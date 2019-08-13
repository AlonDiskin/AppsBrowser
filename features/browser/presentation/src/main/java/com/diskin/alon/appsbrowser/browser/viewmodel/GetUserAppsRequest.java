package com.diskin.alon.appsbrowser.browser.viewmodel;

import androidx.annotation.Nullable;

import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.common.presentation.ServiceRequest;

import java.util.List;

import io.reactivex.Observable;

public class GetUserAppsRequest extends ServiceRequest<Void, Observable<List<UserApp>>> {

    public GetUserAppsRequest(@Nullable Void requestParam) {
        super(requestParam);
    }
}
