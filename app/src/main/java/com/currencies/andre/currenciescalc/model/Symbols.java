package com.currencies.andre.currenciescalc.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Symbols {

    @SerializedName("symbols")
    @Expose
    public Map<String, String> Symbols;


}
