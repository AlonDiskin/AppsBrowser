package com.diskin.alon.appsbrowser.di;

import android.app.Application;

import com.diskin.alon.appsbrowser.AppsBrowserApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        NavigationModule.class,
        DataModule.class,
        MainActivityInjectionModule.class,
        BrowserFragmentInjectionModule.class})
public interface AppComponent extends AndroidInjector<AppsBrowserApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
