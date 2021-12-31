package com.loskon.androidprojectcitizens.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.model.Citizen;
import com.loskon.androidprojectcitizens.ui.activity.MainActivity;
import com.loskon.androidprojectcitizens.ui.helper.WidgetsHelper;
import com.loskon.androidprojectcitizens.ui.recyclerview.AppRecyclerAdapter;
import com.loskon.androidprojectcitizens.utils.WidgetUtils;
import com.loskon.androidprojectcitizens.viewmodel.CitizenViewModel;

import java.util.ArrayList;

/**
 * Форма списка граждан
 */

public class ListCitizensFragment extends Fragment {

    private static final String ARG_EXTRA_PROGRESS = "arg_extra_progress";
    private final Bundle bundleState = new Bundle();

    private MainActivity activity;
    private WidgetsHelper widgetsHelper;
    private CitizenViewModel viewModel;
    private RecyclerView.LayoutManager layoutManager;
    private LayoutAnimationController animController;

    private LinearProgressIndicator indicator;
    private TextView tvEmpty;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private BottomAppBar bottomAppBar;

    private final AppRecyclerAdapter adapter = new AppRecyclerAdapter();
    private final Handler handler = new Handler();
    private ArrayList<Citizen> citizens;

    private boolean hasFirstClick = false;
    private boolean isProgressVisible = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_citizens, container, false);
        initialiseViews(view);
        return view;
    }

    private void initialiseViews(View view) {
        indicator = view.findViewById(R.id.indicator);
        tvEmpty = view.findViewById(R.id.tv_empty_list);
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureRecyclerView();
        initialiseWidgets();
        configureAnimController();
        installHandlers();
        setupViewModel();
    }

    private void configureRecyclerView() {
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    private void initialiseWidgets() {
        widgetsHelper = activity.getWidgetsHelper();
        fab = widgetsHelper.getFab();
        bottomAppBar = widgetsHelper.getBottomAppBar();
    }

    private void configureAnimController() {
        animController = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_animation);
    }

    private void installHandlers() {
        fab.setOnClickListener(v -> clickingFab());
        bottomAppBar.setOnMenuItemClickListener(this::clickingItemMenu);
    }

    private void clickingFab() {
        citizens = activity.getCitizens();

        if (citizens.size() != 0) {
            performLoadingList();
        } else {
            callErrorMessage();
        }

        widgetsHelper.startAnimateFab();
    }

    private void performLoadingList() {
        changeVisibleIndicator(true);
        startHandler();
        if (!hasFirstClick) changeFabIcon();
    }

    private void changeVisibleIndicator(boolean isVisible) {
        isProgressVisible = isVisible;
        WidgetUtils.setVisibleView(indicator, isVisible);
    }

    private void startHandler() {
        int seconds = (int) (Math.random() * 4 + 1); // Время загрузки списка от 1 до 4 секунд
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(this::performHandlerMethod, seconds * 1000L);
    }

    private void performHandlerMethod() {
        changeVisibleIndicator(false);
        bundleState.clear();
        recyclerView.setLayoutAnimation(animController); // Показ анимации при обновлении списка
        viewModel.setCitizens(citizens);
    }

    private void changeFabIcon() {
        hasFirstClick = true;
        widgetsHelper.setIconFab();
    }

    private void callErrorMessage() {
        String message = getString(R.string.fg_list_error);
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    private boolean clickingItemMenu(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            activity.openSettingsFragment();
            return true;
        }

        return false;
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(CitizenViewModel.class);
        viewModel.getCitizens().observe(getViewLifecycleOwner(), citizenListObserver);
    }

    private final Observer<ArrayList<Citizen>> citizenListObserver = this::updateUI;

    private void updateUI(ArrayList<Citizen> citizens) {
        if (citizens != null) {
            adapter.setCitizensList(citizens);
            WidgetUtils.setVisibleViewGone(tvEmpty, citizens.isEmpty());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreStateViews();
    }

    private void restoreStateViews() {
        widgetsHelper.isWidgetsVisible(true);
        restoreIndicatorState();
    }

    private void restoreIndicatorState() {
        isProgressVisible = bundleState.getBoolean(ARG_EXTRA_PROGRESS);
        changeVisibleIndicator(isProgressVisible);
    }

    @Override
    public void onPause() {
        super.onPause();
        saveIndicatorState();
    }

    private void saveIndicatorState() {
        if (layoutManager != null) {
            bundleState.putBoolean(ARG_EXTRA_PROGRESS, isProgressVisible);
        }
    }
}
