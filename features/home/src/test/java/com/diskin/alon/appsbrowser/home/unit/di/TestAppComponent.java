package com.diskin.alon.appsbrowser.home.unit.di;

import androidx.annotation.NonNull;


import com.diskin.alon.appsbrowser.home.unit.MainActivityTest;

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

    void inject(@NonNull MainActivityTest test);
}
