package com.diskin.alon.appsbrowser.browser.domain;

public class UserApp {
    private final String id;
    private final String name;
    private final double size;
    private final String iconUri;

    public UserApp(String id, String name, double size, String iconUri) {
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

        UserApp userApp = (UserApp) o;

        if (Double.compare(userApp.size, size) != 0) return false;
        if (id != null ? !id.equals(userApp.id) : userApp.id != null) return false;
        if (name != null ? !name.equals(userApp.name) : userApp.name != null) return false;
        return iconUri != null ? iconUri.equals(userApp.iconUri) : userApp.iconUri == null;
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
}
