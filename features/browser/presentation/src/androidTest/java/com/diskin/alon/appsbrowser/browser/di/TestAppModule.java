package com.diskin.alon.appsbrowser.browser.di;

import com.diskin.alon.appsbrowser.browser.controller.BrowserFragment;
import com.diskin.alon.appsbrowser.browser.controller.BrowserNavigator;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModel;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

@Module(includes = {AndroidInjectionModule.class})
public abstract class TestAppModule {

    @Singleton
    @Provides
    public static BrowserViewModel provideBrowserViewModel() {
        return Mockito.mock(BrowserViewModel.class);
    }

    @Singleton
    @Provides
    public static BrowserNavigator provideBrowserNavigator() {
        return Mockito.mock(BrowserNavigator.class);
    }

    @ContributesAndroidInjector
    public abstract BrowserFragment contributeBrowserFragmentInjector();

}
