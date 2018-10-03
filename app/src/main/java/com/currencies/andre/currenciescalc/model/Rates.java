package com.currencies.andre.currenciescalc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Rates {

    @SerializedName("base")
    @Expose
    public String base;


    @SerializedName("date")
    @Expose
    public String date;

    @SerializedName("rates")
    @Expose
    public Map<String, Float> rates;


}
