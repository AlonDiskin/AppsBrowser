package com.diskin.alon.appsbrowser.browser.data;

import androidx.annotation.NonNull;

import com.diskin.alon.appsbrowser.browser.applicationservices.interfaces.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

public class UserAppsRepositoryImpl implements UserAppsRepository {
    @NonNull
    private final AppsDataStore appsDataStore;

    public UserAppsRepositoryImpl(@NonNull AppsDataStore appsDataStore) {
        this.appsDataStore = appsDataStore;
    }

    @Override
    public Observable<List<UserAppEntity>> getSortedApps(@NonNull AppsSorting sorting) {
        return appsDataStore.getAll().map(userAppEntities -> sortApps(userAppEntities,sorting));
    }

    @Override
    public Observable<List<UserAppEntity>> search(@NonNull AppsSorting sorting, @NonNull String query) {
        return getSortedApps(sorting)
                .map(userAppEntities -> filterByNameQuery(userAppEntities,query));
    }

    private List<UserAppEntity> sortApps(@NonNull List<UserAppEntity> apps, @NonNull AppsSorting sorting) {
        List<UserAppEntity> sortedApps = new ArrayList<>(apps);
        switch (sorting.getType()) {
            case NAME:
                Collections.sort(sortedApps,(o1, o2) -> o1.getName().compareTo(o2.getName()));
                break;

            case SIZE:
                Collections.sort(sortedApps,(o1, o2) -> Double.compare(o1.getSize(),o2.getSize()));
                break;

            default:
                break;
        }

        if (!sorting.isAscending()) {
            Collections.reverse(sortedApps);
        }

        return sortedApps;
    }

    private List<UserAppEntity> filterByNameQuery(@NonNull List<UserAppEntity> userAppEntities, @NonNull String query) {
        List<UserAppEntity> filtered = new ArrayList<>(userAppEntities.size());

        for (UserAppEntity sortedEntity : userAppEntities) {
            if (sortedEntity.getName().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(sortedEntity);
            }
        }

        return filtered;
    }
}
