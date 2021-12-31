package com.loskon.androidprojectcitizens.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

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

    public static String getAvailabilityCar(Context context, boolean isThereCar) {
        String thereCar;

        if (isThereCar) {
            thereCar = context.getString(R.string.fg_citizen_yes);
        } else {
            thereCar = context.getString(R.string.fg_citizen_no);
        }

        return thereCar;
    }
}
