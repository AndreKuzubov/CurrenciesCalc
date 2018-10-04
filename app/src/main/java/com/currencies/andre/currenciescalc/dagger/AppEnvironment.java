package com.currencies.andre.currenciescalc.dagger;

import android.content.Context;

import io.reactivex.Scheduler;

public class AppEnvironment {

    public final Context context;
    public final Scheduler ioScheduler;
    public final Scheduler mainScheduler;

    public AppEnvironment(Context context, Scheduler ioScheduler, Scheduler mainScheduler) {
        this.context = context;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
    }
}
