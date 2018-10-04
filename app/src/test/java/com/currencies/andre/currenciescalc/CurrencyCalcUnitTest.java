package com.currencies.andre.currenciescalc;

import com.currencies.andre.currenciescalc.utils.CurrecyCalculator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class CurrencyCalcUnitTest {


    @Test
    public void currencyCalculatorTest() {

        // 1 EUR = 1.15 USD
        assertEquals(1.15f, CurrecyCalculator.calcDirectly(1f, 1.15f), 0);
        // 5 EUR = 5.74 USD
        assertEquals(5.75f, CurrecyCalculator.calcDirectly(5f, 1.15f), 0);
        // 1 USD = 0.87 EUR
        assertEquals(0.87f, CurrecyCalculator.calcReverse(1f, 1.15f), 0);
        // 4 USD = 3.48 EUR
        assertEquals(3.48f, CurrecyCalculator.calcReverse(4f, 1.15f), 0);

    }
}
