package com.diskin.alon.appsbrowser.browser.controller;

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
import com.diskin.alon.appsbrowser.browser.applicationservices.AppsSorting;
import com.diskin.alon.appsbrowser.browser.applicationservices.AppsSorting.SortingType;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // inject fragment
        AndroidSupportInjection.inject(this);

        // resolving instance state sorting state
        // set default sorting values
        SortingType sorting = SortingType.NAME;
        boolean isAscending = true;

        if (savedInstanceState != null) {
            // if pref state exist, extract it
            sorting = SortingType.values()[savedInstanceState.getInt(KEY_SORT)];
            isAscending = savedInstanceState.getBoolean(KEY_ORDER);
        }

        // sort according to resolved instance sorting state
        viewModel.sortApps(new AppsSorting(sorting,isAscending));
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
        appsAdapter = new UserAppsAdapter(app -> navigator.openAppDetail(this,app.getPackageName()));

        RecyclerView recyclerView = view.findViewById(R.id.userApps);

        recyclerView.setAdapter(appsAdapter);

        // observe user apps from view model upon view creation
        viewModel.getUserApps().observe(getViewLifecycleOwner(),this::updateUserApps);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_browser,menu);
        // observe user sorting selection
        viewModel.getSorting().observe(this, appsSorting -> {
            switch (appsSorting.getType()) {
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

            menu.findItem(R.id.action_ascending).setChecked(appsSorting.isAscending());
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        AppsSorting currentSorting = Objects.requireNonNull(viewModel.getSorting().getValue(),
                "sorting state should be already set!");

        // pass sorting selection to view model
        if (id == R.id.action_sort_by_name && !item.isChecked()) {
            viewModel.sortApps(new AppsSorting(SortingType.NAME,currentSorting.isAscending()));

        } else if (id == R.id.action_sort_by_size && !item.isChecked()) {
            viewModel.sortApps(new AppsSorting(SortingType.SIZE,currentSorting.isAscending()));

        } else if (id == R.id.action_ascending) {
            viewModel.sortApps(new AppsSorting(currentSorting.getType(),!item.isChecked()));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // save sorting values state
        AppsSorting currentSorting = Objects.requireNonNull(viewModel.getSorting().getValue(),
                "sorting state should be already set!");

        outState.putInt(KEY_SORT,currentSorting.getType().ordinal());
        outState.putBoolean(KEY_ORDER,currentSorting.isAscending());
    }

    private void updateUserApps(@NonNull List<UserApp> userApps) {
        // update apps layout adapter
        appsAdapter.updateApps(userApps);
    }
}
