package com.diskin.alon.appsbrowser.di;

import android.app.Application;

import com.diskin.alon.appsbrowser.browser.applicationservices.interfaces.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.data.AppsDataStore;
import com.diskin.alon.appsbrowser.browser.data.AppsDataStoreImpl;
import com.diskin.alon.appsbrowser.browser.data.UserAppsRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DataModule {

    @Singleton
    @Provides
    public static AppsDataStore provideAppsDataStore(Application application) {
        return new AppsDataStoreImpl(application);
    }

    @Singleton
    @Provides
    public static UserAppsRepository provideUserAppsRepository(AppsDataStore appsDataStore) {
        return new UserAppsRepositoryImpl(appsDataStore);
    }
}
