package com.diskin.alon.appsbrowser.di;

import com.diskin.alon.appsbrowser.BrowserNavigatorImpl;
import com.diskin.alon.appsbrowser.HomeNavigatorImpl;
import com.diskin.alon.appsbrowser.browser.controller.BrowserNavigator;
import com.diskin.alon.appsbrowser.home.HomeNavigator;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class NavigationModule {

    @Singleton
    @Binds
    public abstract HomeNavigator bindsHomeNavigator(HomeNavigatorImpl homeNavigator);

    @Singleton
    @Binds
    public abstract BrowserNavigator bindsBrowserNavigator(BrowserNavigatorImpl browserNavigator);
}
