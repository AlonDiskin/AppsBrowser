package com.diskin.alon.appsbrowser.browser.data;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting.SortingType;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link UserAppsRepositoryImpl} unit test class.
 */
@RunWith(JUnitParamsRunner.class)
public class UserAppsRepositoryImplTest {
    // System under test
    private UserAppsRepositoryImpl repository;

    // Mocked collaborators
    @Mock
    public AppsDataStore appsDataStore;

    // Stub data
    private List<UserAppEntity> userAppEntities = Arrays.asList(
            new UserAppEntity("1","WhatsApp",45.72,"url1"),
            new UserAppEntity("2","YouTube",35.2,"url2"),
            new UserAppEntity("3","Google Maps",75.28,"url3"),
            new UserAppEntity("4","Google Play Music",15.56,"url4"),
            new UserAppEntity("5","Google",95.6,"url5"),
            new UserAppEntity("6","Facebook",25.5,"url6"),
            new UserAppEntity("7","Twitter",19.18,"url7"));

    @Before
    public void setUp() {
        // init mocks
        MockitoAnnotations.initMocks(this);
        // stub mocked data store
        when(appsDataStore.getAll()).thenReturn(Observable.just(userAppEntities));
        // init SUT
        repository = new UserAppsRepositoryImpl(appsDataStore);
    }

    @Test
    @Parameters(method = "sortParams")
    public void shouldFetchSortedAppsObservable_whenInvoked(AppsSorting sorting) {
        // Given an initialized repository

        // When repository is asked to fetch a sorted apps observable
        List<UserAppEntity> actualApps = repository.getSortedApps(sorting).blockingFirst();

        // Then repository should fetch all apps observable from apps data store
        verify(appsDataStore).getAll();

        // And returned observable should emmit sorted apps based on sort args passed by client
        assertThat(actualApps,equalTo(expectedSortedApps(sorting)));
    }

    @Test
    @Parameters(method = "searchParams")
    public void shouldFetchQueryFilteredAppsObservable_whenInvoked(AppsSorting sorting, String query) {
        // Given an initialized repository

        // When repository is asked to fetch apps observable based on a search query
        List<UserAppEntity> actualApps = repository.search(sorting,query).blockingFirst();

        // Then repository should fetch all apps observable from apps data store
        verify(appsDataStore).getAll();

        // And returned observable should emmit searched apps based on args passed by client
        assertThat(actualApps,equalTo(expectedSearchedApps(sorting,query)));
    }

    private List<UserAppEntity> expectedSearchedApps(AppsSorting sorting, String query) {
        List<UserAppEntity> sortedApps = expectedSortedApps(sorting);
        List<UserAppEntity> queriedApps = new ArrayList<>();

        for (UserAppEntity userAppEntity : sortedApps) {
            if (userAppEntity.getName().toLowerCase().contains(query.toLowerCase())) {
                queriedApps.add(userAppEntity);
            }
        }

        return queriedApps;
    }

    private List<UserAppEntity> expectedSortedApps(AppsSorting sorting) {
        List<UserAppEntity> sortedApps = new ArrayList<>(userAppEntities);
        switch (sorting.getType()) {
            case NAME:
                Collections.sort(sortedApps,(o1, o2) -> o1.getName().compareTo(o2.getName()));
                break;

            case SIZE:
                Collections.sort(sortedApps,(o1, o2) -> Double.compare(o1.getSize(),o2.getSize()));
                break;

            default:
                break;
        }

        if (!sorting.isAscending()) {
            Collections.reverse(sortedApps);
        }

        return sortedApps;
    }

    public static Object[] sortParams() {
        return new Object[] {new AppsSorting(SortingType.NAME,true),
                new AppsSorting(SortingType.NAME,false),
                new AppsSorting(SortingType.SIZE,true),
                new AppsSorting(SortingType.SIZE,false)};
    }

    public static Object[][] searchParams() {
        return new Object[][] {new Object[] {new AppsSorting(SortingType.NAME,true),"goo"},
                new Object[] {new AppsSorting(SortingType.SIZE,true),"gle"},
                new Object[] {new AppsSorting(SortingType.SIZE,false),"t"},
                new Object[] {new AppsSorting(SortingType.NAME,false),"T"}
        };
    }
}
