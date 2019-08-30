package com.diskin.alon.appsbrowser.browser.util;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.UserAppDto;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Mapper that converts {@link UserAppDto}s emitted by their observable to {@link UserApp}s.
 */
public class UserAppDtosMapper implements Mapper<Observable<List<UserAppDto>>,Observable<List<UserApp>>> {
    @Inject
    public UserAppDtosMapper() {}

    @Override
    public Observable<List<UserApp>> map(Observable<List<UserAppDto>> source) {
        return source.flatMap(userAppDtos -> Observable.fromIterable(userAppDtos)
                .map(userAppDto -> new UserApp(userAppDto.getId(),
                     userAppDto.getName(),
                     String.format(Locale.getDefault(),"%.1f", userAppDto.getSize()) + " MB",
                     userAppDto.getIconUri()))
                .toList()
                .toObservable());
    }
}
