package com.loskon.androidprojectcitizens.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import com.loskon.androidprojectcitizens.model.Citizen;

import java.util.ArrayList;

import static com.loskon.androidprojectcitizens.ui.activity.MainActivity.KEY_EXTRA;
import static com.loskon.androidprojectcitizens.ui.activity.MainActivity.KEY_PENDING_INTENT;
import static com.loskon.androidprojectcitizens.ui.activity.MainActivity.KEY_SERIALIZABLE;
import static com.loskon.androidprojectcitizens.ui.activity.MainActivity.RESULT_SERVICE_OK;

/**
 * Сервис
 */

public class LocalService extends Service {

    private PendingIntent pendingIntent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
        pendingIntent = intent.getParcelableExtra(KEY_PENDING_INTENT);
        new LoadWordsThread().start();
        return super.onStartCommand(intent, flags, startId);
    }

    class LoadWordsThread extends Thread {
        @Override
        public void run() {

            ArrayList<Citizen> object = new ArrayList<>();
            Citizen user = new Citizen();
            user.setFirstName("s");
            user.setLastName("s2");
            user.setMale(false);
            user.setAgeVal(23);
            user.setWorkName("s3");
            user.setCar(false);
            object.add(user);

            Intent intent = new Intent();
            Bundle args = new Bundle();
            args.putSerializable(KEY_SERIALIZABLE, object);
            intent.putExtra(KEY_EXTRA, args);


            try {
                pendingIntent.send(LocalService.this, RESULT_SERVICE_OK, intent);
            } catch (PendingIntent.CanceledException exception) {
                exception.printStackTrace();
            }

            SystemClock.sleep(4000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "close", Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}