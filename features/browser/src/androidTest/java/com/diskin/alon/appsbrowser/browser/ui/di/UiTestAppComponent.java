package com.diskin.alon.appsbrowser.browser.ui.di;

import androidx.annotation.NonNull;

import com.diskin.alon.appsbrowser.browser.runner.TestApp;
import com.diskin.alon.appsbrowser.browser.ui.BrowserFragmentTest;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;


@Singleton
@Component(modules = TestAppModule.class)
public interface UiTestAppComponent extends AndroidInjector<TestApp> {

    @Component.Builder
    interface Builder {

        UiTestAppComponent build();
    }

    void inject(@NonNull BrowserFragmentTest test);

}
