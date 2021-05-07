package com.loskon.androidprojectcitizens.ui.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.loskon.androidprojectcitizens.service.LocalService;
import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.model.Citizen;
import com.loskon.androidprojectcitizens.recycler.MyRecyclerAdapter;
import com.loskon.androidprojectcitizens.ui.fragment.CitizenFragment;
import com.loskon.androidprojectcitizens.ui.fragment.ListCitizensFragment;
import com.loskon.androidprojectcitizens.ui.fragment.SettingsFragment;
import com.loskon.androidprojectcitizens.ui.helper.WidgetsHelper;

import java.util.ArrayList;

import static com.loskon.androidprojectcitizens.ui.fragment.CitizenFragment.ARG_ID;

/**
 * Хост представления для фрагментов
 */

public class MainActivity extends AppCompatActivity implements MyRecyclerAdapter.CallbackSelected {

    public static final String KEY_EXTRA = "key_put_extra";
    public static final String KEY_SERIALIZABLE = "key_citizen_array_list";
    public static final String KEY_PENDING_INTENT = "key_pending_intent";

    public static final int REQUEST_SERVICE = 451;
    public static final int RESULT_SERVICE_OK = 452;

    private WidgetsHelper widgetsHelper;
    private FragmentManager supportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseSettings();
        openListFragment();
    }

    private void initialiseSettings() {
        widgetsHelper = new WidgetsHelper(this);
        supportFragmentManager = getSupportFragmentManager();
        MyRecyclerAdapter.registerCallbackSelected(this);
    }

    private void openListFragment() {
        Fragment listFragment = supportFragmentManager.findFragmentById(R.id.fragment_container);

        if (listFragment == null) {
            listFragment = new ListCitizensFragment();
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, listFragment)
                    .commit();
        }
    }

    @Override
    public void onCallbackSelected(int id) {
        CitizenFragment citizenFragment = new CitizenFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(ARG_ID, id);
        citizenFragment.setArguments(bundle);

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, citizenFragment)
                .addToBackStack(null)
                .commit();
    }

    public void openSettingsFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService();
    }

    private void startService() {
        PendingIntent pendingResult = createPendingResult(REQUEST_SERVICE, new Intent(), 0);
        Intent intent = new Intent(getApplicationContext(), LocalService.class);
        intent.putExtra(KEY_PENDING_INTENT, pendingResult);
        startService(intent);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SERVICE && resultCode == RESULT_SERVICE_OK) {

            Bundle args = data.getBundleExtra(KEY_EXTRA);
            ArrayList<Citizen> object = (ArrayList<Citizen>) args.getSerializable(KEY_SERIALIZABLE);

            Toast.makeText(this, "" + object, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, LocalService.class));
    }

    public WidgetsHelper getWidgetsHelper() {
        return widgetsHelper;
    }
}