package com.diskin.alon.appsbrowser.home;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Home screen controller.
 */
public class MainActivity extends AppCompatActivity {

    @Inject
    HomeNavigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inject activity dependencies
        AndroidInjection.inject(this);
        // setup activity layout
        setContentView(R.layout.activity_main);

        // setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // show browser screen
        navigator.openBrowser(getNavHost());
    }

    /**
     * Get the navigation host view of this activity.
     */
    private View getNavHost() {
        return findViewById(R.id.nav_host_fragment);
    }

}
