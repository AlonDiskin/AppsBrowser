package com.diskin.alon.appsbrowser.common.presentation;

import androidx.annotation.Nullable;

/**
 * Base class for service requests instances tha hold info needed to execute a service that
 * deliver the specified desired result.
 *
 * @param <P> service input data type.
 * @param <R> service execution result data type.
 */
public abstract class ServiceRequest<P,R> {
    @Nullable
    private final P requestParam;

    /**
     * Initialize {@link ServiceRequest}.
     *
     * @param requestParam service request input data.
     */
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

    @Override
    public String toString() {
        return "ServiceRequest{" +
                "requestParam=" + requestParam +
                '}';
    }
}
