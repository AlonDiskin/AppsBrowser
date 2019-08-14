package com.diskin.alon.appsbrowser.common.presentation;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel extends ViewModel {
    @NonNull
    private final ServiceExecutor serviceExecutor;
    @NonNull
    private final CompositeDisposable disposables = new CompositeDisposable();

    protected BaseViewModel(@NonNull ServiceExecutor serviceExecutor) {
        this.serviceExecutor = serviceExecutor;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.dispose();
    }

    protected void addDisposable(@NonNull Disposable disposable) {
        disposables.add(disposable);
    }

    @NonNull
    protected ServiceExecutor getServiceExecutor() {
        return serviceExecutor;
    }
}
