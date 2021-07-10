package com.loskon.androidprojectcitizens.ui.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.core.content.res.ResourcesCompat;

import com.loskon.androidprojectcitizens.R;

/**
 * Помощник для работы с данными постоянного хранилища
 */

public class SharedHelper {

    // Keys
    public static final String PREF_KEY_GENERATION_PERIOD = "pref_key_generation_period";
    public static final String PREF_KEY_AGE_RANGE_MIN = "pref_key_age_range_min";
    public static final String PREF_KEY_AGE_RANGE_MAX = "pref_key_age_range_max";

    // Save
    public static void saveInt(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(key, value).apply();
    }

    // Load
    public static int loadInt(Context context, String key, int defValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defValue);
    }

    // Setters
    public static void setGenPeriod(Context context, int value) {
        saveInt(context, PREF_KEY_GENERATION_PERIOD, value);
    }

    public static void setAgeRangeMin(Context context, int value) {
        saveInt(context, PREF_KEY_AGE_RANGE_MIN, value);
    }

    public static void setAgeRangeMax(Context context, int value) {
        saveInt(context, PREF_KEY_AGE_RANGE_MAX, value);
    }

    // Getters
    public static int getGenerationPeriod(Context context) {
        int defValue = context.getResources().getInteger(R.integer.def_val_generation_period);
        return loadInt(context, PREF_KEY_GENERATION_PERIOD, defValue);
    }

    public static int getAgeRangeMin(Context context) {
        int defValue = context.getResources().getInteger(R.integer.def_val_range_min);
        return loadInt(context, PREF_KEY_AGE_RANGE_MIN, defValue);
    }

    public static int getAgeRangeMax(Context context) {
        int defValue = context.getResources().getInteger(R.integer.def_val_range_max);
        return loadInt(context, PREF_KEY_AGE_RANGE_MAX, defValue);
    }
}
