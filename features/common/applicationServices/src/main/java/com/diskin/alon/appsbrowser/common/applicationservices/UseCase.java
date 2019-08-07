package com.diskin.alon.appsbrowser.common.applicationservices;

/**
 * Application services use case contract.
 *
 * @param <P> use case input type.
 * @param <R> use case output result type.
 */
public interface UseCase<P,R> {

    /**
     * Executes the use case operation.
     *
     * @param param use case input.
     * @return the execution result.
     */
    R execute(P param);
}
