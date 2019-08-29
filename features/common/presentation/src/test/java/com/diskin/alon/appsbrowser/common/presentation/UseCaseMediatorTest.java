package com.diskin.alon.appsbrowser.common.presentation;

import androidx.annotation.Nullable;

import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;
import com.diskin.alon.appsbrowser.common.applicationservices.UseCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

/**
 * {@link UseCaseMediator} unit test class.
 */
@RunWith(JUnitParamsRunner.class)
public class UseCaseMediatorTest {
    // System under test
    private UseCaseMediator mediator;

    @Before
    public void setUp() {
        mediator = new UseCaseMediator();

        buildTestDispatcher();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_whenRequestUnknown() {
        // Given an initialized mediator
        ServiceRequest<Void, List<Integer>> unknownRequest = new ServiceRequest<Void, List<Integer>>(null) {
            @Nullable
            @Override
            public Void getRequestParam() {
                return super.getRequestParam();
            }
        };

        // When mediator is asked to execute an unknown request
        mediator.execute(unknownRequest);

        // Then mediator should throw an IllegalArgumentException
    }

    @Test
    @Parameters(method = "params")
    public <P,R> void shouldExecuteUseCase_whenRequested(ServiceRequest<P,R> request, R result) {
        // Given an initialized mediator

        // When mediator is asked to execute a known request
        R executionResult = mediator.execute(request);

        // Then mediator should execute request and return expected result
        assertThat(executionResult,equalTo(result));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowException_whenAddingNullUseCases() {
        mediator.addUseCase(null,null);
        mediator.addMappedUseCase(null,null,null);
    }

    public static Object[] params() {
        return new Object[][] {
                new Object[] {new Service1Request(10),"string"},
                new Object[] {new Service2Request("str"),70},
                new Object[] {new Service3Request(null),"text"}
        };
    }

    private void buildTestDispatcher() {
        UseCase<Integer,String> useCase1 = Mockito.mock(UseCase.class);
        UseCase<String,Integer> useCase2 = Mockito.mock(UseCase.class);
        UseCase<Void,Integer> useCase3 = Mockito.mock(UseCase.class);
        Mapper<Integer,String> useCase3Mapper = Mockito.mock(Mapper.class);

        when(useCase1.execute(anyInt())).thenReturn("string");
        when(useCase2.execute(anyString())).thenReturn(70);
        when(useCase3.execute((Void) isNull())).thenReturn(10);
        when(useCase3Mapper.map(eq(10))).thenReturn("text");

        mediator.addUseCase(Service1Request.class,useCase1);
        mediator.addUseCase(Service2Request.class,useCase2);
        mediator.addMappedUseCase(Service3Request.class,useCase3,useCase3Mapper);
    }

    private final static class Service1Request extends ServiceRequest<Integer,String> {

        Service1Request(@Nullable Integer requestParam) {
            super(requestParam);
        }
    }

    private final static class Service2Request extends ServiceRequest<String,Integer> {

        Service2Request(@Nullable String requestParam) {
            super(requestParam);
        }
    }

    private final static class Service3Request extends ServiceRequest<Void,String> {

        Service3Request(@Nullable Void requestParam) {
            super(requestParam);
        }
    }
}
