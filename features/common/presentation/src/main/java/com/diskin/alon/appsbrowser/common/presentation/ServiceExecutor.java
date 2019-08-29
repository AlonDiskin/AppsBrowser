package com.diskin.alon.appsbrowser.common.presentation;

import androidx.annotation.NonNull;

/**
 * Application services executor contract.
 */
public interface ServiceExecutor {
    /**
     * Executes a service tha serves the given request.
     *
     * @param request service request holding to execution input data.
     * @param <P> request input param data type
     * @param <R> request result data type
     * @return the desired result executed by appropriate service.
     */
    <P,R> R execute(@NonNull ServiceRequest<P,R> request);
}
