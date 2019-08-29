package com.diskin.alon.appsbrowser.browser.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.diskin.alon.appsbrowser.browser.WhiteBox;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSearch;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting.SortingType;
import com.diskin.alon.appsbrowser.browser.model.GetSortedAppsRequest;
import com.diskin.alon.appsbrowser.browser.model.SearchAppsRequest;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.common.presentation.ServiceExecutor;
import com.diskin.alon.appsbrowser.common.presentation.ServiceRequest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.SerialDisposable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link BrowserViewModelImpl} unit test class.
 */
@RunWith(JUnitParamsRunner.class)
public class BrowserViewModelImplTest {
    // System under test
    private BrowserViewModelImpl viewModel;

    // Lifecycle testing rule
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    // Mocked dependent on components
    @Mock
    public ServiceExecutor serviceExecutor;

    // Stubbed data
    private PublishSubject<List<UserApp>> appsObservableSubject = PublishSubject.create();

    @BeforeClass
    public static void setUpClass() {
        // Set Rx framework for testing
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable ->
                Schedulers.trampoline());
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        //noinspection unchecked
        when(serviceExecutor.execute(isA(ServiceRequest.class))).thenReturn(appsObservableSubject);

        viewModel = new BrowserViewModelImpl(serviceExecutor);
    }

    @Test
    @Parameters(method = "sortingParam")
    public void shouldFetchApps_whenSortingNotSet(AppsSorting sorting) {
        // Given an initialized view model (sorting is not set)

        // When view model is asked to get user apps
        viewModel.sortApps(sorting);

        // Then view model should fetch apps from service executor with given sorting
        verify(serviceExecutor).execute(eq(new GetSortedAppsRequest(sorting)));
    }

    @Test
    public void shouldRestAppsObservableSubscription_whenFetchesAppsObservable() {
        // test case fixture
        SerialDisposable serialDisposable = (SerialDisposable) WhiteBox
                .getInternalState(viewModel,"serialDisposable");
        Disposable prevDisposable = TestObserver.create();

        serialDisposable.set(prevDisposable);
        // Given an initialized view model with existing observable apps subscription

        // When view model fetches apps observable via service executor
        viewModel.sortApps(new AppsSorting(SortingType.NAME,false));

        // Then view model should reset current apps observable subscription
        assertThat(serialDisposable.get(),not(sameInstance(prevDisposable)));
    }

    @Test
    public void shouldUnsubscribeFromAppsObservable_whenCleared() {
        // test case fixture
        SerialDisposable actualDisposable = (SerialDisposable) WhiteBox
                .getInternalState(viewModel,"serialDisposable");
        actualDisposable.set(TestObserver.create());

        // Given an initialized view model with existing observable apps subscription

        // When view model is cleared
        viewModel.onCleared();

        // Then view model should unsubscribe from apps observable
        assertThat(actualDisposable.isDisposed(),equalTo(true));
    }

    @Test
    @Parameters(method = "sortingParam")
    public void shouldNotReFetchApps_whenSortedWithSameValue(AppsSorting sorting) {
        // Given an initialized view model existing sorting

        // When view model is asked sort user apps
        viewModel.sortApps(sorting);

        // And asked again to sort apps
        viewModel.sortApps(sorting);

        // Then view model should pass first request only to services executor
        verify(serviceExecutor).execute(eq(new GetSortedAppsRequest(sorting)));
    }

    @Test
    @Parameters(method = "sortingParams")
    public void shouldFetchApps_whenSortedDifferently(AppsSorting firstSorting,AppsSorting secondSorting) {
        // Given an initialized view model

        // When view model is asked to sort apps
        viewModel.sortApps(firstSorting);

        // Then view model should fetch apps via service executor
        verify(serviceExecutor).execute(eq(new GetSortedAppsRequest(firstSorting)));

        // When view model is asked to sort with different sorting
        viewModel.sortApps(secondSorting);

        // Then view model should fetch apps from service executor with given sorting
        verify(serviceExecutor).execute(eq(new GetSortedAppsRequest(secondSorting)));
    }

    @Test
    public void shouldUpdateAppsObservers_whenServiceFetchedAppsUpdated() {
        List<UserApp> apps = Arrays.asList(new UserApp("facebook","45", "fc", ""),
                new UserApp("youtube","31", "yt", ""),
                new UserApp("twitter","78.8", "tw", ""),
                new UserApp("whatsApp","24.6", "wa", ""));

        // Given an initialized view model

        // And observing view model client that sorted apps
        LiveData<List<UserApp>> userAppLiveData = viewModel.getUserApps();
        viewModel.sortApps(new AppsSorting(SortingType.NAME,true));

        // When fetched users apps (from services) are updated
        appsObservableSubject.onNext(apps);

        // Then observing client
        assertThat(userAppLiveData.getValue(),equalTo(apps));
    }

    @Test
    public void shouldExecuteSearch_whenAppsSearchedAndSortingSet() {
        // Given an initialized view model, with existing sorting value
        String query = "query";
        AppsSorting sorting = new AppsSorting(SortingType.NAME,true);

        //noinspection unchecked
        ((MutableLiveData<AppsSorting>) WhiteBox.getInternalState(viewModel,"sorting")).postValue(sorting);

        // When view model asked to perform search
        viewModel.searchApps(query);

        // Then view model should execute a search service
        verify(serviceExecutor).execute(new SearchAppsRequest(new AppsSearch(sorting,query)));
    }

    @Test
    public void shouldNotExecuteSearch_whenAppsSearchedAndSortingNotSet() {
        // Given an initialized view model, with nullable sorting value

        // When view model is searched for apps
        viewModel.searchApps("query");

        // Then view model should not execute apps search
        verify(serviceExecutor,times(0)).execute(isA(SearchAppsRequest.class));
    }

    @Test
    public void shouldNotExecuteSearch_whenPassedEmptyQuery() {
        // Given an initialized view model

        // When view model is searched for apps
        viewModel.searchApps("");

        // Then view model should not execute apps search
        verify(serviceExecutor,times(0)).execute(isA(SearchAppsRequest.class));
    }

    public static Object[] sortingParam() {
        return new Object[] {new AppsSorting(SortingType.NAME,true),
                new AppsSorting(SortingType.NAME,false),
                new AppsSorting(SortingType.SIZE,true)
        };
    }

    public static Object[][] sortingParams() {
        return new Object[][] {
                new Object[] {new AppsSorting(SortingType.NAME,false), new AppsSorting(SortingType.SIZE,true)},
                new Object[] {new AppsSorting(SortingType.SIZE,false), new AppsSorting(SortingType.SIZE,true)}
        };
    }
}
