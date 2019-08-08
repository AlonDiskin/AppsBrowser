package com.diskin.alon.appsbrowser.di;

import android.app.Application;

import com.diskin.alon.appsbrowser.BrowserNavigatorImpl;
import com.diskin.alon.appsbrowser.HomeNavigatorImpl;
import com.diskin.alon.appsbrowser.browser.applicationservices.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.controller.BrowserNavigator;
import com.diskin.alon.appsbrowser.browser.data.UserAppsRepositoryImpl;
import com.diskin.alon.appsbrowser.home.HomeNavigator;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

    @Singleton
    @Binds
    public abstract HomeNavigator bindsHomeNavigator(HomeNavigatorImpl homeNavigator);

    @Singleton
    @Binds
    public abstract BrowserNavigator bindsBrowserNavigator(BrowserNavigatorImpl browserNavigator);

    @Singleton
    @Provides
    public static UserAppsRepository provideUserAppsRepository(Application application) {
        return new UserAppsRepositoryImpl(application);
    }
}
