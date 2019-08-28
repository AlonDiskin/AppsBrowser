package com.diskin.alon.appsbrowser.browser.applicationservices.usecase;

import androidx.annotation.NonNull;

import com.diskin.alon.appsbrowser.browser.applicationservices.interfaces.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSearch;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.SearchResults;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.UserAppDto;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;
import com.diskin.alon.appsbrowser.common.applicationservices.UseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Perform a search against existing user apps.
 */
public class SearchAppsUseCase implements UseCase<AppsSearch, Observable<SearchResults>> {
    @NonNull
    private final UserAppsRepository repository;
    @NonNull
    private final Mapper<UserAppEntity, UserAppDto> mapper;

    public SearchAppsUseCase(@NonNull UserAppsRepository repository, @NonNull Mapper<UserAppEntity, UserAppDto> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<SearchResults> execute(AppsSearch param) {
        return repository.search(param.getSorting(),param.getQuery())
                .map(userAppEntities -> {
                    List<UserAppDto> userAppDtos = new ArrayList<>(userAppEntities.size());

                    for (UserAppEntity userAppEntity : userAppEntities) {
                        userAppDtos.add(mapper.map(userAppEntity));
                    }

                    return new SearchResults(userAppDtos,param.getQuery());
                });
    }
}
