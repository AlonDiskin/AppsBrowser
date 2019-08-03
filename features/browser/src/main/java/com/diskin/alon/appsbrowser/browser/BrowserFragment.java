package com.diskin.alon.appsbrowser.browser;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


/**
 * Browser screen ui controller.
 */
public class BrowserFragment extends Fragment {

    @Inject
    BrowserViewModel viewModel;
    private UserAppsAdapter appsAdapter;

    public BrowserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browser, container, false);

        // setup user apps recycler view
        appsAdapter = new UserAppsAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.userApps);

        recyclerView.setAdapter(appsAdapter);
        return view;
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
        appsAdapter.updateApps(userApps);
    }
}
