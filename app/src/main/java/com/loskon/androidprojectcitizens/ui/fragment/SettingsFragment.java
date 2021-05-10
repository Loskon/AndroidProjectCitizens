package com.loskon.androidprojectcitizens.ui.fragment;

import android.os.Bundle;
import android.util.Log;
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

/**
 * Форма настроек генератора
 */

public class SettingsFragment extends Fragment {

    private static final String TAG = SettingsFragment.class.getSimpleName();
    private static CallbackSettings callbackSettings;

    private MainActivity activity;
    private WidgetsHelper widgetsHelper;

    private BottomAppBar bottomAppBar;
    private Slider sliderPeriod;
    private RangeSlider sliderRange;
    private TextView tvPeriod, tvRangeMin, tvRangeMax;

    private int preMin = -1;
    private int preMax = -1;
    private int prePeriod = -1;

    private int preMinOld;
    private int preMaxOld;
    private int prePeriodOld;

    public static void registerCallbackSettings(CallbackSettings callbackSettings) {
        SettingsFragment.callbackSettings = callbackSettings;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initialiseSettingsWidgets(view);
        return view;
    }

    private void initialiseSettingsWidgets(View view) {
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

        initialiseSettingsHelpers();
        handlersSettingsWidgets();
        loadSharedPref();
        setValToWidgets();
    }

    @Override
    public void onResume() {
        super.onResume();
        widgetsHelper.isMainActivityItemsVisible(false);
    }

    private void initialiseSettingsHelpers() {
        widgetsHelper = activity.getWidgetsHelper();
        bottomAppBar = widgetsHelper.getBottomAppBar();
    }

    private void handlersSettingsWidgets() {
        sliderPeriod.addOnChangeListener((slider, value, fromUser) -> handlerSlPeriod(value));
        sliderRange.addOnChangeListener((slider, value, fromUser) -> handlerSrRange(slider));
        bottomAppBar.setNavigationOnClickListener(v -> activity.onBackPressed());
    }

    private void handlerSlPeriod(float value) {
        prePeriod = (int) value;
        setPeriodValToTextView();
    }

    private void setPeriodValToTextView() {
        tvPeriod.setText(String.valueOf(prePeriod));
    }

    private void handlerSrRange(RangeSlider slider) {
        float minVal = slider.getValues().get(0);
        float maxVal = slider.getValues().get(1);

        float diffVal = maxVal - minVal;

        // Защита от выбора одного числа
        if (diffVal == 0) {
            if (minVal != preMin | maxVal != preMax) {
                setSliderRangeVal();
            }
        } else {
            preMin = (int) minVal;
            preMax = (int) maxVal;
        }

        setRangeValToTv();
    }

    private void setSliderRangeVal() {
        sliderRange.setValues((float) preMin, (float) preMax);
    }

    private void setRangeValToTv() {
        tvRangeMin.setText(String.valueOf(preMin));
        tvRangeMax.setText(String.valueOf(preMax));
    }

    private void loadSharedPref() {
        prePeriod = SharedHelper.getGenPeriod(activity);
        preMin = SharedHelper.getAgeRangeMin(activity);
        preMax = SharedHelper.getAgeRangeMax(activity);

        prePeriodOld = prePeriod;
        preMinOld = preMin;
        preMaxOld = preMax;
    }

    private void setValToWidgets() {
        // Установка сохраненных значений в TextView
        setPeriodValToTextView();
        setRangeValToTv();
        // Установка сохраненных значений в Slider
        setSliderRangeVal();
        sliderPeriod.setValue(prePeriod);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveSliderVal();
        callBack();
    }

    private void saveSliderVal() {
        // Сохранение только измененных значений
        if (prePeriod != prePeriodOld) SharedHelper.setGenPeriod(activity, prePeriod);
        Log.d(TAG, "genPeriod: " + prePeriod);

        if (preMin != preMinOld) SharedHelper.setAgeRangeMin(activity, preMin);
        Log.d(TAG, "preMin: " + preMin);

        if (preMax != preMaxOld) SharedHelper.setAgeRangeMax(activity, preMax);
        Log.d(TAG, "preMax: " + preMax);
    }

    private void callBack() {
        // Перезапуск сервиса только при внесении изменений
        boolean isCall = (preMax != preMaxOld) |
                (preMin != preMinOld) | (prePeriod != prePeriodOld);

        if (isCall) {
            if (callbackSettings != null) {
                callbackSettings.onCallbackSettings();
                Log.d(TAG, "callbackSettings call");
            }
        }
    }

    public interface CallbackSettings {
        void onCallbackSettings();
    }
}
