package com.diskin.alon.appsbrowser;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.diskin.alon.appsbrowser.home.HomeNavigator;
import com.diskin.alon.appsbrowser.home.MainActivity;

import javax.inject.Inject;

public class HomeNavigatorImpl implements HomeNavigator {

    @Inject
    public HomeNavigatorImpl() {
    }

    @Override
    public void setGraph(@NonNull View navView) {
        Navigation.findNavController(navView).setGraph(R.navigation.nav_graph);
    }

    @Override
    public void openSettings(@NonNull View navView) {
        Navigation.findNavController(navView).navigate(R.id.settingsFragment);
    }

    @Override
    public void setToolbarUpdates(@NonNull MainActivity activity, @NonNull Toolbar toolbar, @NonNull View navView) {
        NavigationUI.setupWithNavController(toolbar,Navigation.findNavController(navView));
        NavigationUI.setupActionBarWithNavController(activity,Navigation.findNavController(navView));
    }
}
