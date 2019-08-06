package com.diskin.alon.appsbrowser.browser.applicationservices;

import androidx.annotation.NonNull;

import com.diskin.alon.appsbrowser.browser.domain.UserApp;
import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;
import com.diskin.alon.appsbrowser.common.applicationservices.UseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Fetches an observable list of all existing user applications.
 */
public class GetUserAppsUseCase implements UseCase<Void, Observable<List<UserAppDto>>> {

    @NonNull
    private final UserAppsRepository repository;
    @NonNull
    private final Mapper<UserApp,UserAppDto> mapper;

    public GetUserAppsUseCase(@NonNull UserAppsRepository repository,
                              @NonNull Mapper<UserApp, UserAppDto> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<List<UserAppDto>> execute(Void param) {
        return repository.getUserAppsByName()
                .flatMap(Observable::fromIterable)
                .flatMap(userApp -> Observable.just(mapper.map(userApp)))
                .toList()
                .toObservable();
    }
}
