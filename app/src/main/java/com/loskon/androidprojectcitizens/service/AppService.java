package com.loskon.androidprojectcitizens.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.loskon.androidprojectcitizens.generator.GeneratorControl;
import com.loskon.androidprojectcitizens.ui.fragments.SettingsCallback;
import com.loskon.androidprojectcitizens.ui.fragments.SettingsFragment;

/**
 * Сервис
 */

public class AppService extends Service implements SettingsCallback {

    private GeneratorControl generatorControl;

    @Override
    public void onCreate() {
        super.onCreate();
        installCallback();
    }

    private void installCallback() {
        SettingsFragment.registerSettingsCallback(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        launchingGenerator();
        return super.onStartCommand(intent, flags, startId);
    }

    private void launchingGenerator() {
        generatorControl = new GeneratorControl(this);
        generatorControl.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        generatorControl.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Callback
    @Override
    public void onRestartGenerator() {
        generatorControl.restart();
    }
}