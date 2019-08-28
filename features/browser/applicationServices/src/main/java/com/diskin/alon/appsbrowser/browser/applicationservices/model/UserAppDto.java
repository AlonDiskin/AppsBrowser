package com.diskin.alon.appsbrowser.browser.applicationservices.model;

import androidx.annotation.NonNull;

/**
 *
 */
public class UserAppDto {
    @NonNull
    private final String id;
    @NonNull
    private final String name;
    @NonNull
    private final double size;
    @NonNull
    private final String iconUri;

    public UserAppDto(@NonNull String id, @NonNull String name, double size, @NonNull String iconUri) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.iconUri = iconUri;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public double getSize() {
        return size;
    }

    @NonNull
    public String getIconUri() {
        return iconUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAppDto that = (UserAppDto) o;

        if (Double.compare(that.size, size) != 0) return false;
        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        return iconUri.equals(that.iconUri);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        result = 31 * result + name.hashCode();
        temp = Double.doubleToLongBits(size);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + iconUri.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserAppDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", iconUri='" + iconUri + '\'' +
                '}';
    }
}

