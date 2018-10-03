package com.currencies.andre.currenciescalc.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.currencies.andre.currenciescalc.App;
import com.currencies.andre.currenciescalc.R;
import com.currencies.andre.currenciescalc.model.Symbols;
import com.currencies.andre.currenciescalc.presenter.impl.FixerCalcPresenter;
import com.currencies.andre.currenciescalc.ui.mvp.IFixerCalcView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends MvpActivity implements IFixerCalcView {

    @Inject
    @InjectPresenter
    FixerCalcPresenter fixerCalcPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getAppComponent().inject(this);
        fixerCalcPresenter.attachView(this);
    }

    @Override
    public void setSupportedSymbols(Symbols symbols) {
        StringBuilder s = new StringBuilder();
        for (String key : symbols.Symbols.keySet()) {
            s.append("\n").append(key).append(": ").append(symbols.Symbols.get(key));
        }
        Log.i("myLog", "symbols:" + s.toString());

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showLoading() {

    }
}
