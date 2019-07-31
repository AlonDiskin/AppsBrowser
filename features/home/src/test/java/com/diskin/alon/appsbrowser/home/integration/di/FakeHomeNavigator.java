package com.diskin.alon.appsbrowser.home.integration.di;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.diskin.alon.appsbrowser.home.HomeNavigator;
import com.diskin.alon.appsbrowser.home.R;

public class FakeHomeNavigator implements HomeNavigator {
    @Override
    public void openBrowser(@NonNull View navView) {
        Navigation.findNavController(navView).setGraph(R.navigation.fake_nav);
    }
}
