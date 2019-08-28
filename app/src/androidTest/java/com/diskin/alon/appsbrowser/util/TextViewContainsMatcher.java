package com.diskin.alon.appsbrowser.util;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class TextViewContainsMatcher extends BoundedMatcher<View, TextView> {
    @NonNull
    private final String matchedText;
    @NonNull
    private final String containedText;

    private TextViewContainsMatcher(Class<? extends TextView> expectedType, @NonNull String matchedText, @NonNull String containedText) {
        super(expectedType);
        this.matchedText = matchedText;
        this.containedText = containedText;
    }

    public static Matcher<View> withTextContainsIgnoreCase(@NonNull String text,@NonNull String containedText) {
        return new TextViewContainsMatcher(TextView.class,text, containedText);
    }

    @Override
    protected boolean matchesSafely(TextView item) {
        return item.getText().toString().equals(matchedText) &&
                matchedText.toLowerCase().contains(containedText.toLowerCase());
    }

    @Override
    public void describeTo(Description description) {

    }
}
