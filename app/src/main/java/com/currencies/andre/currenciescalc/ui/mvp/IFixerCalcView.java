package com.currencies.andre.currenciescalc.ui.mvp;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.currencies.andre.currenciescalc.model.Symbols;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IFixerCalcView extends MvpView {

    public void setSupportedSymbols(Symbols symbols);

    public void showError(String message);


    public void showLoading();


}
