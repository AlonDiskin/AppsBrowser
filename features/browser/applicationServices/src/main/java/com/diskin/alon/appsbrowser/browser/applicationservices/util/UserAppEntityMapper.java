package com.diskin.alon.appsbrowser.browser.applicationservices.util;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.UserAppDto;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;

public class UserAppEntityMapper implements Mapper<UserAppEntity, UserAppDto> {
    @Override
    public UserAppDto map(UserAppEntity source) {
        return new UserAppDto(source.getId(),
                source.getName(),
                source.getSize(),
                source.getIconUri());
    }
}
