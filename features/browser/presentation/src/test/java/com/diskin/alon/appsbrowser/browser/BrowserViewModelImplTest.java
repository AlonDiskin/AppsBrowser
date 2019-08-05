package com.diskin.alon.appsbrowser.browser;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link BrowserViewModelImpl} unit test class.
 */
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
    public void shouldFetchApps_whenCreated() {
        // Given an initialized view model

        // Then view model should have requested service executor to fetch apps observable
        verify(serviceExecutor).execute(eq(new GetUserAppsRequest(null)));
    }

    @Test
    public void shouldStopAllOngoingServiceExecutions_whenCleared() {
        // Given an initialized view model

        // When view model is cleared
        viewModel.onCleared();
        CompositeDisposable disposables = (CompositeDisposable) WhiteBox.getInternalState(viewModel,"disposables");

        // Then view model should dispose off all its observables
        assertThat(disposables.isDisposed(),equalTo(true));
    }

    @Test
    public void shouldUpdateAppsObservers_whenServiceFetchedAppsUpdated() {
        List<UserApp> apps = Arrays.asList(new UserApp("facebook","45", "fc", ""),
                new UserApp("youtube","31", "yt", ""),
                new UserApp("twitter","78.8", "tw", ""),
                new UserApp("whatsApp","24.6", "wa", ""));

        // Given an initialized view model

        // And observing view model client
        LiveData<List<UserApp>> userAppLiveData = viewModel.getUserApps();

        // When fetched users apps (from services) are updated
        appsObservableSubject.onNext(apps);

        // Then observing client
        assertThat(userAppLiveData.getValue(),equalTo(apps));
    }
}
