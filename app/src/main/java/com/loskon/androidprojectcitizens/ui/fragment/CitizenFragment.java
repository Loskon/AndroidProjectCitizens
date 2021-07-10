package com.loskon.androidprojectcitizens.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.model.Citizen;
import com.loskon.androidprojectcitizens.ui.activity.MainActivity;
import com.loskon.androidprojectcitizens.ui.helper.ResourcesHelper;
import com.loskon.androidprojectcitizens.ui.helper.WidgetsHelper;

/**
 * Форма данных гражданина
 */

public class CitizenFragment extends Fragment {

    public static final String ARG_CITIZEN = "arg_citizen";

    private MainActivity activity;
    private WidgetsHelper widgetsHelper;
    private ResourcesHelper resourcesHelper;

    private BottomAppBar bottomAppBar;
    private TextView tvFullName, tvDistrict, tvAge;
    private TextView tvSex, tvWork, tvCar;
    private ImageView imageViewPerson;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_citizen, container, false);
        initialiseViews(view);
        return view;
    }

    private void initialiseViews(View view) {
        imageViewPerson = view.findViewById(R.id.image_view_person);
        tvFullName = view.findViewById(R.id.tv_full_name);
        tvDistrict = view.findViewById(R.id.tv_district);
        tvAge = view.findViewById(R.id.tv_age);
        tvSex = view.findViewById(R.id.tv_sex);
        tvWork = view.findViewById(R.id.tv_work);
        tvCar = view.findViewById(R.id.tv_car);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (MainActivity) requireActivity();

        initialiseWidgets();
        installHandlers();
    }

    private void initialiseWidgets() {
        widgetsHelper = activity.getWidgetsHelper();
        resourcesHelper = activity.getResourcesHelper();
        bottomAppBar = widgetsHelper.getBottomAppBar();
    }

    private void installHandlers() {
        bottomAppBar.setNavigationOnClickListener(v -> activity.onBackPressed());
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();

        if (bundle != null) {
            Citizen citizen = (Citizen) getArguments().getSerializable(ARG_CITIZEN);
            boolean isMale = citizen.isMale();

            tvFullName.setText(citizen.getFullName());
            tvDistrict.setText(citizen.getDistrictName());
            tvAge.setText(String.valueOf(citizen.getAge()));
            tvSex.setText(resourcesHelper.getSex(isMale));
            tvWork.setText(citizen.getWorkName());
            tvCar.setText(resourcesHelper.getThereCar(citizen.isThereCar()));

            // Для установки изображения
            int draw = resourcesHelper.getImageViewPerson(isMale);
            imageViewPerson.setImageDrawable(ContextCompat.getDrawable(activity, draw));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        widgetsHelper.isItemsVisible(false);
    }
}
