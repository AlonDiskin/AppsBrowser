package com.diskin.alon.appsbrowser.browser.viewmodel;

import com.diskin.alon.appsbrowser.browser.applicationservices.UserAppDto;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;

public class UserAppDtosMapper implements Mapper<Observable<List<UserAppDto>>,Observable<List<UserApp>>> {
    @Override
    public Observable<List<UserApp>> map(Observable<List<UserAppDto>> source) {
        return source.flatMap(Observable::fromIterable)
                .flatMap(userAppDto -> Observable.just(new UserApp(
                        userAppDto.getId(),
                        userAppDto.getName(),
                        String.format(Locale.getDefault(),"%.1f", userAppDto.getSize()) + " MB",
                        userAppDto.getIconUri())))
                .toList()
                .toObservable();
    }
}
