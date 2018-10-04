package com.currencies.andre.currenciescalc.utils;

public class CurrecyCalculator {

    static public float calcDirectly(float base, float rate) {
        float result = base * rate;
        return (float) (Math.round(result * 100.0) / 100.0);
    }

    static public float calcReverse(float target, float rate) {
        float result = target / rate;
        return (float) (Math.round(result * 100.0) / 100.0);
    }
}
