package com.loskon.androidprojectcitizens.utils;

import android.view.View;

/**
 * Утилита для помощи в работе с элементами
 */

public class WidgetUtils {

    //Fragments
    public static void isVisible(View view, boolean isVisible) {
        int visibleValue;

        if (isVisible) {
            visibleValue = View.VISIBLE;
        } else {
            visibleValue = View.INVISIBLE;
        }

        view.setVisibility(visibleValue);
    }

    public static void isVisibleGone(View view, boolean isVisible) {
        int vis;

        if (isVisible) {
            vis = View.VISIBLE;
        } else {
            vis = View.GONE;
        }

        view.setVisibility(vis);
    }
}
