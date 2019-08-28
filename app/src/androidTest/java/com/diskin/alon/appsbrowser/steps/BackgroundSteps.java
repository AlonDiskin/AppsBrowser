package com.diskin.alon.appsbrowser.steps;

import com.diskin.alon.appsbrowser.util.Device;
import com.mauriciotogneri.greencoffee.GreenCoffeeSteps;

public class BackgroundSteps extends GreenCoffeeSteps {
    public void userIsInDeviceHomeScreen() {
        Device.openHomeScreen();
    }


    public void userLaunchApplication() {
        Device.launchApp();
    }
}
