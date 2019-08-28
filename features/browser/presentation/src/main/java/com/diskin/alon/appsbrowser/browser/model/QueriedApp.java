package com.diskin.alon.appsbrowser.browser.model;

import android.text.SpannableString;

import androidx.annotation.NonNull;

public class QueriedApp extends UserApp {
    @NonNull
    private final SpannableString highlightedName;
    @NonNull
    private final String query;

    /**
     * Create a new {@link UserApp} instance.
     *  @param packageName app system identifier.
     * @param name        app name.
     * @param size        app size in megabits.
     * @param iconUri     app icon resource identifier.
     * @param highlightedName
     */
    public QueriedApp(String packageName, String name, String size, String iconUri, SpannableString highlightedName,@NonNull String query) {
        super(packageName, name, size, iconUri);
        this.highlightedName = highlightedName;
        this.query = query;
    }

    @NonNull
    public SpannableString getHighlightedName() {
        return highlightedName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        QueriedApp that = (QueriedApp) o;

        return query.equals(that.query);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + query.hashCode();
        return result;
    }
}
