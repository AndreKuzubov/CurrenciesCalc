package com.currencies.andre.currenciescalc.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.currencies.andre.currenciescalc.R;
import com.currencies.andre.currenciescalc.dagger.AppEnvironment;
import com.currencies.andre.currenciescalc.model.Rates;
import com.currencies.andre.currenciescalc.model.Symbols;
import com.currencies.andre.currenciescalc.presenter.IFixerCalcPresenter;
import com.currencies.andre.currenciescalc.retrofit.IFixerRest;
import com.currencies.andre.currenciescalc.ui.mvp.IFixerCalcView;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class FixerCalcPresenter extends MvpPresenter<IFixerCalcView> implements IFixerCalcPresenter {

    private final IFixerRest iFixerRest;
    private final Context context;
    private final Scheduler ioScheduler;
    private final Scheduler mainScheduler;
    private Symbols supportedSymbols = null;


    @Inject
    public FixerCalcPresenter(AppEnvironment environment, IFixerRest iFixerRest) {
        this.iFixerRest = iFixerRest;
        this.context = environment.context;
        this.ioScheduler = environment.ioScheduler;
        this.mainScheduler = environment.mainScheduler;
    }


    @Override
    public void attachView(IFixerCalcView originalView) {
        super.attachView(originalView);
        if (isInRestoreState(getViewState()))
            return;
        final IFixerCalcView view =getViewState();
        if (supportedSymbols == null) {
            view.showLoading();
            iFixerRest.getSymbols()
                    .subscribeOn(ioScheduler)
                    .observeOn(mainScheduler)
                    .subscribe(new Consumer<Symbols>() {
                        @Override
                        public void accept(Symbols symbols) throws Exception {
                            FixerCalcPresenter.this.supportedSymbols = symbols;
                            view.setSupportedSymbols(FixerCalcPresenter.this.supportedSymbols);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.showErrorServerError();
                        }
                    });
        } else {
            view.setSupportedSymbols(supportedSymbols);
        }
    }


    public void calcAmount(String base, String target) {
        final IFixerCalcView view = getViewState();
        if (base == null || base.length() == 0 || target == null || target.length() == 0) {
            view.showErrorNullInput();
            return;
        }
        view.showLoading();
        iFixerRest.getRates(base)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(new Consumer<Rates>() {
                    @Override
                    public void accept(Rates rates) throws Exception {
                        view.showRates(rates);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showErrorServerError();
                    }
                });

    }


}
