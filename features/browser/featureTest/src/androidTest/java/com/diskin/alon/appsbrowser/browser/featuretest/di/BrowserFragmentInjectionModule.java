package com.diskin.alon.appsbrowser.browser.featuretest.di;

import com.diskin.alon.appsbrowser.browser.controller.BrowserFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.ClassKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = BrowserSubcomponent.class)
public abstract class BrowserFragmentInjectionModule {

    @Binds
    @IntoMap
    @ClassKey(BrowserFragment.class)
    abstract AndroidInjector.Factory<?>
    bindYourAndroidInjectorFactory(BrowserSubcomponent.Builder builder);
}
