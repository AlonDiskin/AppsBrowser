package com.diskin.alon.appsbrowser.browser;

import androidx.annotation.Nullable;

import java.util.List;

import io.reactivex.Observable;

public class GetUserAppsRequest extends ServiceRequest<Void, Observable<List<UserApp>>>{

    public GetUserAppsRequest(@Nullable Void requestParam) {
        super(requestParam);
    }
}
