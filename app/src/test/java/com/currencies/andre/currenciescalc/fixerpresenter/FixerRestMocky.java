package com.currencies.andre.currenciescalc.fixerpresenter;

import com.currencies.andre.currenciescalc.model.Rates;
import com.currencies.andre.currenciescalc.model.Symbols;
import com.currencies.andre.currenciescalc.retrofit.IFixerRest;

import org.mockito.Mock;

import java.io.IOException;

import io.reactivex.Observable;
import retrofit2.Call;

public class FixerRestMocky  implements IFixerRest {

    private Rates rates;
    private Symbols symbols;

    public void setAnswer(Rates rates) {
        this.rates = rates;
    }


    public void setAnswer(Symbols symbols) {
        this.symbols = symbols;
    }

    @Override
    public Observable<Rates> getRates(String base) {
        if (rates == null)
            return Observable.error(new IOException());
        return Observable.just(rates);
    }

    @Override
    public Call<Rates> getRatesResponse(String base) {
        return null;
    }

    @Override
    public Observable<Symbols> getSymbols() {
        if (symbols == null)
            return Observable.error(new IOException());
        return Observable.just(symbols);
    }

    @Override
    public Call<Symbols> getSymbolsResponse() {
        return null;
    }
}
