package com.diskin.alon.appsbrowser.home;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.diskin.alon.appsbrowser.common.espressoidlingresource.EspressoIdlingResource;

/**
 * App launcher activity, shows splash animation upon app launch,followed by
 * user redirection to home screen.
 */
public class SplashActivity extends AppCompatActivity {
    @VisibleForTesting
    public static boolean TEST = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        incrementEspressoIdlRes();
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        launchSplashAnimation();
    }

    private void launchSplashAnimation() {
        // Configure animation sequence
        final TextView appNameTv = findViewById(R.id.appNameLabel);
        ObjectAnimator animation = ObjectAnimator.ofFloat(appNameTv, "alpha", 0f,1f);

        animation.setDuration(800);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                decrementEspressoIdlRes();
                launchHomeActivity();
            }
        });
        animation.start();
    }

    private void launchHomeActivity() {
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        finish();
    }

    private void decrementEspressoIdlRes() {
        if (TEST) {
            EspressoIdlingResource.decrement();
        }
    }

    private void incrementEspressoIdlRes() {
        if (TEST) {
            EspressoIdlingResource.increment();
        }
    }
}
