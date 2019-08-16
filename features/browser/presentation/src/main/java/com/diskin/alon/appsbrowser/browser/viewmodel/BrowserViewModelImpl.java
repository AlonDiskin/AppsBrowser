package com.diskin.alon.appsbrowser.browser.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.diskin.alon.appsbrowser.browser.applicationservices.AppsSorting;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.common.presentation.BaseViewModel;
import com.diskin.alon.appsbrowser.common.presentation.EspressoIdlingResource;
import com.diskin.alon.appsbrowser.common.presentation.ServiceExecutor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class BrowserViewModelImpl extends BaseViewModel implements BrowserViewModel {

    @NonNull
    private final MutableLiveData<AppsSorting> sorting = new MutableLiveData<>();
    private final MutableLiveData<List<UserApp>> userApps = new MutableLiveData<>();

    @Inject
    public BrowserViewModelImpl(@NonNull ServiceExecutor serviceExecutor) {
        super(serviceExecutor);
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
        // fetch user apps if sorting is different from current
        if (this.sorting.getValue() == null || !this.sorting.getValue().equals(sorting)) {
            this.sorting.setValue(sorting);
            fetchUserApps(sorting);
        }
    }

    private void fetchUserApps(@NonNull AppsSorting sorting) {
        EspressoIdlingResource.increment();
        Disposable disposable = getServiceExecutor().execute(new GetUserAppsRequest(sorting))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apps -> {
                    EspressoIdlingResource.decrement();
                    userApps.setValue(apps);
                },throwable -> {
                    EspressoIdlingResource.decrement();
                    throwable.printStackTrace();
                });

        addDisposable(disposable);
    }
}
