package com.diskin.alon.appsbrowser.browser.featuretest.di;

import com.diskin.alon.appsbrowser.browser.applicationservices.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.controller.BrowserNavigator;

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

    @Singleton
    @Provides
    public static BrowserNavigator provideBrowserNavigator() {
        return Mockito.mock(BrowserNavigator.class);
    }
}
