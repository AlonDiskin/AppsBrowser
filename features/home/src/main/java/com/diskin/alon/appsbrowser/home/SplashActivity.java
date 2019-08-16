package com.diskin.alon.appsbrowser.home;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.diskin.alon.appsbrowser.common.presentation.EspressoIdlingResource;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EspressoIdlingResource.increment();
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
                launchHomeActivity();
            }
        });
        animation.start();
    }

    private void launchHomeActivity() {
        startActivity(new Intent(SplashActivity.this,MainActivity.class));
        finish();
        EspressoIdlingResource.decrement();
    }
}