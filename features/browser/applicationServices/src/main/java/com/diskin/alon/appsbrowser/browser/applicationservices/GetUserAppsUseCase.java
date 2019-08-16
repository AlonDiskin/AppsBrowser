package com.diskin.alon.appsbrowser.browser.applicationservices;

import androidx.annotation.NonNull;

import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;
import com.diskin.alon.appsbrowser.common.applicationservices.UseCase;

import java.util.List;

import io.reactivex.Observable;

/**
 * Fetches an observable list of all existing user applications.
 */
public class GetUserAppsUseCase implements UseCase<AppsSorting, Observable<List<UserAppDto>>> {

    @NonNull
    private final UserAppsRepository repository;
    @NonNull
    private final Mapper<UserAppEntity,UserAppDto> mapper;

    public GetUserAppsUseCase(@NonNull UserAppsRepository repository,
                              @NonNull Mapper<UserAppEntity, UserAppDto> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<List<UserAppDto>> execute(AppsSorting param) {
        return repository.getUserApps(param)
                .flatMap(Observable::fromIterable)
                .flatMap(userApp -> Observable.just(mapper.map(userApp)))
                .toList()
                .toObservable();
    }
}
