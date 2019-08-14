package com.diskin.alon.appsbrowser.browser.featuretest.steps;

import android.util.Log;

import androidx.test.core.app.ActivityScenario;

import com.diskin.alon.appsbrowser.browser.applicationservices.AppsSorting;
import com.diskin.alon.appsbrowser.browser.applicationservices.UserAppsRepository;
import com.diskin.alon.appsbrowser.browser.controller.BrowserFragment;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.common.presentation.FragmentTestActivity;
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import gherkin.ast.TableRow;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BackgroundSteps extends GreenCoffeeSteps {

    private static final String TAG = "BackgroundSteps";
    protected ActivityScenario<FragmentTestActivity> scenario;
    protected List<UserAppEntity> userAppEntities = new ArrayList<>();
    protected List<TableRow> appsTestData;

    @Inject
    public UserAppsRepository repository;

    protected void userHasTheNextAppsOnDevice(List<TableRow> userApps) {
        // set given user apps as the existing repository apps
        int nameCellIndex = 0;
        int sizeCellIndex = 1;
        int iconCellIndex = 2;
        int idCellIndex = 3;

        appsTestData = new ArrayList<>(userApps);
        appsTestData.remove(0);

        for (TableRow row : this.appsTestData) {
            String id = row.getCells().get(idCellIndex).getValue();
            String name = row.getCells().get(nameCellIndex).getValue();
            double size = Double.parseDouble(row.getCells().get(sizeCellIndex).getValue());
            String iconUri = row.getCells().get(iconCellIndex).getValue();

            userAppEntities.add(new UserAppEntity(id,name,size,iconUri));
        }

        // stub mocked repository
        when(repository.getUserApps(any(AppsSorting.class))).then(invocation -> {
            AppsSorting sorting = invocation.getArgument(0);
            switch (sorting.getType()) {
                case NAME:
                    Collections.sort(userAppEntities,(o1, o2) -> o1.getName().compareTo(o2.getName()));
                    break;

                case SIZE:
                    Collections.sort(userAppEntities,(o1, o2) -> Double.compare(o1.getSize(),o2.getSize()));
                    break;

                default:
                    break;
            }

            if (!sorting.isAscending()) {
                Collections.reverse(userAppEntities);
            }

            Log.d("VROT", "repository: " + sorting);

            return Observable.just(userAppEntities)
                    .subscribeOn(Schedulers.io());
        });
    }

    protected void userOpensBrowserScreen() {
        // launch browser screen
        scenario = ActivityScenario.launch(FragmentTestActivity.class);
        scenario.onActivity(activity -> activity.setFragment(new BrowserFragment(),TAG));
    }
}
