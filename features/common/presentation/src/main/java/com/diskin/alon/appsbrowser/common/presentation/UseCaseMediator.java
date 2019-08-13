package com.diskin.alon.appsbrowser.common.presentation;

import androidx.annotation.NonNull;

import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;
import com.diskin.alon.appsbrowser.common.applicationservices.UseCase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Mediator pattern implementation of {@link ServiceExecutor}.
 */
public class UseCaseMediator implements ServiceExecutor {

    private static final String TAG = "com.diskin.alon.appsbrowser.common.presentation.UseCaseMediator";
    private static final String REQUEST_ERROR = "unknown service execution request";

    @NonNull
    private final Map<Class<? extends ServiceRequest>, RequestUseCase> dispatcher
            = new HashMap<>();

    public <P,R> void addUseCase(@NonNull Class<? extends ServiceRequest<P,R>> requestClass, @NonNull UseCase<P,R> useCase) {
        Objects.requireNonNull(requestClass);
        Objects.requireNonNull(useCase);
        dispatcher.put(requestClass,new RequestUseCase<>(useCase,null));
    }

    public <P,R,M> void addMappedUseCase(@NonNull Class<? extends ServiceRequest<P,M>> requestClass,
                                         @NonNull UseCase<P,R> useCase,
                                         @NonNull Mapper<R,M> mapper) {
        Objects.requireNonNull(requestClass);
        Objects.requireNonNull(useCase);
        Objects.requireNonNull(mapper);
        dispatcher.put(requestClass,new RequestUseCase<>(useCase,mapper));
    }

    @Override
    public <P,R> R execute(@NonNull ServiceRequest<P, R> request) {
        if (!dispatcher.containsKey(request.getClass())) {
            throw new IllegalArgumentException(REQUEST_ERROR);

        } else {
            //noinspection ConstantConditions
            if (dispatcher.get(request.getClass()).isMapped()) {
                return executeMappedUseCase(request);

            } else {
                return executeUseCase(request);
            }
        }
    }

    private <P,S,R> R executeMappedUseCase(@NonNull ServiceRequest<P, R> request) {
        //noinspection unchecked,ConstantConditions
        UseCase<P,S> useCase = dispatcher.get(request.getClass()).getUseCase();
        //noinspection unchecked,ConstantConditions
        Mapper<S,R> mapper = dispatcher.get(request.getClass()).getMapper();

        //noinspection ConstantConditions
        return mapper.map(useCase.execute(request.getRequestParam()));
    }

    private <P,R> R executeUseCase(@NonNull ServiceRequest<P, R> request) {
        //noinspection unchecked,ConstantConditions
        UseCase<P,R> useCase = dispatcher.get(request.getClass()).getUseCase();

        return useCase.execute(request.getRequestParam());
    }
}
