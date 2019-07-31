package com.diskin.alon.appsbrowser.home.integration.di;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = TestAppModule.class)
public interface TestAppComponent extends AndroidInjector<TestApp> {

    @Component.Builder
    interface Builder {

        TestAppComponent build();
    }
}
