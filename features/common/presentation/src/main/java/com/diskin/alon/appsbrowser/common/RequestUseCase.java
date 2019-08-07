package com.diskin.alon.appsbrowser.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;
import com.diskin.alon.appsbrowser.common.applicationservices.UseCase;

public class RequestUseCase<P,R,S> {

    @NonNull
    private final UseCase<P,R> useCase;
    @Nullable
    private final Mapper<R,S> mapper;

    public RequestUseCase(@NonNull UseCase<P, R> useCase, @Nullable Mapper<R, S> mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @NonNull
    public UseCase<P, R> getUseCase() {
        return useCase;
    }

    @Nullable
    public Mapper<R, S> getMapper() {
        return mapper;
    }

    public boolean isMapped() {
        return mapper != null;
    }
}
