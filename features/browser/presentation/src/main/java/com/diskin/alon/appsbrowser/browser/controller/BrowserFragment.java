package com.diskin.alon.appsbrowser.browser.controller;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.diskin.alon.appsbrowser.browser.R;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.AppsSorting.SortingType;
import com.diskin.alon.appsbrowser.browser.viewmodel.BrowserViewModel;

import java.util.Objects;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Browser screen ui controller.
 */
public class BrowserFragment extends Fragment {
    // saved instance keys
    private static final String KEY_SORT = "sort";
    private static final String KEY_ORDER = "order";
    private static final String KEY_QUERY = "query";
    private static final String KEY_SEARCH_OPEN = "open";

    @Inject
    BrowserViewModel viewModel;
    @Inject
    BrowserNavigator navigator;
    private boolean isSearchViewExpanded = false;

    public BrowserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // inject fragment
        AndroidSupportInjection.inject(this);

        // set default sorting values
        SortingType sorting = SortingType.NAME;
        boolean isAscending = true;

        // resolve instance state
        if (savedInstanceState != null) {
            // if pref state exist, extract it
            sorting = SortingType.values()[savedInstanceState.getInt(KEY_SORT)];
            isAscending = savedInstanceState.getBoolean(KEY_ORDER);
            String query = savedInstanceState.getString(KEY_QUERY);
            isSearchViewExpanded = savedInstanceState.getBoolean(KEY_SEARCH_OPEN);

            // if prev query exist,set it to view model
            if (query != null) {
                viewModel.searchApps(query);
            }
        }

        // sort user apps
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

        // setup apps recycler view
        UserAppsAdapter appsAdapter = new UserAppsAdapter(app -> navigator.openAppDetail(this,app.getPackageName()));
        RecyclerView recyclerView = view.findViewById(R.id.userApps);

        recyclerView.setAdapter(appsAdapter);

        // observe user apps from view model upon view creation
        viewModel.getUserApps().observe(getViewLifecycleOwner(), userApps -> {
            // save and restore recycler vew scroll position, to prevent auto scroll jumps
            // during updates
            //noinspection ConstantConditions
            Parcelable recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            appsAdapter.updateApps(userApps);
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_browser,menu);
        // setup sorting menu according to view model state
        switch (viewModel.getAppsSorting().getType()) {
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

        menu.findItem(R.id.action_ascending).setChecked(viewModel.getAppsSorting().isAscending());

        // configure the searchApps view
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        // restore search view state if needed
        if (isSearchViewExpanded) {
            searchItem.expandActionView();
            searchView.setQuery(viewModel.getSearchQuery(),false);
        }

        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // prevent updates when fragment is replaced in back stack
                if (BrowserFragment.this.isResumed()) {
                    viewModel.searchApps(newText);
                }

                return true;
            }
        });
        searchItem.setOnActionExpandListener(new OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                isSearchViewExpanded = true;
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                isSearchViewExpanded = false;
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        AppsSorting currentSorting = Objects.requireNonNull(viewModel.getAppsSorting(),
                "sorting state should be already set!");

        // pass sorting selection to view model
        if (id == R.id.action_sort_by_name && !item.isChecked()) {
            item.setChecked(true);
            viewModel.sortApps(new AppsSorting(SortingType.NAME,currentSorting.isAscending()));

        } else if (id == R.id.action_sort_by_size && !item.isChecked()) {
            item.setChecked(true);
            viewModel.sortApps(new AppsSorting(SortingType.SIZE,currentSorting.isAscending()));

        } else if (id == R.id.action_ascending) {
            item.setChecked(!item.isChecked());
            viewModel.sortApps(new AppsSorting(currentSorting.getType(),item.isChecked()));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        AppsSorting currentSorting = Objects.requireNonNull(viewModel.getAppsSorting(),
                "sorting state should be already set!");

        outState.putInt(KEY_SORT,currentSorting.getType().ordinal());
        outState.putBoolean(KEY_ORDER,currentSorting.isAscending());
        outState.putString(KEY_QUERY,viewModel.getSearchQuery());
        outState.putBoolean(KEY_SEARCH_OPEN,isSearchViewExpanded);
    }
}
