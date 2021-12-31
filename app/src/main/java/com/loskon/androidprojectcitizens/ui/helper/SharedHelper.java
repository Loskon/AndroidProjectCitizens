package com.loskon.androidprojectcitizens.ui.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.loskon.androidprojectcitizens.R;

/**
 * Помощник для работы с данными постоянного хранилища
 */

public class SharedHelper {

    public static final String PREF_KEY_GENERATION_PERIOD = "pref_key_generation_period";
    public static final String PREF_KEY_AGE_RANGE_MIN = "pref_key_age_range_min";
    public static final String PREF_KEY_AGE_RANGE_MAX = "pref_key_age_range_max";

    //----------------------------------------------------------------------------------------------
    public static void save(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(key, value).apply();
    }

    public static int load(Context context, String key, int defValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defValue);
    }

    //----------------------------------------------------------------------------------------------
    public static void setGenPeriod(Context context, int value) {
        save(context, PREF_KEY_GENERATION_PERIOD, value);
    }

    public static void setAgeRangeMin(Context context, int value) {
        save(context, PREF_KEY_AGE_RANGE_MIN, value);
    }

    public static void setAgeRangeMax(Context context, int value) {
        save(context, PREF_KEY_AGE_RANGE_MAX, value);
    }

    //----------------------------------------------------------------------------------------------
    public static int getGenerationPeriod(Context context) {
        int defValue = context.getResources().getInteger(R.integer.def_val_generation_period);
        return load(context, PREF_KEY_GENERATION_PERIOD, defValue);
    }

    public static int getAgeRangeMin(Context context) {
        int defValue = context.getResources().getInteger(R.integer.def_val_range_min);
        return load(context, PREF_KEY_AGE_RANGE_MIN, defValue);
    }

    public static int getAgeRangeMax(Context context) {
        int defValue = context.getResources().getInteger(R.integer.def_val_range_max);
        return load(context, PREF_KEY_AGE_RANGE_MAX, defValue);
    }
}
