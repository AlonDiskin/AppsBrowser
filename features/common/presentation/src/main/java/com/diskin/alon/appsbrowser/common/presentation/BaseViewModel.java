package com.diskin.alon.appsbrowser.common.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

/**
 * Base {@link ViewModel} that holds a {@link ServiceExecutor}
 * to handle any service requests from child classes.
 */
public abstract class BaseViewModel extends ViewModel {
    @NonNull
    private final ServiceExecutor serviceExecutor;

    /**
     * Instantiates {@link BaseViewModel} base state.
     *
     * @param serviceExecutor an implementation of {@link ServiceExecutor} to handle the view models
     * service requests.
     */
    protected BaseViewModel(@NonNull ServiceExecutor serviceExecutor) {
        this.serviceExecutor = serviceExecutor;
    }

    /**
     * Passes the given request to {@link ServiceExecutor} for execution.
     *
     * @param request the service request to be executed.
     * @param <P> request input params data type.
     * @param <R> request result data type.
     * @return service execution result of type R.
     */
    protected <P,R> R executeService(@NonNull ServiceRequest<P,R> request) {
        return serviceExecutor.execute(request);
    }
}
