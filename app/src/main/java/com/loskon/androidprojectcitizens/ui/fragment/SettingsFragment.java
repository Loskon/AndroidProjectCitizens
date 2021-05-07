package com.loskon.androidprojectcitizens.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.ui.activity.MainActivity;
import com.loskon.androidprojectcitizens.ui.helper.SharedHelper;
import com.loskon.androidprojectcitizens.ui.helper.WidgetsHelper;

import static com.loskon.androidprojectcitizens.ui.helper.SharedHelper.KEY_AGE_RANGE_MAX;
import static com.loskon.androidprojectcitizens.ui.helper.SharedHelper.KEY_AGE_RANGE_MIN;
import static com.loskon.androidprojectcitizens.ui.helper.SharedHelper.KEY_GENERATION_PERIOD;

/**
 * Форма настроек генератора
 */

public class SettingsFragment extends Fragment {

    private MainActivity activity;
    private WidgetsHelper widgetsHelper;

    private BottomAppBar bottomAppBar;
    private Slider sliderPeriod;
    private RangeSlider sliderRange;
    private TextView tvPeriod, tvRangeMin, tvRangeMax;

    private float preMinVal = -1;
    private float preMaxVal = -1;
    private int periodValue = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initialiseFragmentWidgets(view);
        return view;
    }

    private void initialiseFragmentWidgets(View view) {
        sliderPeriod = view.findViewById(R.id.slider_period);
        sliderRange = view.findViewById(R.id.slider_range);
        tvPeriod = view.findViewById(R.id.tv_val_period);
        tvRangeMin = view.findViewById(R.id.tv_val_range_min);
        tvRangeMax = view.findViewById(R.id.tv_val_range_max);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (MainActivity) requireActivity();

        initialiseActivityWidgs();
        handlersWidgsActivity();
        handlersWidgetsFragment();
        loadSharedPref();
        setValToWidgets();
    }

    @Override
    public void onResume() {
        super.onResume();
        widgetsHelper.isIconFabVisible(false);
        widgetsHelper.isNavigationIconVisible(true);
        widgetsHelper.isMenuItemVisible(false);
    }


    private void initialiseActivityWidgs() {
        widgetsHelper = activity.getWidgetsHelper();
        bottomAppBar = widgetsHelper.getBottomAppBar();
    }


    private void handlersWidgsActivity() {
        bottomAppBar.setNavigationOnClickListener(v -> activity.onBackPressed());
    }


    private void handlersWidgetsFragment() {
        sliderPeriod.addOnChangeListener((slider, value, fromUser) -> handlerSliderPeriod(value));
        sliderRange.addOnChangeListener((slider, value, fromUser) -> handlerSrRange(slider));
    }

    private void handlerSliderPeriod(float value) {
        periodValue = (int) value;
        setPeriodValToTextView();
    }

    private void setPeriodValToTextView() {
        tvPeriod.setText(String.valueOf((int) periodValue));
    }


    private void handlerSrRange(RangeSlider slider) {
        float minVal = slider.getValues().get(0);
        float maxVal = slider.getValues().get(1);

        float diffVal = maxVal - minVal;

        // Защита от выбора одного числа
        if (diffVal == 0) {
            if (minVal != preMinVal | maxVal != preMaxVal) {
                sliderRange.setValues(preMinVal, preMaxVal);
            }
        } else {
            preMinVal = minVal;
            preMaxVal = maxVal;
        }

        setRangeValToTv();
    }

    private void setRangeValToTv() {
        tvRangeMin.setText(String.valueOf((int) preMinVal));
        tvRangeMax.setText(String.valueOf((int) preMaxVal));
    }


    private void loadSharedPref() {
        periodValue = SharedHelper.getValPeriod(activity);
        preMinVal = SharedHelper.getValRangeMin(activity);
        preMaxVal = SharedHelper.getValRangeMax(activity);
    }


    private void setValToWidgets() {
        setPeriodValToTextView();
        setRangeValToTv();

        sliderRange.setValues(preMinVal, preMaxVal);
        sliderPeriod.setValue(periodValue);
    }

    @Override
    public void onPause() {
        super.onPause();
        saveSliderVal();
    }

    private void saveSliderVal() {
        SharedHelper.saveInt(activity, KEY_AGE_RANGE_MIN, (int) preMinVal);
        SharedHelper.saveInt(activity, KEY_AGE_RANGE_MAX, (int) preMaxVal);
        SharedHelper.saveInt(activity, KEY_GENERATION_PERIOD, (int) periodValue);
    }
}
