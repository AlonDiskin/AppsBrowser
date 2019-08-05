package com.diskin.alon.appsbrowser.common;

public interface UseCase<P,R> {
    R execute(P param);
}
