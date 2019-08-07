package com.diskin.alon.appsbrowser.browser.featuretest.di;

import com.diskin.alon.appsbrowser.browser.applicationservices.UserAppsRepository;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

    @Singleton
    @Provides
    public static UserAppsRepository provideUserAppsRepository() {
        return Mockito.mock(UserAppsRepository.class);
    }
}
