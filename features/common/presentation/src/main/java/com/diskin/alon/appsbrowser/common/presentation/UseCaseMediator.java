package com.diskin.alon.appsbrowser.common.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;
import com.diskin.alon.appsbrowser.common.applicationservices.UseCase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@link ServiceExecutor} implementation via mediator pattern.
 */
public class UseCaseMediator implements ServiceExecutor {
    private static final String REQUEST_ERROR = "unknown service execution request";

    @NonNull
    private final Map<Class<? extends ServiceRequest>, RequestUseCase> dispatcher
            = new HashMap<>();

    /**
     * Makes a{@link UseCase} known to this mediator.
     *
     * @param requestClass request class type served by use case.
     * @param useCase the use case to be invoked upon incoming requests.
     * @param <P> use case input data type .
     * @param <R> use case execution result data type.
     */
    public <P,R> void addUseCase(@NonNull Class<? extends ServiceRequest<P,R>> requestClass, @NonNull UseCase<P,R> useCase) {
        Objects.requireNonNull(requestClass);
        Objects.requireNonNull(useCase);
        dispatcher.put(requestClass,new RequestUseCase<>(useCase,null));
    }

    /**
     * Makes a{@link UseCase} known to this mediator, coupled with a {@link Mapper} needed for
     * result mapping to served request.
     *
     * @param requestClass request class type served by use case.
     * @param useCase the use case to be invoked upon incoming requests.
     * @param mapper a mapper to be used on use case result.
     * @param <P> use case input data type .
     * @param <R> use case result.
     * @param <M> mapped data type.
     */
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
        // throw error if trying to execute unknown request
        if (!dispatcher.containsKey(request.getClass())) {
            throw new IllegalArgumentException(REQUEST_ERROR);

        } else {
            // resolve if request has a matching use case coupled wit a mapper, and
            // serve request by executing appropriate use case

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

    /**
     * Mediator known use case.
     *
     * @param <P> use case input data type.
     * @param <R> use case execution result data type.
     * @param <S> use case result mapped (optional) data type.
     */
    private static class RequestUseCase<P,R,S> {
        @NonNull
        private final UseCase<P,R> useCase;
        @Nullable
        private final Mapper<R,S> mapper;

        RequestUseCase(@NonNull UseCase<P, R> useCase, @Nullable Mapper<R, S> mapper) {
            this.useCase = useCase;
            this.mapper = mapper;
        }

        @NonNull
        UseCase<P, R> getUseCase() {
            return useCase;
        }

        @Nullable
        public Mapper<R, S> getMapper() {
            return mapper;
        }

        boolean isMapped() {
            return mapper != null;
        }
    }
}
