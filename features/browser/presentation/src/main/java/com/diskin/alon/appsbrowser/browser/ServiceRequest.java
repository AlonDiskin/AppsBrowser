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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceRequest<?, ?> that = (ServiceRequest<?, ?>) o;

        return requestParam != null ? requestParam.equals(that.requestParam) : that.requestParam == null;
    }

    @Override
    public int hashCode() {
        return requestParam != null ? requestParam.hashCode() : 0;
    }
}
