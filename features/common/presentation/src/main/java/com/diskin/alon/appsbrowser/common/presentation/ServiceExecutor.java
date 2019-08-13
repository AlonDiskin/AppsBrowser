package com.diskin.alon.appsbrowser.common.presentation;

import androidx.annotation.NonNull;

/**
 * Application services executor contract.
 */
public interface ServiceExecutor {

    <P,R> R execute(@NonNull ServiceRequest<P,R> request);
}
