package com.diskin.alon.appsbrowser.browser.unit.di;

import com.diskin.alon.appsbrowser.browser.BrowserFragment;
import com.diskin.alon.appsbrowser.browser.BrowserViewModel;

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

    @ContributesAndroidInjector
    public abstract BrowserFragment contributeBrowserFragmentInjector();

}
