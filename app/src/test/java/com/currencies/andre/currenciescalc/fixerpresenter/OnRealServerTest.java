package com.currencies.andre.currenciescalc.fixerpresenter;

import android.app.Application;
import android.content.Context;
import android.test.AndroidTestCase;

import com.currencies.andre.currenciescalc.dagger.AllModules;
import com.currencies.andre.currenciescalc.dagger.AppComponent;
import com.currencies.andre.currenciescalc.dagger.AppEnvironment;
import com.currencies.andre.currenciescalc.dagger.DaggerAppComponent;
import com.currencies.andre.currenciescalc.model.Rates;
import com.currencies.andre.currenciescalc.model.Symbols;
import com.currencies.andre.currenciescalc.presenter.IFixerCalcPresenter;
import com.currencies.andre.currenciescalc.presenter.impl.FixerCalcPresenter;
import com.currencies.andre.currenciescalc.retrofit.IFixerRest;
import com.currencies.andre.currenciescalc.ui.MainActivity;
import com.currencies.andre.currenciescalc.ui.mvp.IFixerCalcView;
import com.currencies.andre.currenciescalc.ui.mvp.IFixerCalcView$$State;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

import java.lang.reflect.Field;

import javax.inject.Inject;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.TestScheduler;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import it.cosenonjaviste.daggermock.InjectFromComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class OnRealServerTest {

    @Mock
    private Application app;

    @Mock
    private MainActivity mainActivity;


    private FixerCalcPresenter presenter;
    private TestScheduler testScheduler = new TestScheduler();


    @BeforeClass
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        AppComponent appComponent = DaggerAppComponent
                .builder()
                .allModules(new AllModules(new AppEnvironment(app, testScheduler, testScheduler)))
                .build();
        appComponent.inject(mainActivity);
        try {
            Field f = MainActivity.class.getDeclaredField("fixerCalcPresenter");
            f.setAccessible(true);
            presenter = (FixerCalcPresenter) f.get(mainActivity);

        } catch (NoSuchFieldException | IllegalAccessException e) {
        }

        presenter.attachView(mainActivity);
        verify(mainActivity).showLoading();
        verifyNoMoreInteractions(mainActivity);

        testScheduler.triggerActions();
        Field f = FixerCalcPresenter.class.getDeclaredField("supportedSymbols");
        f.setAccessible(true);
        Symbols supportedSymbols = (Symbols) f.get(presenter);

        assertNotNull(supportedSymbols.Symbols);
        verify(mainActivity).setSupportedSymbols(supportedSymbols);
        verifyNoMoreInteractions(mainActivity);
    }


    @Test
    public void testErrorInput() throws NoSuchFieldException, IllegalAccessException {
        // TODO
        presenter.calcAmount("", "");
        testScheduler.triggerActions();
//        TODO
        verify(mainActivity).showErrorNullInput();
        verifyNoMoreInteractions(mainActivity);

    }


}