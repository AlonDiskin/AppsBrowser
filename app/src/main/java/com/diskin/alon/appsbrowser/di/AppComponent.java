package com.diskin.alon.appsbrowser.di;

import com.diskin.alon.appsbrowser.AppsBrowserApp;
import com.diskin.alon.appsbrowser.home.di.MainActivityInjectionModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {AndroidInjectionModule.class,
        AppModule.class,
        MainActivityInjectionModule.class})
public interface AppComponent extends AndroidInjector<AppsBrowserApp> {

    @Component.Builder
    interface Builder {

        AppComponent build();
    }
}
