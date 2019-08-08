package com.diskin.alon.appsbrowser.browser.featuretest.di;

import com.diskin.alon.appsbrowser.browser.featuretest.steps.AppDetailSteps;
import com.diskin.alon.appsbrowser.browser.featuretest.steps.ListAppsSteps;
import com.diskin.alon.appsbrowser.browser.featuretest.runner.FeatureTestApp;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        BrowserFragmentInjectionModule.class,
        AppModule.class})
public interface AppComponent extends AndroidInjector<FeatureTestApp> {

    void inject(ListAppsSteps test);

    void inject(AppDetailSteps test);
}
