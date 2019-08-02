package com.diskin.alon.appsbrowser.browser;

/**
 * Holds the values for a on device installed application,
 */
public class UserApp {

    private final String name;
    private final String size;
    private final String iconUri;

    /**
     * Create a new {@link UserApp} instance.
     *
     * @param name app name.
     * @param size app size in megabits.
     * @param iconUri app icon resource identifier.
     */
    public UserApp(String name, String size, String iconUri) {
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

    public String getIconUri() {
        return iconUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserApp userApp = (UserApp) o;

        if (name != null ? !name.equals(userApp.name) : userApp.name != null) return false;
        if (size != null ? !size.equals(userApp.size) : userApp.size != null) return false;
        return iconUri != null ? iconUri.equals(userApp.iconUri) : userApp.iconUri == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (iconUri != null ? iconUri.hashCode() : 0);
        return result;
    }
}
