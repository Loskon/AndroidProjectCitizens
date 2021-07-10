package com.loskon.androidprojectcitizens.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.model.Citizen;
import com.loskon.androidprojectcitizens.ui.fragment.CitizenFragment;
import com.loskon.androidprojectcitizens.ui.fragment.ListCitizensFragment;
import com.loskon.androidprojectcitizens.ui.fragment.SettingsFragment;
import com.loskon.androidprojectcitizens.ui.helper.ResourcesHelper;
import com.loskon.androidprojectcitizens.ui.helper.ServiceHelper;
import com.loskon.androidprojectcitizens.ui.helper.WidgetsHelper;
import com.loskon.androidprojectcitizens.ui.recycler.AppRecyclerAdapter;

import java.util.ArrayList;

import static com.loskon.androidprojectcitizens.ui.fragment.CitizenFragment.ARG_CITIZEN;
import static com.loskon.androidprojectcitizens.ui.helper.ServiceHelper.KEY_EXTRA;
import static com.loskon.androidprojectcitizens.ui.helper.ServiceHelper.KEY_SERIALIZABLE;
import static com.loskon.androidprojectcitizens.ui.helper.ServiceHelper.REQUEST_SERVICE;
import static com.loskon.androidprojectcitizens.ui.helper.ServiceHelper.RESULT_SERVICE_OK;

/**
 * Хост представления для фрагментов
 */

public class MainActivity extends AppCompatActivity implements AppRecyclerAdapter.CallbackSelected {

    private WidgetsHelper widgetsHelper;
    private ResourcesHelper resourcesHelper;
    private ServiceHelper serviceHelper;
    private FragmentManager supportFragmentManager;

    private ArrayList<Citizen> citizens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseObjects();
        initialiseOther();
        openListFragment();
        serviceHelper.startService();
    }

    private void initialiseObjects() {
        widgetsHelper = new WidgetsHelper(this);
        resourcesHelper = new ResourcesHelper(this);
        serviceHelper = new ServiceHelper(this);

    }

    private void initialiseOther() {
        supportFragmentManager = getSupportFragmentManager();
        AppRecyclerAdapter.registerCallbackSelected(this);
    }

    private void openListFragment() {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, new ListCitizensFragment())
                .commit();
    }

    @Override
    public void onCallbackSelected(Citizen citizen) {
        CitizenFragment citizenFragment = new CitizenFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CITIZEN, citizen);
        citizenFragment.setArguments(bundle);

        replaceFragment(citizenFragment);
    }

    public void openSettingsFragment() {
        replaceFragment(new SettingsFragment());
    }

    private void replaceFragment(Fragment fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.enter_fg_slide_from_bottom,  // Ввод в следующий
                        R.anim.leave_fg_slide_from_top, // Выход из текущего фрагмента
                        R.anim.enter_fg_slide_from_top, // Ввод в прошлый фрагмент
                        R.anim.leave_fg_slide_from_bottom // Выход из открытого фрагмента
                )
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SERVICE && resultCode == RESULT_SERVICE_OK) {
            Bundle bundle = data.getBundleExtra(KEY_EXTRA);
            if (bundle != null) {
                citizens = (ArrayList<Citizen>) bundle.getSerializable(KEY_SERIALIZABLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serviceHelper.stopService();
    }

    public WidgetsHelper getWidgetsHelper() {
        return widgetsHelper;
    }

    public ResourcesHelper getResourcesHelper() {
        return resourcesHelper;
    }

    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }
}