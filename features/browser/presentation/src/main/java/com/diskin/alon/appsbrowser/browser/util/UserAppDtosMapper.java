package com.diskin.alon.appsbrowser.browser.util;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.UserAppDto;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

public class UserAppDtosMapper implements Mapper<Observable<List<UserAppDto>>,Observable<List<UserApp>>> {
    @Inject
    public UserAppDtosMapper() {}

    @Override
    public Observable<List<UserApp>> map(Observable<List<UserAppDto>> source) {
        return source.map(userAppDtos -> {
            List<UserApp> userApps = new ArrayList<>(userAppDtos.size());

            for (UserAppDto userAppDto : userAppDtos) {
                userApps.add(new UserApp(userAppDto.getId(),
                        userAppDto.getName(),
                        String.format(Locale.getDefault(),"%.1f", userAppDto.getSize()) + " MB",
                        userAppDto.getIconUri()));
            }

            return userApps;
        });
    }
}
