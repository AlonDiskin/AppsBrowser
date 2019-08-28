package com.diskin.alon.appsbrowser.browser.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.diskin.alon.appsbrowser.common.presentation.ServiceExecutor;

import javax.inject.Inject;
import javax.inject.Provider;

public class BrowserViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    private final Provider<ServiceExecutor> serviceExecutorProvider;

    @Inject
    public BrowserViewModelFactory(@NonNull Provider<ServiceExecutor> serviceExecutorProvider) {
        this.serviceExecutorProvider = serviceExecutorProvider;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new BrowserViewModelImpl(serviceExecutorProvider.get());
    }
}
