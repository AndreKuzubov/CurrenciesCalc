package com.currencies.andre.currenciescalc.utils;

public class CurrecyCalculator {

    static public float calcDirectly(float base, float rate) {
        return base * rate;
    }

    static public float calcReverse(float target, float rate) {
        return target / rate;
    }
}
