package com.diskin.alon.appsbrowser.browser;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


/**
 * Browser screen ui controller.
 */
public class BrowserFragment extends Fragment {

    @Inject
    BrowserViewModel viewModel;


    public BrowserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browser, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // inject fragment dependencies
        AndroidSupportInjection.inject(this);

        // start observing user apps
        viewModel.getUserApps().observe(this,this::updateUserApps);
    }

    private void updateUserApps(@NonNull List<UserApp> userApps) {

    }
}
