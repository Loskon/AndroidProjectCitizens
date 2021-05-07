package com.loskon.androidprojectcitizens.ui.helper;

import android.view.Menu;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
        handlingClicks();
    }

    private void initialiseWidgets() {
        fab = activity.findViewById(R.id.fab);
        bottomAppBar = activity.findViewById(R.id.bar);
        appBarMenu = bottomAppBar.getMenu();
    }

    // Handlers
    private void handlingClicks() {
        fab.setOnClickListener(v -> {
            //Toast.makeText(activity, ""+ CitizenGenerator.getPeriod(), Toast.LENGTH_SHORT).show();
        });


        bottomAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_settings) {
                activity.openSettingsFragment();
                return true;
            } else
                return false;
        });
    }


    public void isMenuItemVisible(boolean isVisible) {
        appBarMenu.findItem(R.id.action_settings).setVisible(isVisible);
    }

    public void isIconFabVisible(boolean isVisible) {
        if (!isVisible) {
            //Animation myAnim = AnimationUtils.loadAnimation(activity, R.anim.scale_down);
            //fab.startAnimation(myAnim);
            fab.hide();
        } else {
            //Animation myAnim = AnimationUtils.loadAnimation(activity, R.anim.scale_up);
            //fab.startAnimation(myAnim);
            fab.show();
        }
    }

    public void isNavigationIconVisible(boolean isVisible) {
        if (isVisible) {
            bottomAppBar.setNavigationIcon(R.drawable.ic_baseline_keyboard_arrow_left_24);
        } else {
            bottomAppBar.setNavigationIcon(null);
        }
    }

    // Getters
    public FloatingActionButton getFab() {
        return fab;
    }

    public BottomAppBar getBottomAppBar() {
        return bottomAppBar;
    }
}
