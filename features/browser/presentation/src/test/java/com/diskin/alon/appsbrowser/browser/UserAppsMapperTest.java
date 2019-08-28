package com.diskin.alon.appsbrowser.browser;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.UserAppDto;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.browser.util.UserAppDtosMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * {@link UserAppDtosMapper} unit test class.
 */
@RunWith(JUnitParamsRunner.class)
public class UserAppsMapperTest {
    // System under test
    private UserAppDtosMapper mapper;

    @Before
    public void setUp() {
        mapper = new UserAppDtosMapper();
    }

    @Test
    @Parameters(method = "mapParams")
    public void shouldMapObservableUserAppDtoListToUserAppsList(List<UserAppDto> userAppDtos, List<UserApp> expected) {
        // Given an initialized mapper

        // When mapper is asked to map an observable list of user app dtos
        List<UserApp> actualMapped = mapper.map(Observable.just(userAppDtos)).blockingFirst();

        // Then mapper should map input observable to observable  list of user apps
        assertThat(actualMapped,equalTo(expected));
    }

    private static Object[][] mapParams() {
        return new Object[][] {new Object[]{new ArrayList<>(),new ArrayList<>()},
                new Object[]{Arrays.asList(new UserAppDto("id1","name1",34.678,"url1")),
                        Arrays.asList(new UserApp("id1","name1","34.7 MB","url1"))}
        };
    }
}
