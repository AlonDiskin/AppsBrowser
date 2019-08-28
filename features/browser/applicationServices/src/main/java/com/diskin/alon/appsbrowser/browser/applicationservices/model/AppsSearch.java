package com.diskin.alon.appsbrowser.browser.applicationservices.model;

import androidx.annotation.NonNull;

public class AppsSearch {
    @NonNull
    private final AppsSorting sorting;
    @NonNull
    private final String query;

    public AppsSearch(@NonNull AppsSorting sorting, @NonNull String query) {
        this.sorting = sorting;
        this.query = query;
    }

    @NonNull
    public AppsSorting getSorting() {
        return sorting;
    }

    @NonNull
    public String getQuery() {
        return query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppsSearch params = (AppsSearch) o;

        if (!sorting.equals(params.sorting)) return false;
        return query.equals(params.query);
    }

    @Override
    public int hashCode() {
        int result = sorting.hashCode();
        result = 31 * result + query.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SearchAppsParams{" +
                "sorting=" + sorting +
                ", query='" + query + '\'' +
                '}';
    }
}
