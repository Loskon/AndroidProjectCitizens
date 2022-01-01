package com.loskon.androidprojectcitizens.ui.helper;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.Menu;

import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loskon.androidprojectcitizens.R;

/**
 * Помощник для управления элементами MainActivity
 */

public class WidgetsHelper {

    private final Context context;

    private final FloatingActionButton fab;
    private final BottomAppBar bottomBar;

    private final Menu menu;

    public WidgetsHelper(Context context,
                         FloatingActionButton fab,
                         BottomAppBar bottomBar) {
        this.context = context;
        this.fab = fab;
        this.bottomBar = bottomBar;
        menu = bottomBar.getMenu();
    }

    public void isWidgetsVisible(boolean isVisible) {
        isMenuItemVisible(isVisible);
        isFabVisible(isVisible);
        isNavigationIconVisible(!isVisible);
    }

    private void isMenuItemVisible(boolean isVisible) {
        menu.findItem(R.id.action_settings).setVisible(isVisible);
    }

    private void isFabVisible(boolean isVisible) {
        if (isVisible) {
            fab.show();
        } else {
            fab.hide();
        }
    }

    private void isNavigationIconVisible(boolean isVisible) {
        if (isVisible) {
            bottomBar.setNavigationIcon(R.drawable.ic_baseline_keyboard_arrow_left_24);
        } else {
            bottomBar.setNavigationIcon(null);
        }
    }

    public void setIconFab() {
        fab.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(),
                R.drawable.ic_baseline_replay_24, null));
    }

    public void startAnimateFab() {
        ObjectAnimator.ofFloat(fab, "rotation", 360f, 0)
                .setDuration(400).start();
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    public BottomAppBar getBottomBar() {
        return bottomBar;
    }
}
