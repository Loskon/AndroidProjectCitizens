package com.loskon.androidprojectcitizens.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.loskon.androidprojectcitizens.generator.GeneratorControl;
import com.loskon.androidprojectcitizens.ui.fragments.SettingsFragment;

import static com.loskon.androidprojectcitizens.ui.helper.ServiceHelper.KEY_PENDING_INTENT;

/**
 * Сервис
 */

public class AppService extends Service implements SettingsFragment.CallbackSettings {

    private static final String TAG = "MyLogs_" + AppService.class.getSimpleName();

    private Context context;
    private GeneratorControl generatorControl;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        SettingsFragment.listenerCallback(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        starCommand(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void starCommand(Intent intent) {
        PendingIntent pendingIntent = intent.getParcelableExtra(KEY_PENDING_INTENT);
        generatorControl = new GeneratorControl(context, pendingIntent);
        generatorControl.start();
        Log.d(TAG, "onStartCommand()");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        generatorControl.stop();
        Log.d(TAG, "onDestroy()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Callback
    @Override
    public void onRestartGenerator() {
        // Перезапуск потока после изменения настроек
        generatorControl.restart();
    }
}