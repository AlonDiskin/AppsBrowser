package com.diskin.alon.appsbrowser.common;

import android.util.Pair;

import androidx.annotation.NonNull;

import java.util.Map;

/**
 * Mediator pattern implementation of {@link ServiceExecutor}.
 */
public class UseCaseMediator implements ServiceExecutor {

    private Map<Class<? extends ServiceRequest>, Pair<UseCase, Mapper>> dispatcher;

    @Override
    public <P, R> R execute(@NonNull ServiceRequest<P, R> request) {
        return null;
    }
}
