package com.currencies.andre.currenciescalc.retrofit;

import com.currencies.andre.currenciescalc.model.Rates;
import com.currencies.andre.currenciescalc.model.Symbols;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IFixerRest {

    static public final String FIXER_API_KEY = "e755cb4b3b8b1da2478f41bdea3aac81";


    @GET("/api/latest?access_key=" + FIXER_API_KEY)
    Observable<Rates> getRates(@Query("base") String base);

    @GET("/api/latest?access_key=" + FIXER_API_KEY)
    Call<Rates> getRatesResponse(@Query("base") String base);

    @GET("/api/symbols?access_key=" + FIXER_API_KEY)
    Observable<Symbols> getSymbols();


    @GET("/api/symbols?access_key=" + FIXER_API_KEY)
    Call<Symbols> getSymbolsResponse();

}
