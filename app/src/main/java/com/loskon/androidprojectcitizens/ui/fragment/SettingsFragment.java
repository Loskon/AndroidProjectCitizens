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
import com.loskon.androidprojectcitizens.ui.sheets.SheetDialog;

import static com.loskon.androidprojectcitizens.ui.sheets.SheetDialog.TYPE_PERIOD;
import static com.loskon.androidprojectcitizens.ui.sheets.SheetDialog.TYPE_RANGE_MAX;
import static com.loskon.androidprojectcitizens.ui.sheets.SheetDialog.TYPE_RANGE_MIN;

/**
 * Форма настроек генератора
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "MyLogs_" + SettingsFragment.class.getSimpleName();
    private static CallbackSettings callback;

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
        SettingsFragment.callback = callbackSettings;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initialiseViews(view);
        return view;
    }

    private void initialiseViews(View view) {
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

        initialiseWidgets();
        installHandlers();
        loadSharedPref();
        installValuesInWidgets();
    }

    @Override
    public void onResume() {
        super.onResume();
        widgetsHelper.isItemsVisible(false);
    }

    private void initialiseWidgets() {
        widgetsHelper = activity.getWidgetsHelper();
        bottomAppBar = widgetsHelper.getBottomAppBar();
    }

    private void installHandlers() {
        sliderPeriod.addOnChangeListener((slider, value, b) -> handlingSliderPeriod(value));
        sliderRange.addOnChangeListener((slider, value, b) -> handlingSliderRange(slider));
        bottomAppBar.setNavigationOnClickListener(v -> activity.onBackPressed());
        tvPeriod.setOnClickListener(this);
        tvRangeMin.setOnClickListener(this);
        tvRangeMax.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.tv_val_period) {
            new SheetDialog(activity, this).show(TYPE_PERIOD);
        } else if (id == R.id.tv_val_range_min) {
            new SheetDialog(activity, this).show(TYPE_RANGE_MIN, preMax);
        } else if (id == R.id.tv_val_range_max) {
            new SheetDialog(activity, this).show(TYPE_RANGE_MAX, preMin);
        }
    }

    private void handlingSliderPeriod(float value) {
        prePeriod = (int) value;
        tvPeriod.setText(String.valueOf(prePeriod));
    }

    private void handlingSliderRange(RangeSlider slider) {
        float min = slider.getValues().get(0);
        float max = slider.getValues().get(1);
        float difference = max - min;

        // Защита от выбора одного числа
        if (difference == 0) {
            if (min != preMin | max != preMax) setValuesSliderRange();
        } else {
            preMin = (int) min;
            preMax = (int) max;
        }

        setValuesRangeInTextView();
    }

    private void setValuesSliderRange() {
        sliderRange.setValues((float) preMin, (float) preMax);
    }

    private void setValuesRangeInTextView() {
        tvRangeMin.setText(String.valueOf(preMin));
        tvRangeMax.setText(String.valueOf(preMax));
    }

    private void loadSharedPref() {
        prePeriod = SharedHelper.getGenerationPeriod(activity);
        preMin = SharedHelper.getAgeRangeMin(activity);
        preMax = SharedHelper.getAgeRangeMax(activity);

        prePeriodOld = prePeriod;
        preMinOld = preMin;
        preMaxOld = preMax;
    }

    private void installValuesInWidgets() {
        tvPeriod.setText(String.valueOf(prePeriod));
        setValuesRangeInTextView();
        setValuesSliderRange();
        sliderPeriod.setValue(prePeriod);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        savingChangedValues();
        restartingGenerator();
    }

    private void savingChangedValues() {
        // Сохранение только измененных значений
        if (prePeriod != prePeriodOld) SharedHelper.setGenPeriod(activity, prePeriod);
        Log.d(TAG, "genPeriod: " + prePeriod);

        if (preMin != preMinOld) SharedHelper.setAgeRangeMin(activity, preMin);
        Log.d(TAG, "preMin: " + preMin);

        if (preMax != preMaxOld) SharedHelper.setAgeRangeMax(activity, preMax);
        Log.d(TAG, "preMax: " + preMax);
    }

    private void restartingGenerator() {
        boolean hasCallback = (preMax != preMaxOld) |
                (preMin != preMinOld) | (prePeriod != prePeriodOld);

        if (hasCallback) {
            if (callback != null) callback.onCallbackSettings();
        }
    }

    public void setSlidersValues(String type, int enteredValue, int neighboringValue) {

        switch (type) {
            case TYPE_PERIOD:
                prePeriod = enteredValue;
                break;
            case TYPE_RANGE_MIN:
                preMin = enteredValue;
                preMax = neighboringValue;
                break;
            case TYPE_RANGE_MAX:
                preMin = neighboringValue;
                preMax = enteredValue;
                break;
        }

        installValuesInWidgets();
    }

    public interface CallbackSettings {
        void onCallbackSettings();
    }
}
