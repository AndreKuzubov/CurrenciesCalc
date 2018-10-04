package com.currencies.andre.currenciescalc.fixerpresenter;

import android.app.Application;

import com.currencies.andre.currenciescalc.model.Rates;
import com.currencies.andre.currenciescalc.model.Symbols;
import com.currencies.andre.currenciescalc.dagger.AppEnvironment;
import com.currencies.andre.currenciescalc.presenter.impl.FixerCalcPresenter;
import com.currencies.andre.currenciescalc.ui.MainActivity;
import com.currencies.andre.currenciescalc.ui.mvp.IFixerCalcView;
import com.currencies.andre.currenciescalc.ui.mvp.IFixerCalcView$$State;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MoxyStatesTest {

    @Mock
    private Application app;
    @Mock
    private MainActivity mainActivity;

    @Mock
    private MainActivity newActivity;


    private FixerRestMocky fixerRestMocky = Mockito.spy(new FixerRestMocky());

    private TestScheduler testScheduler = new TestScheduler();

    private FixerCalcPresenter presenter;
    private Symbols symbols = new Symbols();
    private Rates rates = new Rates();


    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        symbols.Symbols = new HashMap<>();
        rates.rates = new HashMap<>();
        fixerRestMocky.setAnswer(symbols);
        fixerRestMocky.setAnswer(rates);

        presenter = new FixerCalcPresenter(new AppEnvironment(app, testScheduler, testScheduler), fixerRestMocky);
        presenter.attachView(mainActivity);
        testScheduler.triggerActions();

        clearInvocations(mainActivity);
        clearInvocations(newActivity);
    }

    @Test
    public void testRestoreState() {
        ((IFixerCalcView$$State) presenter.getViewState()).attachView(newActivity);
        verify(newActivity).setSupportedSymbols(symbols);
        verifyNoMoreInteractions(newActivity);
    }

    @Test
    public void testSkipErrorsStates() {
        presenter.getViewState().showErrorNullInput();
        presenter.getViewState().showErrorServerError();

        ((IFixerCalcView$$State) presenter.getViewState()).attachView(newActivity);
        verify(newActivity).setSupportedSymbols(symbols);
        verifyNoMoreInteractions(newActivity);
    }


    @Test
    public void testRestoreLoadingState() {
        presenter.getViewState().showLoading();

        ((IFixerCalcView$$State) presenter.getViewState()).attachView(newActivity);
        verify(newActivity).showLoading();
        verifyNoMoreInteractions(newActivity);
    }

    @Test
    public void testSetRatesState() {
        presenter.getViewState().showRates(rates);

        ((IFixerCalcView$$State) presenter.getViewState()).attachView(newActivity);
        verify(newActivity).showRates(rates);
        verifyNoMoreInteractions(newActivity);
    }

}
