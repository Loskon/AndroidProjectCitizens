package com.loskon.androidprojectcitizens.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.loskon.androidprojectcitizens.generator.GeneratorControl;
import com.loskon.androidprojectcitizens.ui.fragment.SettingsFragment;

import static com.loskon.androidprojectcitizens.ui.helper.ServiceHelper.KEY_PENDING_INTENT;

/**
 * Сервис
 */

public class MyService extends Service implements SettingsFragment.CallbackSettings {

    private static final String TAG = MyService.class.getSimpleName();

    private Context context;
    private GeneratorControl generatorControl;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        SettingsFragment.registerCallbackSettings(this);
    }

    @Override
    public void onCallbackSettings() {
        // Перезапуск потока после изменения настроек
        stopGenerator();
        startGenerator();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        starCommand(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void starCommand(Intent intent) {
        PendingIntent pendingIntent = intent.getParcelableExtra(KEY_PENDING_INTENT);
        generatorControl = new GeneratorControl(context, pendingIntent);
        startGenerator();
        Log.d(TAG, "Service onStartCommand()");
    }

    private void startGenerator() {
        generatorControl.startGen();
    }

    private void stopGenerator() {
        generatorControl.stopGen();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopGenerator();
        Log.d(TAG, "Service onDestroy()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}