package com.diskin.alon.appsbrowser.di;

import androidx.lifecycle.ViewModelProviders;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.SearchResults;
import com.diskin.alon.appsbrowser.browser.applicationservices.usecase.GetUserAppsUseCase;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.UserAppDto;
import com.diskin.alon.appsbrowser.browser.applicationservices.usecase.SearchAppsUseCase;
import com.diskin.alon.appsbrowser.browser.applicationservices.util.UserAppEntityMapper;
import com.diskin.alon.appsbrowser.browser.applicationservices.interfaces.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.controller.BrowserFragment;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.browser.model.SearchAppsRequest;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModel;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModelFactory;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModelImpl;
import com.diskin.alon.appsbrowser.browser.model.GetSortedAppsRequest;
import com.diskin.alon.appsbrowser.browser.util.QueriedAppsMapper;
import com.diskin.alon.appsbrowser.browser.util.UserAppDtosMapper;
import com.diskin.alon.appsbrowser.common.presentation.ServiceExecutor;
import com.diskin.alon.appsbrowser.common.presentation.UseCaseMediator;
import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;

import java.util.List;

import dagger.Binds;
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
    public static SearchAppsUseCase provideSearchAppsUseCase(UserAppsRepository repository,
                                                             Mapper<UserAppEntity, UserAppDto> mapper) {
        return new SearchAppsUseCase(repository, mapper);
    }

    @Binds
    public abstract Mapper<Observable<List<UserAppDto>>, Observable<List<UserApp>>> bindUserAppsMapper(UserAppDtosMapper mapper);

    @Binds
    public abstract Mapper<Observable<SearchResults>,Observable<List<UserApp>>> bindSearchResultsMapper(QueriedAppsMapper mapper);

    @Provides
    public static ServiceExecutor provideServiceExecutor(GetUserAppsUseCase getUserAppsUseCase,
                                                         SearchAppsUseCase searchAppsUseCase,
                                                         Mapper<Observable<List<UserAppDto>>, Observable<List<UserApp>>> userAppsMapper,
                                                         Mapper<Observable<SearchResults>,Observable<List<UserApp>>> searchResultsMapper) {
        UseCaseMediator mediator = new UseCaseMediator();

        mediator.addMappedUseCase(GetSortedAppsRequest.class,getUserAppsUseCase,userAppsMapper);
        mediator.addMappedUseCase(SearchAppsRequest.class,searchAppsUseCase,searchResultsMapper);
        return mediator;
    }

    @Provides
    public static BrowserViewModel providesBrowserViewModel(BrowserFragment fragment,
                                                            BrowserViewModelFactory factory) {
        return ViewModelProviders.of(fragment,factory).get(BrowserViewModelImpl.class);
    }
}
