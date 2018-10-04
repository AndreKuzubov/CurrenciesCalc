package com.currencies.andre.currenciescalc.dagger;

import android.content.Context;
import android.os.Environment;

import com.currencies.andre.currenciescalc.presenter.IFixerCalcPresenter;
import com.currencies.andre.currenciescalc.presenter.impl.FixerCalcPresenter;
import com.currencies.andre.currenciescalc.retrofit.IFixerRest;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.PublicKey;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AllModules {

    static private final String HOST = "http://data.fixer.io";
    private final AppEnvironment environment;

    public AllModules(AppEnvironment environment) {
        this.environment = environment;
    }

    @Provides
    AppEnvironment provideEnvironment() {
        return environment;
    }

    @Provides
    IFixerCalcPresenter providePresenter(FixerCalcPresenter presenter) {
        return presenter;
    }


    @Provides
    IFixerRest provideRest() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HOST)
                .build()
                .create(IFixerRest.class);
    }
}
