package com.diskin.alon.appsbrowser.browser.domain;

import java.util.Objects;

/**
 * Domain entity that represents an installed application on user device.
 */
public class UserAppEntity {
    private final String id;
    private final String name;
    private final double size;
    private final String iconUri;

    /**
     * Create new {@link UserAppEntity} instance.
     *
     * @param id app unique identifier.
     * @param name app name.
     * @param size app size in mb.
     * @param iconUri app icon url.
     */
    public UserAppEntity(String id, String name, double size, String iconUri) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.iconUri = iconUri;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSize() {
        return size;
    }

    public String getIconUri() {
        return iconUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAppEntity userApp = (UserAppEntity) o;

        if (Double.compare(userApp.size, size) != 0) return false;
        if (!Objects.equals(id, userApp.id)) return false;
        if (!Objects.equals(name, userApp.name)) return false;
        return Objects.equals(iconUri, userApp.iconUri);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(size);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (iconUri != null ? iconUri.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserAppEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", iconUri='" + iconUri + '\'' +
                '}';
    }
}
