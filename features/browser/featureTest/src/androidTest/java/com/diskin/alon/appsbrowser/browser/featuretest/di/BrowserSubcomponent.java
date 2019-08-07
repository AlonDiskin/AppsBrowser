package com.diskin.alon.appsbrowser.browser.featuretest.di;

import com.diskin.alon.appsbrowser.browser.controller.BrowserFragment;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@Subcomponent(modules = BrowserFeatureModule.class)
public interface BrowserSubcomponent extends AndroidInjector<BrowserFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BrowserFragment> {}
}
