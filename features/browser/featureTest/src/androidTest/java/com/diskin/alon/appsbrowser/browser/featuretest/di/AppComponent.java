package com.diskin.alon.appsbrowser.browser.featuretest.di;

import android.app.Application;

import com.diskin.alon.appsbrowser.browser.featuretest.steps.AppDetailSteps;
import com.diskin.alon.appsbrowser.browser.featuretest.steps.AppsSearchSteps;
import com.diskin.alon.appsbrowser.browser.featuretest.steps.ListAppsSteps;
import com.diskin.alon.appsbrowser.browser.featuretest.runner.FeatureTestApp;
import com.diskin.alon.appsbrowser.browser.featuretest.steps.ProvideSortingSteps;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        BrowserFragmentInjectionModule.class,
        AppModule.class})
public interface AppComponent extends AndroidInjector<FeatureTestApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(ListAppsSteps test);

    void inject(AppDetailSteps test);

    void inject(ProvideSortingSteps test);

    void inject(AppsSearchSteps test);
}
