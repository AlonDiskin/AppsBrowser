package com.diskin.alon.appsbrowser.browser.data;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.common.espressoidlingresource.EspressoIdlingResource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class AppsDataStoreImpl implements AppsDataStore {
    @VisibleForTesting
    public static boolean INCREMENT_TEST = false;

    @NonNull
    private final Application application;
    @NonNull
    private final Observable<List<UserAppEntity>> deviceAppsObservable;

    public AppsDataStoreImpl(@NonNull Application application) {
        this.application = application;
        deviceAppsObservable = createDeviceAppsObservable();
    }

    @Override
    public Observable<List<UserAppEntity>> getAll() {
        return deviceAppsObservable;
    }

    private Observable<List<UserAppEntity>> createDeviceAppsObservable() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addDataScheme("package");

        Observable<List<UserAppEntity>> fetchAppsObservable =
                Observable.create(emitter -> emitter.onNext(getDeviceApps()));

        Observable<List<UserAppEntity>> deviceAppsChangeObservable =
                RxBroadcastReceiver.create(application,filter)
                .observeOn(Schedulers.io())
                .map(intent -> getDeviceApps());

        return Observable.merge(fetchAppsObservable,deviceAppsChangeObservable)
                .subscribeOn(Schedulers.io());
    }

    private List<UserAppEntity> getDeviceApps() {
        if (INCREMENT_TEST) {
            EspressoIdlingResource.increment();
        }
        PackageManager pm = application.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<UserAppEntity> userApps = new ArrayList<>(packages.size());

        for (ApplicationInfo appInfo : packages) {
            // get info for non system apps only
            if( pm.getLaunchIntentForPackage(appInfo.packageName) != null ){
                // extract installed app information
                String appId = appInfo.packageName;
                String appName = pm.getApplicationLabel(appInfo).toString();
                File file = new File(appInfo.publicSourceDir);
                double appSize = Long.valueOf(file.length()).doubleValue() / (1024 * 1024);
                Uri uri = Uri.parse("android.resource://" + appInfo.packageName + "/"
                        + appInfo.icon);

                // add to user apps result list
                userApps.add(new UserAppEntity(appId,
                        appName,
                        appSize,
                        uri.toString()));
            }
        }

        return userApps;
    }
}
