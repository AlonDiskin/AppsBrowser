package com.diskin.alon.appsbrowser.browser.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSearch;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.model.GetSortedAppsRequest;
import com.diskin.alon.appsbrowser.browser.model.SearchAppsRequest;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.common.espressoidlingresource.EspressoIdlingResource;
import com.diskin.alon.appsbrowser.common.presentation.BaseViewModel;
import com.diskin.alon.appsbrowser.common.presentation.ServiceExecutor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Browser screen view model implementation.
 */
public class BrowserViewModelImpl extends BaseViewModel implements BrowserViewModel {
    @VisibleForTesting
    public static boolean TEST = false;
    private static final String DEFAULT_QUERY = "";

    @NonNull
    private final MutableLiveData<List<UserApp>> userApps = new MutableLiveData<>();
    @NonNull
    private final BehaviorSubject<String> appsQuery = BehaviorSubject.createDefault(DEFAULT_QUERY);
    @NonNull
    private final BehaviorSubject<AppsSorting> appsSorting = BehaviorSubject.create();
    @NonNull
    private final Disposable appsDisposable;

    @Inject
    public BrowserViewModelImpl(@NonNull ServiceExecutor serviceExecutor) {
        super(serviceExecutor);
        // build event handling rx chain
        appsDisposable = Observable.combineLatest(appsQuery, appsSorting, (query, sorting) ->
                // create service request, based on user state selections
                query.isEmpty() ? new GetSortedAppsRequest(sorting) :
                        new SearchAppsRequest(new AppsSearch(sorting, query)))
                .switchMap(request -> executeService(request))
                .doOnDispose(this::decrementEspressoIdlRes)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apps -> {
                    decrementEspressoIdlRes();
                    userApps.setValue(apps);
                }, throwable -> {
                    decrementEspressoIdlRes();
                    throwable.printStackTrace();
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // unsubscribe from apps observable
        if (!appsDisposable.isDisposed()) {
            appsDisposable.dispose();
        }
    }

    @NonNull
    @Override
    public LiveData<List<UserApp>> getUserApps() {
        return userApps;
    }

    @Override
    public void sortApps(@NonNull AppsSorting sorting) {
        // update state if value is new (to prevent unneeded repeated service executions)
        if (appsSorting.getValue() == null || !appsSorting.getValue().equals(sorting)) {
            appsSorting.onNext(sorting);
        }
    }

    @Override
    public void searchApps(@NonNull String query) {
        // update state if value is new
        if (!appsQuery.getValue().equals(query)) {
            appsQuery.onNext(query);
        }
    }

    @NonNull
    @Override
    public String getSearchQuery() {
        return appsQuery.getValue();
    }

    @Nullable
    @Override
    public AppsSorting getAppsSorting() {
        return appsSorting.getValue();
    }

    private void decrementEspressoIdlRes() {
        if (TEST && !EspressoIdlingResource.getIdlingResource().isIdleNow()) {
            EspressoIdlingResource.decrement();
        }
    }
}
