package com.diskin.alon.appsbrowser.browser.model;

import java.util.Objects;

/**
 * Data wrapper for existing on device user app info shown in UI.
 */
public class UserApp {
    private final String packageName;
    private final String name;
    private final String size;
    private final String iconUri;

    /**
     * Create a new {@link UserApp} instance.
     * @param name app name.
     * @param size app size in megabits.
     * @param packageName app system identifier.
     * @param iconUri app icon resource identifier.
     */
    public UserApp(String packageName, String name, String size, String iconUri) {
        this.packageName = packageName;
        this.name = name;
        this.size = size;
        this.iconUri = iconUri;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getIconUri() {
        return iconUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserApp app = (UserApp) o;

        if (!Objects.equals(name, app.name)) return false;
        if (!Objects.equals(size, app.size)) return false;
        if (!Objects.equals(packageName, app.packageName)) return false;
        return Objects.equals(iconUri, app.iconUri);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (packageName != null ? packageName.hashCode() : 0);
        result = 31 * result + (iconUri != null ? iconUri.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserApp{" +
                "packageName='" + packageName + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", iconUri='" + iconUri + '\'' +
                '}';
    }
}
