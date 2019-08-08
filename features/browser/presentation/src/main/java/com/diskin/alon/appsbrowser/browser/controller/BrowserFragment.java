package com.diskin.alon.appsbrowser.browser.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.diskin.alon.appsbrowser.browser.R;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Browser screen ui controller.
 */
public class BrowserFragment extends Fragment {

    @Inject
    BrowserViewModel viewModel;
    @Inject
    BrowserNavigator navigator;
    private UserAppsAdapter appsAdapter;

    public BrowserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // inject fragment dependencies
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browser, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // setup user apps recycler view
        appsAdapter = new UserAppsAdapter(app -> navigator.openAppDetail(app.getPackageName()));
        RecyclerView recyclerView = view.findViewById(R.id.userApps);

        recyclerView.setAdapter(appsAdapter);

        // start observing user apps, once view is crated and able to show them
        viewModel.getUserApps().observe(this,this::updateUserApps);
    }

    private void updateUserApps(@NonNull List<UserApp> userApps) {
        // update apps layout adapter
        appsAdapter.updateApps(userApps);
    }
}
