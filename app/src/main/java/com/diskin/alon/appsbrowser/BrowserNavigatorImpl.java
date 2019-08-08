package com.diskin.alon.appsbrowser;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.diskin.alon.appsbrowser.browser.controller.BrowserNavigator;

import javax.inject.Inject;

public class BrowserNavigatorImpl implements BrowserNavigator {

    @NonNull
    private final Application application;

    @Inject
    public BrowserNavigatorImpl(@NonNull Application application) {
        this.application = application;
    }

    @Override
    public void openAppDetail(@NonNull String packageName) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        application.startActivity(intent);
    }
}
