package com.loskon.androidprojectcitizens.utils;

import android.view.View;

/**
 * Утилита для помощи в работе с элементами
 */

public class WidgetUtils {

    public static void setVisibleView(View view, boolean isVisible) {
        int visible;

        if (isVisible) {
            visible = View.VISIBLE;
        } else {
            visible = View.INVISIBLE;
        }

        view.setVisibility(visible);
    }

    public static void setVisibleViewGone(View view, boolean isVisible) {
        int visible;

        if (isVisible) {
            visible = View.VISIBLE;
        } else {
            visible = View.GONE;
        }

        view.setVisibility(visible);
    }
}
