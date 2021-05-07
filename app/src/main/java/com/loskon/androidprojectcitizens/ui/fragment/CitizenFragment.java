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
import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.ui.activity.MainActivity;
import com.loskon.androidprojectcitizens.ui.helper.WidgetsHelper;

/**
 * Форма данных гражданина
 */

public class CitizenFragment extends Fragment {

    public static final String ARG_ID = "_id";

    private MainActivity activity;
    private WidgetsHelper widgetsHelper;

    private TextView textView;
    private BottomAppBar bottomAppBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_citizen, container, false);
        initialiseFragmentWidgets(view);
        return view;
    }

    private void initialiseFragmentWidgets(View view) {
        textView = (TextView) view.findViewById(R.id.textView8);

        //String customColorText = getResources().getString(R.string.pbs_setup_title);
        //textView.setText(Html.fromHtml(customColorText));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (MainActivity) requireActivity();

        initialiseActivityWidgs();
        handlersWidgsActivity();

        //handlersWidgetsFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            textView.setText(String.valueOf(args.getInt(ARG_ID)));
        }
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

}
