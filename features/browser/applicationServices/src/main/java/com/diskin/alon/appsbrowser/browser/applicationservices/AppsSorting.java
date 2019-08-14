package com.diskin.alon.appsbrowser.browser.applicationservices;

import androidx.annotation.NonNull;

public class AppsSorting {

    public enum SortingType {SIZE,NAME}

    @NonNull
    private final SortingType type;
    private final boolean ascending;

    public AppsSorting(@NonNull SortingType type, boolean ascending) {
        this.type = type;
        this.ascending = ascending;
    }

    @NonNull
    public SortingType getType() {
        return type;
    }

    public boolean isAscending() {
        return ascending;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppsSorting sorting = (AppsSorting) o;

        if (ascending != sorting.ascending) return false;
        return type == sorting.type;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (ascending ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AppsSorting{" +
                "type=" + type +
                ", ascending=" + ascending +
                '}';
    }
}
