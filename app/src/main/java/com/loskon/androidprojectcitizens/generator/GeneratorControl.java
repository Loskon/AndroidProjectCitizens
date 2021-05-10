package com.loskon.androidprojectcitizens.generator;

import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import com.loskon.androidprojectcitizens.ui.helper.SharedHelper;

import java.util.Timer;

/**
 * Таймер для запуска генератора (потока TimerTask)
 */

public class GeneratorControl {

    private static final String TAG = GeneratorControl.class.getSimpleName();

    private final Context context;

    private Timer timer;
    private final PendingIntent pendingIntent;

    public GeneratorControl(Context context, PendingIntent pendingIntent) {
        this.context = context;
        this.pendingIntent = pendingIntent;
    }

    public void startGen() {
        int period = SharedHelper.getGenPeriod(context);
        Log.d(TAG, "period: " + period);
        timer = new Timer();
        timer.schedule(new CitizenGeneratorTask(context, pendingIntent), 0, period * 1000);
    }

    public void stopGen() {
        timer.cancel();
        timer.purge();
    }
}

