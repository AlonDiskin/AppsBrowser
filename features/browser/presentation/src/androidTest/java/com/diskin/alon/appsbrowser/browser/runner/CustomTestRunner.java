package com.diskin.alon.appsbrowser.browser.runner;

import android.app.Application;
import android.content.Context;

import androidx.test.runner.AndroidJUnitRunner;

public class CustomTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return super.newApplication(cl, TestApp.class.getName(), context);
    }
}
