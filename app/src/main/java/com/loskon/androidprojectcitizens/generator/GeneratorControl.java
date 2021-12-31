package com.loskon.androidprojectcitizens.generator;

import android.content.Context;

import com.loskon.androidprojectcitizens.ui.helper.SharedHelper;

import java.util.Timer;

/**
 * Таймер для запуска генератора (потока TimerTask)
 */

public class GeneratorControl {

    private final Context context;

    private Timer timer;

    public GeneratorControl(Context context) {
        this.context = context;
    }

    public void start() {
        int period = SharedHelper.getGenerationPeriod(context);
        timer = new Timer();
        timer.schedule(new GeneratorTask(context), 0, period * 1000L);
    }

    public void stop() {
        timer.cancel();
        timer.purge();
    }

    public void restart() {
        stop();
        start();
    }
}

