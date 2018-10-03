package com.currencies.andre.currenciescalc.presenter.impl;

import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;
import com.currencies.andre.currenciescalc.model.Rates;
import com.currencies.andre.currenciescalc.model.Symbols;
import com.currencies.andre.currenciescalc.presenter.IFixerCalcPresenter;
import com.currencies.andre.currenciescalc.retrofit.IFixerRest;
import com.currencies.andre.currenciescalc.ui.mvp.IFixerCalcView;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FixerCalcPresenter extends MvpPresenter<IFixerCalcView> implements IFixerCalcPresenter {

    private final IFixerRest iFixerRest;
    private Symbols supportedSymbols = null;


    @Inject
    public FixerCalcPresenter(IFixerRest iFixerRest) {
        this.iFixerRest = iFixerRest;
    }


    @Override
    public void attachView(IFixerCalcView view) {
        super.attachView(view);

        if (isInRestoreState(getViewState()))
            return;


        final WeakReference<IFixerCalcView> viewWeakReference = new WeakReference<>(view);
        if (supportedSymbols == null) {
            iFixerRest.getSymbols()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Symbols>() {
                        @Override
                        public void accept(Symbols symbols) throws Exception {

                            FixerCalcPresenter.this.supportedSymbols = symbols;
                            if (viewWeakReference.get() != null)
                                viewWeakReference.get().setSupportedSymbols(FixerCalcPresenter.this.supportedSymbols);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getViewState().showError(throwable.getMessage());
                        }
                    });
        } else {
            view.setSupportedSymbols(supportedSymbols);
        }
    }


    public void calcAmount() {

    }


}
