package com.diskin.alon.appsbrowser.browser.util;

import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class SpannableTextMatcher extends BoundedMatcher<View, TextView> {
    @NonNull
    private final SpannableString matched;

    private SpannableTextMatcher(Class<? extends TextView> expectedType, @NonNull SpannableString str) {
        super(expectedType);
        matched = str;
    }

    public static Matcher<View> withSpannableText(@NonNull SpannableString str) {
        return new SpannableTextMatcher(TextView.class,str);
    }

    @Override
    protected boolean matchesSafely(TextView item) {
        return item.getText().equals(matched);
    }

    @Override
    public void describeTo(Description description) {

    }
}
