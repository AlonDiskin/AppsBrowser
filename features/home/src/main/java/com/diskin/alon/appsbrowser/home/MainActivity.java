package com.diskin.alon.appsbrowser.home;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
        // Inject activity dependencies
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        // setup activity layout
        setContentView(R.layout.activity_main);

        // set activity toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // show browser screen
        navigator.setGraph(getNavHost());

        // setup toolbar navigation support
        navigator.setToolbarUpdates(this,toolbar,getNavHost());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // React to menu items selection
        if (item.getItemId() == R.id.action_settings) {
            // navigate to feature b
            navigator.openSettings(getNavHost());
        }

        return false;
    }

    /**
     * Get the navigation host view of this activity.
     */
    private View getNavHost() {
        return findViewById(R.id.nav_host_fragment);
    }

}
