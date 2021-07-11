package com.loskon.androidprojectcitizens.utils;

import android.content.Context;

import com.loskon.androidprojectcitizens.R;

public class ValuesUtils {

    public static String getSexName(Context context, boolean isMale) {
        String string;

        if (isMale) {
            string = context.getString(R.string.item_sex_male);
        } else {
            string = context.getString(R.string.item_sex_female);
        }

        return string;
    }

    public static String getAge(Context context, int age) {
        return context.getString(R.string.Item_age, age);
    }
}
