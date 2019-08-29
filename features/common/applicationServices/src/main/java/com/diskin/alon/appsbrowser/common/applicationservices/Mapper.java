package com.diskin.alon.appsbrowser.common.applicationservices;

/**
 * Objects mapper contract.
 *
 * @param <S> source object data type.
 * @param <R> mapped result data type.
 */
public interface Mapper<S,R> {

    /**
     * Maps the given arg to contracted data type.
     *
     * @param source the object to map
     * @return mapped result of passed source.
     */
    R map(S source);
}
