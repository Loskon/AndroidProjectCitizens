package com.loskon.androidprojectcitizens.ui.helper;

import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.ui.activity.MainActivity;

/**
 * Помощник для получения значения ресурсов
 */

public class ResourcesHelper {

    private final MainActivity activity;

    public ResourcesHelper(MainActivity activity) {
        this.activity = activity;
    }

    public String getSex(boolean isMale) {
        String sex;

        if (isMale) {
            sex = activity.getString(R.string.fg_citizen_male);
        } else {
            sex = activity.getString(R.string.fg_citizen_female);
        }

        return sex;
    }

    public String getThereCar(boolean isThereCar) {
        String car;

        if (isThereCar) {
            car = activity.getString(R.string.fg_citizen_yes);
        } else {
            car = activity.getString(R.string.fg_citizen_no);
        }

        return car;
    }

    public int getImageViewPerson(boolean isMale) {
        int draw;

        if (isMale) {
            draw = R.drawable.male;
        } else {
            draw = R.drawable.female;
        }

        return draw;
    }
}
