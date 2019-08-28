package com.diskin.alon.appsbrowser.browser.applicationservices;

import com.diskin.alon.appsbrowser.browser.applicationservices.interfaces.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSearch;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting.SortingType;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.SearchResults;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.UserAppDto;
import com.diskin.alon.appsbrowser.browser.applicationservices.usecase.SearchAppsUseCase;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link SearchAppsUseCase} unit test class.
 */
@RunWith(JUnitParamsRunner.class)
public class SearchAppsUseCaseTest {
    // System under test
    private SearchAppsUseCase useCase;

    // Mocked collaborators
    @Mock
    public UserAppsRepository repository;
    @Mock
    public Mapper<UserAppEntity, UserAppDto> mapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // stub mapper
        when(mapper.map(ArgumentMatchers.any(UserAppEntity.class)))
                .thenAnswer(invocation -> {
                    UserAppEntity userApp = invocation.getArgument(0);

                    return new UserAppDto(userApp.getId(),
                            userApp.getName(),
                            userApp.getSize(),
                            userApp.getIconUri());});

        useCase = new SearchAppsUseCase(repository,mapper);
    }

    @Test
    @Parameters(method = "executionParams")
    public void shouldFetchSearchedApps_whenExecuted(AppsSearch searchParam, List<UserAppEntity> repositoryApps, SearchResults expectedResult) {
        // test case fixture
        when(repository.search(eq(searchParam.getSorting()),eq(searchParam.getQuery())))
                .thenReturn(Observable.just(repositoryApps));

        // Given an initialized use case

        // When use case is executed
        SearchResults actualResult = useCase.execute(searchParam).blockingFirst();

        // Then use case should fetch searched apps observable from repository
        verify(repository).search(eq(searchParam.getSorting()),eq(searchParam.getQuery()));

        // And return expected observable result to client
        assertThat(actualResult,equalTo(expectedResult));
    }

    public static Object[][] executionParams() {
        return new Object[][] {
                new Object[] {new AppsSearch(new AppsSorting(SortingType.NAME,true),"query1"),
                        new ArrayList<>(),new SearchResults(new ArrayList<>(),"query1")},
                new Object[] {new AppsSearch(new AppsSorting(SortingType.SIZE,false),"query2"),
                        Arrays.asList(new UserAppEntity("id1","app1",23.4,"url1"),
                                new UserAppEntity("id2","app2",43.4,"url2")),
                        new SearchResults(Arrays.asList(new UserAppDto("id1","app1",23.4,"url1"),
                                new UserAppDto("id2","app2",43.4,"url2")),"query2")}
        };
    }
}
