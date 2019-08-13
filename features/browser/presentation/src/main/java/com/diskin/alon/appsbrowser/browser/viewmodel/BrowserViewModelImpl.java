package com.diskin.alon.appsbrowser.browser.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.diskin.alon.appsbrowser.browser.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.common.presentation.EspressoIdlingResource;
import com.diskin.alon.appsbrowser.common.presentation.ServiceExecutor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BrowserViewModelImpl extends ViewModel implements BrowserViewModel {

    @NonNull
    private final ServiceExecutor serviceExecutor;
    @NonNull
    private MutableLiveData<List<UserApp>> userApps = new MutableLiveData<>();
    @NonNull
    private CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public BrowserViewModelImpl(@NonNull ServiceExecutor serviceExecutor) {
        this.serviceExecutor = serviceExecutor;

        // get apps observable from application services
        fetchUserApps();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.dispose();
    }

    /**
     * Fetches an observable list of {@link UserApp} from app services, and updates user
     * apps state when new lists are emitted.
     */
    private void fetchUserApps() {
        EspressoIdlingResource.increment();
        Disposable disposable = serviceExecutor.execute(new GetUserAppsRequest(null))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(apps -> {
                    EspressoIdlingResource.decrement();
                    userApps.setValue(apps);
                },throwable -> {
                    EspressoIdlingResource.decrement();
                    throwable.printStackTrace();
                });

        disposables.add(disposable);
    }

    @Override
    public LiveData<List<UserApp>> getUserApps() {
        return userApps;
    }

    @NonNull
    @Override
    public LiveData<List<UserApp>> getUserApps(@NonNull AppsSorting sorting, boolean ascending) {
        return null;
    }

    @NonNull
    @Override
    public LiveData<AppsSorting> getAppsSorting() {
        return null;
    }

    @NonNull
    @Override
    public LiveData<Boolean> getAscending() {
        return null;
    }

    @Override
    public void sortApps(@NonNull AppsSorting sorting) {

    }

    @Override
    public void orderApps(boolean ascending) {

    }

}
