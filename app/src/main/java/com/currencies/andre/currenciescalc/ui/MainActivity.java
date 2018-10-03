package com.currencies.andre.currenciescalc.ui;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.currencies.andre.currenciescalc.App;
import com.currencies.andre.currenciescalc.R;
import com.currencies.andre.currenciescalc.model.Rates;
import com.currencies.andre.currenciescalc.model.Symbols;
import com.currencies.andre.currenciescalc.presenter.impl.FixerCalcPresenter;
import com.currencies.andre.currenciescalc.ui.mvp.IFixerCalcView;
import com.currencies.andre.currenciescalc.utils.CurrecyCalculator;
import com.evernote.android.state.State;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class MainActivity extends MvpActivity implements IFixerCalcView {

    @Inject
    @InjectPresenter
    FixerCalcPresenter fixerCalcPresenter;

    @BindView(R.id.et_base_amount)
    EditText etBaseAmount;
    @BindView(R.id.et_target_amount)
    EditText etTargetAmount;

    @BindView(R.id.sp_base_currency)
    Spinner spBaseCurrency;

    @BindView(R.id.sp_target_currency)
    Spinner spTargetCurrency;

    @BindView(R.id.pr_progress)
    ProgressBar prProgress;

    @BindView(R.id.tv_base_equals)
    TextView tvEqualsExpression1;

    @BindView(R.id.tv_equals_rate)
    TextView tvEqualsExpression2;


    @State
    float baseAmount;

    @State
    float targetAmount;

    @State
    float rate;

    @State
    String codeBaseCurrency, baseCurrencyName;

    @State
    String codeTargetCurrency, targetCurrencyName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        App.getAppComponent().inject(this);
        fixerCalcPresenter.attachView(this);
    }


    @Override
    public void setSupportedSymbols(Symbols symbols) {

        final List<String> allShortCurrencies = new ArrayList<>(symbols.Symbols.size());
        final List<String> allCurrencies = new ArrayList<>(symbols.Symbols.size());

        for (String key : symbols.Symbols.keySet()) {
            allShortCurrencies.add(key);
            allCurrencies.add(key + " " + symbols.Symbols.get(key));
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, allCurrencies);

        spBaseCurrency.setAdapter(adapter);
        spBaseCurrency.setSelection(allShortCurrencies.indexOf("EUR"));
        spBaseCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                codeBaseCurrency = allShortCurrencies.get(position);
                baseCurrencyName = allCurrencies.get(position);
                fixerCalcPresenter.calcAmount(codeBaseCurrency, codeTargetCurrency);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spTargetCurrency.setAdapter(adapter);
        spTargetCurrency.setSelection(allShortCurrencies.indexOf("USD"));
        spTargetCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                codeTargetCurrency = allShortCurrencies.get(position);
                targetCurrencyName = allCurrencies.get(position);
                fixerCalcPresenter.calcAmount(codeBaseCurrency, codeTargetCurrency);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @OnTextChanged(value = R.id.et_base_amount,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onBaseAmountChanged(Editable s) {
        if (!etBaseAmount.isFocused())
            return;
        try {
            baseAmount = Float.valueOf(s.toString());
        } catch (NumberFormatException e) {
            s.clear();
        }
        targetAmount = CurrecyCalculator.calcDirectly(baseAmount, rate);
        etTargetAmount.setText("" + targetAmount);
    }

    @OnTextChanged(value = R.id.et_target_amount,
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTargetAmountChanged(Editable s) {
        if (!etTargetAmount.isFocused())
            return;
        try {
            targetAmount = Float.valueOf(s.toString());

        } catch (NumberFormatException e) {
            s.clear();
        }
        baseAmount = CurrecyCalculator.calcReverse(baseAmount, rate);
        etBaseAmount.setText("" + baseAmount);
    }


    @Override
    public void showError(String message) {
        prProgress.setVisibility(View.INVISIBLE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        prProgress.setVisibility(View.VISIBLE);
        etBaseAmount.setEnabled(false);
        etTargetAmount.setEnabled(false);

        etBaseAmount.setText("");
        etTargetAmount.setText("");

        tvEqualsExpression1.setText(String.format("1 %s %s", baseCurrencyName, getString(R.string.equals)));
        tvEqualsExpression2.setText(targetCurrencyName);
    }

    @Override
    public void showRates(Rates rates) {
        if (!rates.base.equals(codeBaseCurrency) || !rates.rates.containsKey(codeTargetCurrency))
            return;
        rate = rates.rates.get(codeTargetCurrency);
        prProgress.setVisibility(View.INVISIBLE);

        etBaseAmount.setEnabled(true);
        etTargetAmount.setEnabled(true);

        tvEqualsExpression1.setText(String.format("1 %s %s", baseCurrencyName, getString(R.string.equals)));
        tvEqualsExpression2.setText(String.format("%s %s", rates.rates.get(codeTargetCurrency), targetCurrencyName));
    }

}
