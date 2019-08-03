package com.diskin.alon.appsbrowser.browser;

import androidx.annotation.Nullable;

public abstract class ServiceRequest<P,R> {

    @Nullable
    private final P requestParam;

    protected ServiceRequest(@Nullable P requestParam) {
        this.requestParam = requestParam;
    }

    @Nullable
    public P getRequestParam() {
        return requestParam;
    }
}
