package com.currencies.andre.currenciescalc.fixerpresenter;

import android.app.Application;

import com.currencies.andre.currenciescalc.model.Rates;
import com.currencies.andre.currenciescalc.model.Symbols;
import com.currencies.andre.currenciescalc.dagger.AppEnvironment;
import com.currencies.andre.currenciescalc.presenter.impl.FixerCalcPresenter;
import com.currencies.andre.currenciescalc.ui.mvp.IFixerCalcView;
import com.currencies.andre.currenciescalc.ui.mvp.IFixerCalcView$$State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;

import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class MoxyStatesTest {

    @Mock
    private Application app;
    @Mock
    private IFixerCalcView view;
    @Mock
    private IFixerCalcView newView;


    private FixerRestMocky fixerRestMocky = Mockito.spy(new FixerRestMocky());

    private TestScheduler testScheduler = new TestScheduler();

    private FixerCalcPresenter presenter;
    private Symbols symbols = new Symbols();
    private Rates rates = new Rates();


    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        symbols.symbols = new HashMap<>();
        rates.rates = new HashMap<>();
        fixerRestMocky.setAnswer(symbols);
        fixerRestMocky.setAnswer(rates);

        presenter = new FixerCalcPresenter(new AppEnvironment(app, testScheduler, testScheduler), fixerRestMocky);
        presenter.attachView(view);
        testScheduler.triggerActions();

        clearInvocations(view);
        clearInvocations(newView);
    }

    @Test
    public void testRestoreState() {
        ((IFixerCalcView$$State) presenter.getViewState()).attachView(newView);
        verify(newView).setSupportedSymbols(symbols);
        verifyNoMoreInteractions(newView);
    }

    @Test
    public void testSkipErrorsStates() {
        presenter.getViewState().showErrorNullInput();
        presenter.getViewState().showErrorServerError();

        ((IFixerCalcView$$State) presenter.getViewState()).attachView(newView);
        verify(newView).setSupportedSymbols(symbols);
        verifyNoMoreInteractions(newView);
    }


    @Test
    public void testRestoreLoadingState() {
        presenter.getViewState().showLoading();

        ((IFixerCalcView$$State) presenter.getViewState()).attachView(newView);
        verify(newView).showLoading();
        verifyNoMoreInteractions(newView);
    }

    @Test
    public void testSetRatesState() {
        presenter.getViewState().showRates(rates);

        ((IFixerCalcView$$State) presenter.getViewState()).attachView(newView);
        verify(newView).showRates(rates);
        verifyNoMoreInteractions(newView);
    }

}
