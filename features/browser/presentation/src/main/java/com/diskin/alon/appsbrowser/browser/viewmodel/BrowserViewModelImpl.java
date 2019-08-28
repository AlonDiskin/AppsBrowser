package com.diskin.alon.appsbrowser.browser.viewmodel;

import androidx.annotation.NonNull;
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
import com.diskin.alon.appsbrowser.common.presentation.ServiceRequest;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.SerialDisposable;

/**
 * Browser screen view model implementation.
 */
public class BrowserViewModelImpl extends BaseViewModel implements BrowserViewModel {
    private static final String EMPTY_FILTER = "";
    @VisibleForTesting
    public static boolean DECREMENT_TEST = false;

    @NonNull
    private final MutableLiveData<AppsSorting> sorting = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<List<UserApp>> userApps = new MutableLiveData<>();
    @NonNull
    private String query = EMPTY_FILTER;
    @NonNull
    private final SerialDisposable serialDisposable = new SerialDisposable();

    @Inject
    public BrowserViewModelImpl(@NonNull ServiceExecutor serviceExecutor) {
        super(serviceExecutor);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // unregister from apps observable stream
        if (!serialDisposable.isDisposed()) {
            serialDisposable.dispose();
        }
    }

    @NonNull
    @Override
    public LiveData<List<UserApp>> getUserApps() {
        return userApps;
    }

    @NonNull
    @Override
    public LiveData<AppsSorting> getSorting() {
        return sorting;
    }

    @Override
    public void sortApps(@NonNull AppsSorting sorting) {
        // update sorting data if passed arg is different then existing value
        if (this.sorting.getValue() == null || !this.sorting.getValue().equals(sorting)) {
            this.sorting.setValue(sorting);
            // execute sorting
            ServiceRequest<?,Observable<List<UserApp>>> request;

            // if query empty,get all with no filtering
            if (query.equals(EMPTY_FILTER)) {
                request = new GetSortedAppsRequest(sorting);

            } else {
                request = new SearchAppsRequest(new AppsSearch(sorting,query));
            }

            fetchUserApps(request);
        }
    }

    @Override
    public void searchApps(@NonNull String query) {
        // update query data if passed arg is different then existing value and non empty
        if (!this.query.equals(query)) {
            this.query = query;
            // execute search if sorting value exists
            if (sorting.getValue() != null) {
                AppsSearch params = new AppsSearch(sorting.getValue(), query);
                SearchAppsRequest searchAppsRequest = new SearchAppsRequest(params);
                fetchUserApps(searchAppsRequest);
            }
        }
    }

    @NonNull
    @Override
    public String getSearchQuery() {
        return query;
    }

    /**
     * Fetch observable list of device apps.
     *
     * @param request the selected service request for apps fetching, from possible
     * implementations.
     */
    private void fetchUserApps(@NonNull ServiceRequest<?,Observable<List<UserApp>>> request) {
        // fetch observable apps from service executor
        Disposable appsDisposable = executeService(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userApps -> {
                    decrementEspressoIdlRes();
                    this.userApps.setValue(userApps);
                },throwable -> {
                    decrementEspressoIdlRes();
                    throwable.printStackTrace();
                });

        // replace apps observable subscription with current one
        serialDisposable.set(appsDisposable);
    }

    private void decrementEspressoIdlRes() {
        if (DECREMENT_TEST) {
            EspressoIdlingResource.decrement();
        }
    }
}
