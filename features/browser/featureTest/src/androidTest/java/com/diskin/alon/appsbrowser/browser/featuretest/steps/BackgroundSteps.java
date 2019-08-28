package com.diskin.alon.appsbrowser.browser.featuretest.steps;

import androidx.test.core.app.ActivityScenario;

import com.diskin.alon.appsbrowser.browser.controller.BrowserFragment;
import com.diskin.alon.appsbrowser.browser.data.AppsDataStore;
import com.diskin.alon.appsbrowser.browser.domain.UserAppEntity;
import com.diskin.alon.appsbrowser.common.presentation.FragmentTestActivity;
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import gherkin.ast.TableRow;
import io.reactivex.Observable;

import static org.mockito.Mockito.when;

/**
 * Common background in 'browser' feature steps definitions.
 */
public class BackgroundSteps extends GreenCoffeeSteps {
    protected static final String TAG = "BackgroundSteps";
    protected ActivityScenario<FragmentTestActivity> scenario;
    private List<UserAppEntity> userAppEntities = new ArrayList<>();
    @Inject
    public AppsDataStore appsDataStore;

    protected void userHasTheNextAppsOnDevice(List<TableRow> userApps) {
        List<TableRow> appsTestData  = new ArrayList<>(userApps);
        int nameCellIndex = 0;
        int sizeCellIndex = 1;
        int iconCellIndex = 2;
        int idCellIndex = 3;

        // remove firs row containing column names(not needed)
        appsTestData.remove(0);

        // extract user apps from test table param
        for (TableRow row : appsTestData) {
            String id = row.getCells().get(idCellIndex).getValue();
            String name = row.getCells().get(nameCellIndex).getValue();
            double size = Double.parseDouble(row.getCells().get(sizeCellIndex).getValue());
            String iconUri = row.getCells().get(iconCellIndex).getValue();

            userAppEntities.add(new UserAppEntity(id,name,size,iconUri));
        }

        // stub mocked apps data store with test apps
        when(appsDataStore.getAll()).thenReturn(Observable.just(userAppEntities));
    }

    protected void userOpensBrowserScreen() {
        // launch browser screen
        scenario = ActivityScenario.launch(FragmentTestActivity.class);
        scenario.onActivity(activity -> activity.setFragment(new BrowserFragment(),TAG));
    }

    protected List<UserAppEntity> getTestUserApp() {
        return new ArrayList<>(this.userAppEntities);
    }
}
