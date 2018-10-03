package com.currencies.andre.currenciescalc.ui.mvp;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.currencies.andre.currenciescalc.model.Rates;
import com.currencies.andre.currenciescalc.model.Symbols;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IFixerCalcView extends MvpView {

    @StateStrategyType(SingleStateStrategy.class)
    public void setSupportedSymbols(Symbols symbols);

    @StateStrategyType(SkipStrategy.class)
    public void showError(String message);


    public void showLoading();


    public void showRates(Rates rates);


}
