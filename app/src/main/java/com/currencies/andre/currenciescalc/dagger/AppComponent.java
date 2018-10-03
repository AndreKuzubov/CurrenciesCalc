package com.currencies.andre.currenciescalc.dagger;

import com.currencies.andre.currenciescalc.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AllModules.class})
@Singleton
public interface AppComponent {

    void inject(MainActivity activity);

}