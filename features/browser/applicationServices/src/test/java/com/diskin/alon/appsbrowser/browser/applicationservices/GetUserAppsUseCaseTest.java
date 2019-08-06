package com.diskin.alon.appsbrowser.browser.applicationservices;

import com.diskin.alon.appsbrowser.browser.domain.UserApp;
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

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link GetUserAppsUseCase} unit test class.
 */
@RunWith(JUnitParamsRunner.class)
public class GetUserAppsUseCaseTest {

    // System under test
    private GetUserAppsUseCase useCase;

    // Mocked SUT dependencies
    @Mock
    public UserAppsRepository repository;
    @Mock
    public Mapper<UserApp,UserAppDto> mapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        // stub mapper
        when(mapper.map(ArgumentMatchers.any(UserApp.class)))
                .thenAnswer(invocation -> {
                    UserApp userApp = invocation.getArgument(0);

                    return new UserAppDto(userApp.getId(),
                            userApp.getName(),
                            userApp.getSize(),
                            userApp.getIconUri());});

        useCase = new GetUserAppsUseCase(repository,mapper);
    }

    @Test
    @Parameters(method = "executionParams")
    public void shouldFetchUserApps_whenExecuted(List<UserApp> repositoryApps,List<UserAppDto> expectedApps) {
        // Given ann initialized use case, and existing apps in repository
        when(repository.getUserAppsByName()).thenReturn(Observable.just(repositoryApps));

        // When use case is executed
        Observable<List<UserAppDto>> useCaseResult = useCase.execute(null);

        // Then use case should return an observable mapped list of repository apps
        assertThat(useCaseResult.blockingFirst(),equalTo(expectedApps));
    }

    public static Object[][] executionParams() {
        return new Object[][] {
                new Object[] {new ArrayList<UserApp>(),new ArrayList<UserAppDto>()},
                new Object[] {Arrays.asList(
                        new UserApp("id1","app1",23.4,"url1"),
                        new UserApp("id2","app2",43.4,"url2")),
                        Arrays.asList(
                                new UserAppDto("id1","app1",23.4,"url1"),
                                new UserAppDto("id2","app2",43.4,"url2"))}
        };
    }
}
