package com.loskon.androidprojectcitizens.ui.helper;

import android.app.PendingIntent;
import android.content.Intent;

import com.loskon.androidprojectcitizens.service.MyService;
import com.loskon.androidprojectcitizens.ui.activity.MainActivity;

/**
 * Помощник для работы с Сервисом
 */

public class ServiceHelper {

    public static final String KEY_EXTRA = "key_put_extra";
    public static final String KEY_SERIALIZABLE = "key_citizen_array_list";
    public static final String KEY_PENDING_INTENT = "key_pending_intent";

    public static final int REQUEST_SERVICE = 451;
    public static final int RESULT_SERVICE_OK = 452;

    private final MainActivity activity;

    public ServiceHelper(MainActivity activity) {
        this.activity = activity;
    }

    public void startService() {
        PendingIntent pendingIntent = activity.createPendingResult(REQUEST_SERVICE, new Intent(), 0);
        Intent intent = new Intent(activity, MyService.class);
        intent.putExtra(KEY_PENDING_INTENT, pendingIntent);
        activity.startService(intent);
    }

    public void stopService() {
        activity.stopService(new Intent(activity, MyService.class));
    }
}
