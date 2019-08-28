package com.diskin.alon.appsbrowser.browser.applicationservices;

import com.diskin.alon.appsbrowser.browser.applicationservices.model.UserAppDto;
import com.diskin.alon.appsbrowser.browser.applicationservices.util.UserAppEntityMapper;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * {@link UserAppEntityMapper} unit test class.
 */
@RunWith(JUnitParamsRunner.class)
public class UserAppEntityMapperTest {
    // System under test
    private UserAppEntityMapper mapper;

    @Before
    public void setUp() {
        mapper = new UserAppEntityMapper();
    }

    @Test
    @Parameters(method = "mapParams")
    public void shouldMapUserEntitiesToUserDtos_whenInvoked(UserAppEntity userAppEntity, UserAppDto expected) {
        // Given an initialized mapper

        // When client maps a user entity instance
        UserAppDto actualMapped = mapper.map(userAppEntity);

        // Then mapper should map the given user instance arg to user dto
        assertThat(actualMapped,equalTo(expected));
    }

    private static Object[][] mapParams() {
        return new Object[][] {new Object[] {
                new UserAppEntity("","",0,""),
                new UserAppDto("","",0,"")},
            new Object[] {new UserAppEntity("id1","name1",12.45,"url1"),
                    new UserAppDto("id1","name1",12.45,"url1")}};
    }
}
