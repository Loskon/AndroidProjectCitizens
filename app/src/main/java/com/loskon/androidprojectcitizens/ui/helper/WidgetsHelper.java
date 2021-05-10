package com.loskon.androidprojectcitizens.ui.helper;

import android.animation.ObjectAnimator;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.ui.activity.MainActivity;

/**
 * Помощник для управления элементами
 */

public class WidgetsHelper {

    private final MainActivity activity;

    private FloatingActionButton fab;
    private BottomAppBar bottomAppBar;

    private Menu appBarMenu;

    public WidgetsHelper(MainActivity activity) {
        this.activity = activity;
        initialiseWidgets();
    }

    // MainActivity
    private void initialiseWidgets() {
        fab = activity.findViewById(R.id.fab);
        bottomAppBar = activity.findViewById(R.id.bottom_app_bar);
        appBarMenu = bottomAppBar.getMenu();
    }

    public void isMainActivityItemsVisible(boolean isVisible) {
        // Скрытие/показ элементов при смене фрагментов
        isMenuItemVisible(isVisible);
        isIconFabVisible(isVisible);
        isNavigationIconVisible(!isVisible);
    }

    private void isMenuItemVisible(boolean isVisible) {
        appBarMenu.findItem(R.id.action_settings).setVisible(isVisible);
    }

    private void isIconFabVisible(boolean isVisible) {
        if (isVisible) {
            fab.show();
        } else {
            fab.hide();
        }
    }

    private void isNavigationIconVisible(boolean isVisible) {
        if (isVisible) {
            bottomAppBar.setNavigationIcon(R.drawable.ic_baseline_keyboard_arrow_left_24);
        } else {
            bottomAppBar.setNavigationIcon(null);
        }
    }

    public void setIconFab() {
        fab.setImageDrawable(ResourcesCompat.getDrawable(activity.getResources(),
                R.drawable.ic_baseline_replay_24, null));
    }

    public void startAnimateFab() {
        ObjectAnimator.ofFloat(fab, "rotation", 360f, 0)
                .setDuration(400).start();
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public BottomAppBar getBottomAppBar() {
        return bottomAppBar;
    }


    //Fragments
    public void isVisibleIndicator(LinearProgressIndicator indicator, boolean isVisible) {
        int vis;

        if (isVisible) {
            vis = View.VISIBLE;
        } else {
            vis = View.INVISIBLE;
        }

        indicator.setVisibility(vis);
    }

    public void isVisibleTextEmpty(TextView tvEmpty, boolean isVisible) {
        int vis;

        if (isVisible) {
            vis = View.VISIBLE;
        } else {
            vis = View.GONE;
        }

        tvEmpty.setVisibility(vis);
    }
}
