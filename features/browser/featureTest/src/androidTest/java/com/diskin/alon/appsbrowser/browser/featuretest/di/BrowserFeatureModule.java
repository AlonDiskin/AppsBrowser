package com.diskin.alon.appsbrowser.browser.featuretest.di;

import androidx.lifecycle.ViewModelProviders;

import com.diskin.alon.appsbrowser.browser.applicationservices.GetUserAppsUseCase;
import com.diskin.alon.appsbrowser.browser.applicationservices.UserAppDto;
import com.diskin.alon.appsbrowser.browser.applicationservices.UserAppEntityMapper;
import com.diskin.alon.appsbrowser.browser.applicationservices.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.controller.BrowserFragment;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModel;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModelFactory;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModelImpl;
import com.diskin.alon.appsbrowser.browser.viewmodel.GetUserAppsRequest;
import com.diskin.alon.appsbrowser.browser.viewmodel.UserAppDtosMapper;
import com.diskin.alon.appsbrowser.common.ServiceExecutor;
import com.diskin.alon.appsbrowser.common.UseCaseMediator;
import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;

@Module
public abstract class BrowserFeatureModule {

    @Provides
    public static Mapper<UserAppEntity, UserAppDto> provideUserAppUserAppDtoMapper() {
        return new UserAppEntityMapper();
    }

    @Provides
    public static GetUserAppsUseCase provideGetUserAppsUseCase(UserAppsRepository repository,
                                                               Mapper<UserAppEntity, UserAppDto> mapper) {
        return new GetUserAppsUseCase(repository, mapper);
    }

    @Provides
    public static Mapper<Observable<List<UserAppDto>>, Observable<List<UserApp>>> provideObservableObservableMapper() {
        return new UserAppDtosMapper();
    }

    @Provides
    public static ServiceExecutor provideServiceExecutor(GetUserAppsUseCase getUserAppsUseCase,
                                                         Mapper<Observable<List<UserAppDto>>, Observable<List<UserApp>>> mapper) {
        UseCaseMediator mediator = new UseCaseMediator();

        mediator.addMappedUseCase(GetUserAppsRequest.class,getUserAppsUseCase,mapper);
        return mediator;
    }

    @Provides
    public static BrowserViewModel providesBrowserViewModel(BrowserFragment fragment,
                                                            BrowserViewModelFactory factory) {
        return ViewModelProviders.of(fragment,factory).get(BrowserViewModelImpl.class);
    }
}
