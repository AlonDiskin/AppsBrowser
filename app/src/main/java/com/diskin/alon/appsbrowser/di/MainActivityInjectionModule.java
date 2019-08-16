package com.diskin.alon.appsbrowser.di;

import com.diskin.alon.appsbrowser.home.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = MainSubComponent.class)
public abstract class MainActivityInjectionModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivityInjector();
}
