package com.currencies.andre.currenciescalc.dagger;

import android.content.Context;

import com.currencies.andre.currenciescalc.presenter.IFixerCalcPresenter;
import com.currencies.andre.currenciescalc.presenter.impl.FixerCalcPresenter;
import com.currencies.andre.currenciescalc.retrofit.IFixerRest;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.PublicKey;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AllModules {

    static private final String HOST = "http://data.fixer.io";
    private final Context context;

    public AllModules(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    public IFixerCalcPresenter providePresenter(FixerCalcPresenter presenter) {
        return presenter;
    }


    @Provides
    public IFixerRest provideRest() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(HOST)
                .build()
                .create(IFixerRest.class);
    }
}
