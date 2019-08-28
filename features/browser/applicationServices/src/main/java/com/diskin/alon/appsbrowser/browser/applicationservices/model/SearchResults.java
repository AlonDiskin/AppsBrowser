package com.diskin.alon.appsbrowser.browser.applicationservices.model;

import androidx.annotation.NonNull;

import java.util.List;

public class SearchResults {
    @NonNull
    private final List<UserAppDto> apps;
    @NonNull
    private final String query;

    public SearchResults(@NonNull List<UserAppDto> apps, @NonNull String query) {
        this.apps = apps;
        this.query = query;
    }

    @NonNull
    public List<UserAppDto> getApps() {
        return apps;
    }

    @NonNull
    public String getQuery() {
        return query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchResults that = (SearchResults) o;

        if (!apps.equals(that.apps)) return false;
        return query.equals(that.query);
    }

    @Override
    public int hashCode() {
        int result = apps.hashCode();
        result = 31 * result + query.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SearchResults{" +
                "apps=" + apps +
                ", query='" + query + '\'' +
                '}';
    }
}
