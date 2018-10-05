package com.currencies.andre.currenciescalc.fixerpresenter;

import android.app.Application;

import com.currencies.andre.currenciescalc.dagger.AppEnvironment;
import com.currencies.andre.currenciescalc.model.Rates;
import com.currencies.andre.currenciescalc.model.Symbols;
import com.currencies.andre.currenciescalc.presenter.impl.FixerCalcPresenter;
import com.currencies.andre.currenciescalc.ui.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class MockyServerTest {

    @Mock
    private Application app;

    @Mock
    private MainActivity mainActivity;


    private TestScheduler testScheduler = new TestScheduler();

    private FixerRestMocky fixerRestMocky = Mockito.spy(new FixerRestMocky());

    private FixerCalcPresenter presenter;


    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        presenter = new FixerCalcPresenter(new AppEnvironment(app, testScheduler, testScheduler), fixerRestMocky);
    }


    @Test
    public void testSymblsErrorAnswers() throws NoSuchFieldException, IllegalAccessException {
        presenter.attachView(mainActivity);
        verify(mainActivity).showLoading();
        verifyNoMoreInteractions(mainActivity);

        testScheduler.triggerActions();
        verify(mainActivity).showErrorServerError();
        verifyNoMoreInteractions(mainActivity);
    }

    @Test
    public void testCacheSymbls() throws NoSuchFieldException, IllegalAccessException {
        Symbols symbols = new Symbols();
        symbols.symbols = new HashMap<>();

        fixerRestMocky.setAnswer(symbols);

        presenter.attachView(mainActivity);
        verify(mainActivity).showLoading();
        verifyNoMoreInteractions(mainActivity);
        testScheduler.triggerActions();
        verify(mainActivity).setSupportedSymbols(symbols);
        verifyNoMoreInteractions(mainActivity);
        clearInvocations(fixerRestMocky);


        presenter.detachView(mainActivity);
        presenter.attachView(mainActivity);
        testScheduler.triggerActions();
//        TODO fix into 1 invocation
        verify(mainActivity, times(2)).setSupportedSymbols(symbols);
        verifyNoMoreInteractions(mainActivity);
        verifyNoMoreInteractions(fixerRestMocky);
    }

    @Test
    public void testRatesErrorAnswers() throws NoSuchFieldException, IllegalAccessException {
        presenter.attachView(mainActivity);
        testScheduler.triggerActions();
        clearInvocations(mainActivity);

        for (String base : new String[]{null, "", "USD"})
            for (String target : new String[]{null, "", "USD"}) {
                if (target != null && base != null && target.length() > 0 && base.length() > 0)
                    break;
                presenter.calcAmount(base, target);

                try {
                    verify(mainActivity).showErrorNullInput();
                    verifyNoMoreInteractions(mainActivity);
                    clearInvocations(mainActivity);
                } catch (MockitoAssertionError e) {
                    throw new MockitoAssertionError("presenter.calcAmount(base, target) for " + base + ":" + target);
                }

            }


    }

    @Test
    public void testRates() throws NoSuchFieldException, IllegalAccessException {
        Rates rates = new Rates();
        rates.rates = new HashMap<>();

        fixerRestMocky.setAnswer(rates);

        presenter.attachView(mainActivity);
        testScheduler.triggerActions();
        clearInvocations(fixerRestMocky);
        clearInvocations(mainActivity);


        presenter.calcAmount("USD", "EUR");
        verify(mainActivity).showLoading();
        testScheduler.triggerActions();
        verify(mainActivity).showRates(rates);
        verifyNoMoreInteractions(mainActivity);
        verify(fixerRestMocky).getRates("USD");
        verifyNoMoreInteractions(fixerRestMocky);
    }


    @Test
    public void testViewState() throws NoSuchFieldException, IllegalAccessException {
        Rates rates = new Rates();
        rates.rates = new HashMap<>();

        fixerRestMocky.setAnswer(rates);

        presenter.attachView(mainActivity);
        testScheduler.triggerActions();
        clearInvocations(fixerRestMocky);
        clearInvocations(mainActivity);


        presenter.calcAmount("USD", "EUR");
        verify(mainActivity).showLoading();
        testScheduler.triggerActions();
        verify(mainActivity).showRates(rates);
        verifyNoMoreInteractions(mainActivity);
        verify(fixerRestMocky).getRates("USD");
        verifyNoMoreInteractions(fixerRestMocky);
    }

}
