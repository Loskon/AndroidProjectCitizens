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
import com.loskon.androidprojectcitizens.ui.recyclerview.CitizenRecyclerAdapter;
import com.loskon.androidprojectcitizens.ui.recyclerview.CitizenClickListener;
import com.loskon.androidprojectcitizens.utils.WidgetUtils;
import com.loskon.androidprojectcitizens.viewmodel.CitizenViewModel;

import java.util.ArrayList;

/**
 * Форма списка граждан
 */

public class CitizenListFragment extends Fragment implements CitizenClickListener {

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

    private final CitizenRecyclerAdapter adapter = new CitizenRecyclerAdapter();
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
        configureRecyclerAdapter();
        configureRecyclerView();
        initialiseWidgets();
        configureAnimController();
        installHandlers();
        setupViewModel();
    }

    private void configureRecyclerAdapter() {
        adapter.registerCitizenClickListener(this);
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
        bottomAppBar = widgetsHelper.getBottomBar();
    }

    private void configureAnimController() {
        animController = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_animation);
    }

    private void installHandlers() {
        fab.setOnClickListener(v -> onFabClick());
        bottomAppBar.setOnMenuItemClickListener(this::onClickMenuItem);
    }

    private void onFabClick() {
        citizens = activity.getCitizens();

        if (citizens.size() != 0) {
            startLoadingListCitizens();
        } else {
            callErrorMessage();
        }

        widgetsHelper.startAnimateFab();
    }

    private void startLoadingListCitizens() {
        changeVisibleIndicator(true);
        configureHandlerForLoading();
        if (!hasFirstClick) changeFabIcon();
    }

    private void changeVisibleIndicator(boolean isVisible) {
        isProgressVisible = isVisible;
        WidgetUtils.setVisibleView(indicator, isVisible);
    }

    private void configureHandlerForLoading() {
        int seconds = (int) (Math.random() * 4 + 1); // Время загрузки списка от 1 до 4 секунд
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(this::performLoadingListCitizens, seconds * 1000L);
    }

    private void performLoadingListCitizens() {
        changeVisibleIndicator(false);
        recyclerView.scrollToPosition(0);
        recyclerView.setLayoutAnimation(animController); // Показ анимации при обновлении списка
        bundleState.clear(); // Очищаем, чтобы убрать бесконечный показ индикатора прогресса
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

    private boolean onClickMenuItem(MenuItem item) {
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

    //----------------------------------------------------------------------------------------------
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

    //----------------------------------------------------------------------------------------------
    @Override
    public void onPause() {
        saveIndicatorState();
        super.onPause();
    }

    private void saveIndicatorState() {
        if (layoutManager != null) {
            bundleState.putBoolean(ARG_EXTRA_PROGRESS, isProgressVisible);
        }
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onDetach() {
        nullifyListeners();
        super.onDetach();
    }

    private void nullifyListeners() {
        fab.setOnClickListener(null);
        bottomAppBar.setOnMenuItemClickListener(null);
    }

    //----------------------------------------------------------------------------------------------
    @Override
    public void onClickingItem(Citizen citizen) {
        CitizenFragment fragment = CitizenFragment.newInstance(citizen);
        activity.replaceFragment(fragment);
    }
}
