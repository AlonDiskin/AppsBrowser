package com.diskin.alon.appsbrowser.browser.applicationservices;

import com.diskin.alon.appsbrowser.browser.applicationservices.interfaces.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.UserAppDto;
import com.diskin.alon.appsbrowser.browser.applicationservices.usecase.GetUserAppsUseCase;
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

        useCase = new GetUserAppsUseCase(repository,mapper);
    }

    @Test
    @Parameters(method = "executionParams")
    public void shouldFetchUserApps_whenExecuted(AppsSorting sorting, List<UserAppEntity> repositoryApps, List<UserAppDto> expectedApps) {
        // Given ann initialized use case, and existing apps in repository
        when(repository.getSortedApps(eq(sorting))).thenReturn(Observable.just(repositoryApps));

        // When use case is executed
        Observable<List<UserAppDto>> useCaseResult = useCase.execute(sorting);

        // Then use case should return an observable list user app dto, mapped from repository
        assertThat(useCaseResult.blockingFirst(),equalTo(expectedApps));
    }

    public static Object[][] executionParams() {
        return new Object[][] {
                new Object[] {new AppsSorting(AppsSorting.SortingType.NAME,true),new ArrayList<UserAppEntity>(),new ArrayList<UserAppDto>()},
                new Object[] {new AppsSorting(AppsSorting.SortingType.SIZE,true),Arrays.asList(
                        new UserAppEntity("id1","app1",23.4,"url1"),
                        new UserAppEntity("id2","app2",43.4,"url2")),
                        Arrays.asList(
                                new UserAppDto("id1","app1",23.4,"url1"),
                                new UserAppDto("id2","app2",43.4,"url2"))}
        };
    }
}
