package com.currencies.andre.currenciescalc;

import android.app.Application;

import com.currencies.andre.currenciescalc.dagger.AllModules;
import com.currencies.andre.currenciescalc.dagger.AppComponent;
import com.currencies.andre.currenciescalc.dagger.DaggerAppComponent;

public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent
                .builder()
                .allModules(new AllModules(getApplicationContext()))
                .build();
    }
}
