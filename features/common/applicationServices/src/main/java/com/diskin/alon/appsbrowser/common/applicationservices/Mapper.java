package com.diskin.alon.appsbrowser.common.applicationservices;

/**
 * Objects mapper contract.
 *
 * @param <S> source object data type.
 * @param <R> mapped result data type.
 */
public interface Mapper<S,R> {

    /**
     * Maps the given source arg to contracted data type.
     *
     * @param source the object to copy
     * @return mapped result of source to contracted dat type.
     */
    R map(S source);
}
