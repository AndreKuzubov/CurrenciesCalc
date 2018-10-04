package com.currencies.andre.currenciescalc.ui.mvp;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.currencies.andre.currenciescalc.model.Rates;
import com.currencies.andre.currenciescalc.model.Symbols;

import java.util.List;

@StateStrategyType(value = SingleStateStrategy.class, tag = "loading")
public interface IFixerCalcView extends MvpView {


    @StateStrategyType(SkipStrategy.class)
    public void showErrorNullInput();

    @StateStrategyType(SkipStrategy.class)
    public void showErrorServerError();

    public void setSupportedSymbols(Symbols symbols);

    public void showLoading();

    public void showRates(Rates rates);

}
