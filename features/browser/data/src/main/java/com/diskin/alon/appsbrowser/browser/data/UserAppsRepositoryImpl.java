package com.diskin.alon.appsbrowser.browser.data;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.diskin.alon.appsbrowser.browser.applicationservices.AppsSorting;
import com.diskin.alon.appsbrowser.browser.applicationservices.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class UserAppsRepositoryImpl implements UserAppsRepository {

    @NonNull
    private final Application application;

    public UserAppsRepositoryImpl(@NonNull Application application) {
        this.application = application;
    }

    @Override
    public Observable<List<UserAppEntity>> getUserApps(@NonNull AppsSorting sorting) {
        return getAppsList(sorting)
                .subscribeOn(Schedulers.io());
    }

    /**
     * Returns an observable that emits a list of all non system existing apps on user device, sorted by given sort values.
     */
    private Observable<List<UserAppEntity>> getAppsList(@NonNull AppsSorting sorting) {
        return Observable.create(emitter -> {
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

            switch (sorting.getType()) {
                case NAME:
                    Collections.sort(userApps,(o1, o2) -> o1.getName().compareTo(o2.getName()));
                    break;

                case SIZE:
                    Collections.sort(userApps,(o1, o2) -> Double.compare(o1.getSize(),o2.getSize()));
                    break;

                default:
                    break;
            }

            if (!sorting.isAscending()) {
                Collections.reverse(userApps);
            }

            emitter.onNext(userApps);
            emitter.onComplete();
        });
    }
}
