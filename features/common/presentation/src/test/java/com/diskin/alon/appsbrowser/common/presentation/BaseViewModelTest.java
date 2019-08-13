package com.diskin.alon.appsbrowser.common.presentation;

import org.junit.Test;

import io.reactivex.disposables.CompositeDisposable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * {@link BaseViewModel} unit test class.
 */
public class BaseViewModelTest {

    // System under test
    private BaseViewModel viewModel = new BaseViewModel(null) {};

    @Test
    public void shouldStopAllOngoingServiceExecutions_whenCleared() {
        // Given an initialized view model

        // When view model is cleared
        viewModel.onCleared();
        CompositeDisposable disposables = (CompositeDisposable) WhiteBox.getInternalState(viewModel,"disposables");

        // Then view model should dispose off all its observables
        assertThat(disposables.isDisposed(),equalTo(true));
    }
}