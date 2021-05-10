package com.loskon.androidprojectcitizens.ui.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.loskon.androidprojectcitizens.R;

/**
 * Помощник для работы с данными постоянного хранилища
 */

public class SharedHelper {

    public static final String KEY_GENERATION_PERIOD = "key_generation_period";
    public static final String KEY_AGE_RANGE_MIN = "key_age_range_min";
    public static final String KEY_AGE_RANGE_MAX = "key_age_range_max";

    // Save
    public static void saveInt(Context context, String key, int val) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, val);
        editor.apply();
    }

    public static void setGenPeriod(Context context, int val) {
        saveInt(context,KEY_GENERATION_PERIOD, val);
    }

    public static void setAgeRangeMin(Context context, int val) {
        saveInt(context,KEY_AGE_RANGE_MIN, val);
    }

    public static void setAgeRangeMax(Context context, int val) {
        saveInt(context,KEY_AGE_RANGE_MAX, val);
    }


    // Load
    public static int loadInt(Context context, String key, int defVal) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defVal);
    }

    public static int getGenPeriod(Context context) {
        int defVal = context.getResources().getInteger(R.integer.def_val_gen_period);
        return loadInt(context, KEY_GENERATION_PERIOD, defVal);
    }

    public static int getAgeRangeMin(Context context) {
        int deftVal = context.getResources().getInteger(R.integer.def_val_range_min);
        return loadInt(context, KEY_AGE_RANGE_MIN, deftVal);
    }

    public static int getAgeRangeMax(Context context) {
        int defVal = context.getResources().getInteger(R.integer.def_val_range_max);
        return loadInt(context, KEY_AGE_RANGE_MAX, defVal);
    }
}
