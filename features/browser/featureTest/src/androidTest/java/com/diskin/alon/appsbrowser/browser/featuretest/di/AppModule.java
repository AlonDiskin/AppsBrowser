package com.diskin.alon.appsbrowser.browser.featuretest.di;

import com.diskin.alon.appsbrowser.browser.controller.BrowserNavigator;
import com.diskin.alon.appsbrowser.browser.data.AppsDataStore;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {
    @Singleton
    @Provides
    public static AppsDataStore provideAppsDataStore() {
        return Mockito.mock(AppsDataStore.class);
    }

    @Singleton
    @Provides
    public static BrowserNavigator provideBrowserNavigator() {
        return Mockito.mock(BrowserNavigator.class);
    }
}
