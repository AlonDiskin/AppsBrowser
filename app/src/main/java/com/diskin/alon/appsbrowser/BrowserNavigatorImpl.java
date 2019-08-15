package com.diskin.alon.appsbrowser;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.diskin.alon.appsbrowser.browser.controller.BrowserNavigator;

import javax.inject.Inject;

public class BrowserNavigatorImpl implements BrowserNavigator {

    @Inject
    public BrowserNavigatorImpl() {
    }

    @Override
    public void openAppDetail(@NonNull Fragment fragment, @NonNull String packageName) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + packageName));
        fragment.startActivity(intent);
    }
}
