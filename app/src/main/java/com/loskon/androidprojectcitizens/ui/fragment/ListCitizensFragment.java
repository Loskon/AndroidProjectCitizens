package com.loskon.androidprojectcitizens.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
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
import com.loskon.androidprojectcitizens.ui.recycler.AppRecyclerAdapter;
import com.loskon.androidprojectcitizens.utils.WidgetUtils;
import com.loskon.androidprojectcitizens.viewmodel.CitizenViewModel;

import java.util.ArrayList;

/**
 * Форма списка граждан
 */

public class ListCitizensFragment extends Fragment {

    private static final String TAG = "MyLogs_" + ListCitizensFragment.class.getSimpleName();

    private static final String SAVED_RECYCLER_STATE = "saved_recycler_state";
    private static final String SAVED_PROGRESS_VISIBLE = "saved_progress_visible";
    private Bundle bundleState;

    private MainActivity activity;
    private WidgetsHelper widgetsHelper;
    private CitizenViewModel viewModel;
    private RecyclerView.LayoutManager layoutManager;
    private LayoutAnimationController animationController;

    private LinearProgressIndicator indicator;
    private TextView tvEmpty;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private BottomAppBar bottomAppBar;

    private ArrayList<Citizen> citizens = new ArrayList<>();
    private final Handler handler = new Handler();

    private boolean hasFirstClick = false;
    private boolean isProgressVisible = false;

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
        activity = (MainActivity) requireActivity();

        initialiseWidgets();
        installHandlers();
        setupViewModel();
    }

    private void initialiseWidgets() {
        widgetsHelper = activity.getWidgetsHelper();
        fab = widgetsHelper.getFab();
        bottomAppBar = widgetsHelper.getBottomAppBar();
        animationController = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_animation);
    }

    private void installHandlers() {
        fab.setOnClickListener(v -> onClickFab());
        bottomAppBar.setOnMenuItemClickListener(this::onClickItemMenu);
    }

    private void onClickFab() {
        citizens = activity.getCitizens();

        if (citizens.size() != 0) {
            startLoadingList();
        } else {
            callErrorMessage();
        }

        widgetsHelper.startAnimateFab();
    }

    private void startLoadingList() {
        isProgressVisible = true;
        isVisibleIndicator(true);
        startHandler();
        if (!hasFirstClick) firstClick(); // Смена иконки fab после первой загрузке списка
    }

    private void isVisibleIndicator(boolean isVisible) {
        WidgetUtils.isVisible(indicator, isVisible);
    }

    private void startHandler() {
        int seconds = (int) (Math.random() * 4 + 1); // Время загрузки списка от 1 до 4 секунд
        Log.d(TAG, "seconds: " + seconds);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(this::handlerMethod, seconds * 1000);
    }

    private void handlerMethod() {
        isProgressVisible = false;
        isVisibleIndicator(false);
        bundleState.clear(); // Сброс сохранения состояний при обновлении
        recyclerView.setLayoutAnimation(animationController); // Показ анимации только при обновлении
        viewModel.setCitizens(citizens);
    }

    private void firstClick() {
        hasFirstClick = true;
        widgetsHelper.setIconFab();
    }

    private void callErrorMessage() {
        String message = getString(R.string.fg_list_error);
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    private boolean onClickItemMenu(MenuItem item) {
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

    private void updateUI(ArrayList<Citizen> userArrayList) {
        if (userArrayList != null) {
            AppRecyclerAdapter appRecyclerAdapter = new AppRecyclerAdapter(userArrayList);
            layoutManager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(appRecyclerAdapter);
            recyclerView.setHasFixedSize(true);
            WidgetUtils.isVisibleGone(tvEmpty, userArrayList.isEmpty());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onResumeAction();
    }

    private void onResumeAction() {
        widgetsHelper.isItemsVisible(true);
        restoreViewsState();
        isVisibleIndicator(isProgressVisible);
    }

    private void restoreViewsState() {
        if (bundleState != null) {
            Parcelable listState = bundleState.getParcelable(SAVED_RECYCLER_STATE);
            if (layoutManager != null) layoutManager.onRestoreInstanceState(listState);
            isProgressVisible = bundleState.getBoolean(SAVED_PROGRESS_VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveViewsState();
    }

    private void saveViewsState() {
        bundleState = new Bundle();
        if (layoutManager != null) {
            Parcelable listState = layoutManager.onSaveInstanceState();
            bundleState.putParcelable(SAVED_RECYCLER_STATE, listState);
            bundleState.putBoolean(SAVED_PROGRESS_VISIBLE, isProgressVisible);
        }
    }
}
