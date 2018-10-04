package com.currencies.andre.currenciescalc;

import android.app.Application;

import com.currencies.andre.currenciescalc.dagger.AllModules;
import com.currencies.andre.currenciescalc.dagger.AppComponent;
import com.currencies.andre.currenciescalc.dagger.AppEnvironment;
import com.currencies.andre.currenciescalc.dagger.DaggerAppComponent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
                .allModules(new AllModules(
                        new AppEnvironment(getApplicationContext(), Schedulers.io(), AndroidSchedulers.mainThread()
                        )))
                .build();
    }
}
