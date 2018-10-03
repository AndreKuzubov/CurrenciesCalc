package com.currencies.andre.currenciescalc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Rates {

    @SerializedName("base")
    @Expose
    String base;


    @SerializedName("date")
    @Expose
    String date;

    @SerializedName("rates")
    @Expose
    Map<String, Float> Rates;


}
