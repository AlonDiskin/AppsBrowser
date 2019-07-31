package com.diskin.alon.appsbrowser;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.diskin.alon.appsbrowser.home.HomeNavigator;

import javax.inject.Inject;

public class HomeNavigatorImpl implements HomeNavigator {

    @Inject
    public HomeNavigatorImpl() {
    }

    @Override
    public void openBrowser(@NonNull View navView) {
        Navigation.findNavController(navView).setGraph(R.navigation.nav_graph);
    }
}
