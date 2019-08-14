package com.diskin.alon.appsbrowser.common.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class FragmentTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_test);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    public void setFragment(@NonNull Fragment fragment, @NonNull String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commitNow();
    }
}
