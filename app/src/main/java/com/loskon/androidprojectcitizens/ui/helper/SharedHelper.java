package com.loskon.androidprojectcitizens.ui.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Помощник для работы с данными постоянного хранилища
 */

public class SharedHelper {

    public static final String KEY_GENERATION_PERIOD = "key_generation_period";
    public static final String KEY_AGE_RANGE_MIN = "key_age_range_min";
    public static final String KEY_AGE_RANGE_MAX = "key_age_range_max";

    // SharedPreferences
    public static void saveInt(Context context, String key, int val) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(key, val).apply();
    }

    public static int loadInt(Context context, String key, int defVal) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defVal);
    }

    // Get value SharedPreferences
    public static int getValPeriod(Context context) {
        return loadInt(context, KEY_GENERATION_PERIOD, 10);
    }

    public static int getValRangeMin(Context context) {
        return loadInt(context, KEY_AGE_RANGE_MIN, 22);
    }

    public static int getValRangeMax(Context context) {
        return loadInt(context, KEY_AGE_RANGE_MAX, 35);
    }
}
