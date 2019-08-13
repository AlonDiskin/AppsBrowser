package com.diskin.alon.appsbrowser.browser.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import com.diskin.alon.appsbrowser.browser.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.model.AppsSorting.SortingType;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.common.presentation.ServiceExecutor;

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
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link BrowserViewModelImpl} unit test class.
 */
@RunWith(JUnitParamsRunner.class)
public class BrowserViewModelImplTest {

    // System under test
    private BrowserViewModelImpl viewModel;

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
        when(serviceExecutor.execute(isA(GetUserAppsRequest.class))).thenReturn(appsObservableSubject);

        viewModel = new BrowserViewModelImpl(serviceExecutor);
    }

    @Test
    @Parameters(method = "sortingParam")
    public void shouldFetchApps_whenSortingNotSet(AppsSorting sorting) {
        // Given an initialized view model (sorting is not set)

        // When view model is asked to get user apps
        viewModel.sortApps(sorting);

        // Then view model should fetch apps from service executor with given sorting
        verify(serviceExecutor).execute(eq(new GetUserAppsRequest(sorting)));
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
        verify(serviceExecutor).execute(eq(new GetUserAppsRequest(sorting)));
    }

    @Test
    @Parameters(method = "sortingParams")
    public void shouldFetchApps_whenSortedDifferently(AppsSorting firstSorting,AppsSorting secondSorting) {
        // Given an initialized view model

        // When view model is asked to sort apps
        viewModel.sortApps(firstSorting);

        // Then view model should fetch apps via service executor
        verify(serviceExecutor).execute(eq(new GetUserAppsRequest(firstSorting)));

        // When view model is asked to sort with different sorting
        viewModel.sortApps(secondSorting);

        // Then view model should fetch apps from service executor with given sorting
        verify(serviceExecutor).execute(eq(new GetUserAppsRequest(secondSorting)));
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
