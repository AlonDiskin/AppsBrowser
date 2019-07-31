package com.diskin.alon.appsbrowser.home.integration.di;


import com.diskin.alon.appsbrowser.home.HomeNavigator;
import com.diskin.alon.appsbrowser.home.MainActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {AndroidInjectionModule.class})
abstract public class TestAppModule {

    @Singleton
    @Provides
    public static HomeNavigator provideHomeNavigator() {
        return new FakeHomeNavigator();
    }

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivityInjector();
}
