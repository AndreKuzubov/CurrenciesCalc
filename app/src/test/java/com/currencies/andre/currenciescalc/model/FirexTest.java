package com.currencies.andre.currenciescalc.model;

import android.app.Application;

import com.currencies.andre.currenciescalc.dagger.AllModules;
import com.currencies.andre.currenciescalc.dagger.AppEnvironment;
import com.currencies.andre.currenciescalc.retrofit.IFixerRest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FirexTest {

    @Mock
    private Application app;

    private TestScheduler testScheduler = new TestScheduler();

    private IFixerRest iFixerRest;


    @Before
    public void testGetSymbls() throws NoSuchFieldException, IllegalAccessException {
        AllModules allModules = new AllModules(new AppEnvironment(app, testScheduler, testScheduler));
        iFixerRest = allModules.provideRest();
    }

    @Test
    public void testGetSybls() {
        Symbols symbols = iFixerRest.getSymbols().blockingFirst();
        assertNotNull(symbols.symbols);
    }


    @Test
    public void testGetRates() {
        Rates rates = iFixerRest.getRates("EUR").blockingFirst();
        assertNotNull(rates.rates);
    }
}
