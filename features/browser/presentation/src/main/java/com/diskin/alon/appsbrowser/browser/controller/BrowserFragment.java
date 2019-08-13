package com.diskin.alon.appsbrowser.browser.controller;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.diskin.alon.appsbrowser.browser.R;
import com.diskin.alon.appsbrowser.browser.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModel;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Browser screen ui controller.
 */
public class BrowserFragment extends Fragment {

    // saved instance state keys
    private static final String KEY_SORT = "sort";
    private static final String KEY_ORDER = "order";

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

        // resolving instance state and start observing user apps
        if (savedInstanceState == null) {
            // if no prev state exist, fetch apps sorted by name in ascending order
            viewModel.getUserApps(AppsSorting.NAME,true).observe(this,this::updateUserApps);
        } else {
            // prev state exist, extract it
            AppsSorting sorting = AppsSorting.values()[savedInstanceState.getInt(KEY_SORT)];
            boolean isAscending = savedInstanceState.getBoolean(KEY_ORDER);

            viewModel.getUserApps(sorting,isAscending);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_browser,menu);
        // observe user sorting selections
        viewModel.getAppsSorting().observe(this,appsSorting -> {
            switch (appsSorting) {
                case NAME:
                    menu.findItem(R.id.action_sort_by_name)
                            .setChecked(true);
                    break;

                case SIZE:
                    menu.findItem(R.id.action_sort_by_size)
                            .setChecked(true);
                    break;

                default:
                    break;
            }
        });

        viewModel.getAscending().observe(this,ascending ->
                menu.findItem(R.id.action_ascending).setChecked(ascending));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // pass sorting selection to view model
        if (id == R.id.action_sort_by_name && !item.isChecked()) {
            viewModel.sortApps(AppsSorting.NAME);

        } else if (id == R.id.action_sort_by_size) {
            viewModel.sortApps(AppsSorting.SIZE);

        } else if (id == R.id.action_ascending) {
            viewModel.orderApps(!item.isChecked());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // save sorting values state
        Objects.requireNonNull(viewModel.getAppsSorting().getValue());
        Objects.requireNonNull(viewModel.getAscending().getValue());

        outState.putInt(KEY_SORT,viewModel.getAppsSorting().getValue().ordinal());
        outState.putBoolean(KEY_ORDER,viewModel.getAscending().getValue());
    }

    private void updateUserApps(@NonNull List<UserApp> userApps) {
        // update apps layout adapter
        appsAdapter.updateApps(userApps);
    }
}
