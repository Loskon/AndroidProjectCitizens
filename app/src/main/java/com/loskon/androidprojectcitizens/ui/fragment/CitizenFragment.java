package com.loskon.androidprojectcitizens.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.databinding.FragmentCitizenBinding;
import com.loskon.androidprojectcitizens.model.Citizen;
import com.loskon.androidprojectcitizens.ui.activity.MainActivity;
import com.loskon.androidprojectcitizens.ui.helper.WidgetsHelper;

import org.jetbrains.annotations.NotNull;

/**
 * Форма данных гражданина
 */

public class CitizenFragment extends Fragment {

    public static final String ARG_CITIZEN = "arg_citizen";

    private MainActivity activity;
    private WidgetsHelper widgetsHelper;

    private BottomAppBar bottomAppBar;

    private Citizen citizen;

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();

        if (bundle != null) {
            citizen = (Citizen) getArguments().getSerializable(ARG_CITIZEN);
        }
    }

    public View onCreateView(@NotNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentCitizenBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_citizen, container, false);
        binding.setCitizen(citizen);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (MainActivity) requireActivity();

        initialiseWidgets();
        setupWidgets();
        installHandlers();
    }

    private void initialiseWidgets() {
        widgetsHelper = activity.getWidgetsHelper();
        bottomAppBar = widgetsHelper.getBottomAppBar();
    }

    private void setupWidgets() {
        widgetsHelper.isItemsVisible(false);
    }

    private void installHandlers() {
        bottomAppBar.setNavigationOnClickListener(v -> activity.onBackPressed());
    }
}
