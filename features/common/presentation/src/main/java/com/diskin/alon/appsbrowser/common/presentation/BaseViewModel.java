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
     * Initialize {@link BaseViewModel} state.
     *
     * @param serviceExecutor an implementation of {@link ServiceExecutor} that will handle view models
     * service requests.
     */
    protected BaseViewModel(@NonNull ServiceExecutor serviceExecutor) {
        this.serviceExecutor = serviceExecutor;
    }

    /**
     * Serves given request to via view models {@link ServiceExecutor}.
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
