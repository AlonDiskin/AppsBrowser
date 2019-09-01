package com.diskin.alon.appsbrowser.util;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class ImageViewUriMatcher extends BoundedMatcher<View, ImageView> {
    @NonNull
    private final Uri matchedUri;

    public ImageViewUriMatcher(Class<? extends ImageView> expectedType, @NonNull Uri matchedUri) {
        super(expectedType);
        this.matchedUri = matchedUri;
    }

    @NonNull
    public static Matcher<View> withImageUri(@NonNull Uri uri) {
        return new ImageViewUriMatcher(ImageView.class,uri);
    }

    @Override
    protected boolean matchesSafely(ImageView item) {

        return false;
    }

    @Override
    public void describeTo(Description description) {

    }
}
