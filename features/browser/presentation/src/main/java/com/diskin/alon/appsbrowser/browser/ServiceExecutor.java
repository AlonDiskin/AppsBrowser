package com.diskin.alon.appsbrowser.browser;

import androidx.annotation.NonNull;

public interface ServiceExecutor {

    <P,R> R execute(@NonNull ServiceRequest<P,R> request);
}
