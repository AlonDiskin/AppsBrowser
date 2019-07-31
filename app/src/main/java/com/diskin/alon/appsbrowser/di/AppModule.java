package com.diskin.alon.appsbrowser.di;

import com.diskin.alon.appsbrowser.HomeNavigatorImpl;
import com.diskin.alon.appsbrowser.home.HomeNavigator;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class AppModule {

    @Singleton
    @Binds
    public abstract HomeNavigator bindsHomeNavigator(HomeNavigatorImpl homeNavigator);
}
