package com.diskin.alon.appsbrowser.browser;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

/**
 * Calculates the difference between {@link UserApp}s lists.
 */
public class UserAppsDiffCallback extends DiffUtil.Callback {

    @NonNull
    private final List<UserApp> oldApps;
    @NonNull
    private final List<UserApp> newApps;

    public UserAppsDiffCallback(@NonNull List<UserApp> oldApps, @NonNull List<UserApp> newApps) {
        this.oldApps = oldApps;
        this.newApps = newApps;
    }

    @Override
    public int getOldListSize() {
        return oldApps.size();
    }

    @Override
    public int getNewListSize() {
        return newApps.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldApps.get(oldItemPosition).getId().equals(newApps.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldApps.get(oldItemPosition).equals(newApps.get(newItemPosition));
    }
}
