package com.loskon.androidprojectcitizens.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.model.Citizen;
import com.loskon.androidprojectcitizens.service.AppService;
import com.loskon.androidprojectcitizens.ui.fragments.CitizenFragment;
import com.loskon.androidprojectcitizens.ui.fragments.ListCitizensFragment;
import com.loskon.androidprojectcitizens.ui.fragments.SettingsFragment;
import com.loskon.androidprojectcitizens.ui.helper.WidgetsHelper;
import com.loskon.androidprojectcitizens.ui.recyclerview.AppRecyclerAdapter;
import com.loskon.androidprojectcitizens.ui.recyclerview.RecyclerAdapterCallback;

import java.util.ArrayList;

/**
 * Хост представления для фрагментов
 */

public class MainActivity extends AppCompatActivity implements RecyclerAdapterCallback {

    public final static String BROADCAST_ACTION = "broadcast_action";
    public static final String ARG_EXTRA_CITIZENS = "arg_extra_citizens";
    public static final String ARG_EXTRA_SERIALIZABLE_CITIZENS = "arg_extra_serializable_citizens";

    private WidgetsHelper widgetsHelper;
    private FragmentManager fragmentManager;

    private ArrayList<Citizen> citizens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        installCallback();
        initialiseObjects();
        registerBroadcastReceiver();
        startService();
        openCitizensListFragment(savedInstanceState);
    }

    private void installCallback() {
        AppRecyclerAdapter.registerCallbackRecyclerAdapter(this);
    }

    private void initialiseObjects() {
        widgetsHelper = new WidgetsHelper(this);
        fragmentManager = getSupportFragmentManager();
    }

    private void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(receiver, filter);
    }

    private void startService() {
        Intent intent = new Intent(this, AppService.class);
        startService(intent);
    }

    private void openCitizensListFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new ListCitizensFragment())
                    .commit();
        }
    }

    @Override
    public void onClickingItem(Citizen citizen) {
        CitizenFragment fragment = CitizenFragment.newInstance(citizen);
        replaceFragment(fragment);
    }

    private void replaceFragment(Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.enter_fg_slide_from_bottom,
                        R.anim.leave_fg_slide_from_top,
                        R.anim.enter_fg_slide_from_top,
                        R.anim.leave_fg_slide_from_bottom
                )
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void openSettingsFragment() {
        replaceFragment(new SettingsFragment());
    }

    @SuppressWarnings("unchecked")
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle bundle = intent.getBundleExtra(ARG_EXTRA_CITIZENS);
            if (bundle != null) {
                citizens = (ArrayList<Citizen>) bundle.getSerializable(ARG_EXTRA_SERIALIZABLE_CITIZENS);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        stopService();
    }

    private void stopService() {
        stopService(new Intent(this, AppService.class));
    }

    public WidgetsHelper getWidgetsHelper() {
        return widgetsHelper;
    }

    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }
}