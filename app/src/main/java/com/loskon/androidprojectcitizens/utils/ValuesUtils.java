package com.loskon.androidprojectcitizens.utils;

import android.content.Context;

import com.loskon.androidprojectcitizens.R;

public class ValuesUtils {

    public static String getSexName(Context context, boolean isMale) {
        String string;

        if (isMale) {
            string = context.getString(R.string.row_sex_male);
        } else {
            string = context.getString(R.string.row_sex_female);
        }

        return string;
    }

    public static String getAge(Context context, int age) {
        return context.getString(R.string.row_age, age);
    }

    public static String getAvailabilityCar(Context context, boolean hasCar) {
        String string;

        if (hasCar) {
            string = context.getString(R.string.fg_citizen_yes);
        } else {
            string = context.getString(R.string.fg_citizen_no);
        }

        return string;
    }
}
