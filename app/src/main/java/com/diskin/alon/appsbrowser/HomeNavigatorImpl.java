package com.diskin.alon.appsbrowser;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

    @Override
    public void openSettings(@NonNull View navView) {
        Navigation.findNavController(navView).navigate(R.id.settingsFragment);
    }

    @Override
    public void addToolbar(@NonNull Toolbar toolbar, @NonNull View navView) {
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(Navigation.findNavController(navView).getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, Navigation.findNavController(navView),appBarConfiguration);
    }
}
