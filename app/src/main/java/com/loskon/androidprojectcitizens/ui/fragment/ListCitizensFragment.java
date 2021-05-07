package com.loskon.androidprojectcitizens.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loskon.androidprojectcitizens.R;
import com.loskon.androidprojectcitizens.model.Citizen;
import com.loskon.androidprojectcitizens.recycler.MyRecyclerAdapter;
import com.loskon.androidprojectcitizens.ui.activity.MainActivity;
import com.loskon.androidprojectcitizens.ui.helper.WidgetsHelper;
import com.loskon.androidprojectcitizens.viewmodel.CitizenViewModel;

import java.util.ArrayList;

/**
 * Форма списка граждан
 */

public class ListCitizensFragment extends Fragment {

    private final static String KEY_RECYCLER_STATE = "key_recycler_state";
    private static Bundle bundleRecyclerState;

    private MainActivity activity;
    private WidgetsHelper widgetsHelper;
    private MyRecyclerAdapter myRecyclerAdapter;
    private CitizenViewModel viewModel;

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private BottomAppBar bottomAppBar;
    private Menu appBarMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_citizens, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (MainActivity) requireActivity();
        widgetsHelper = activity.getWidgetsHelper();

        fab = activity.getWidgetsHelper().getFab();
        bottomAppBar = activity.getWidgetsHelper().getBottomAppBar();

        recyclerView = requireActivity().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        viewModel = new ViewModelProvider(this).get(CitizenViewModel.class);
        viewModel.getCitizens().observe(getViewLifecycleOwner(), userListUpdateObserver);
    }

    private final Observer<ArrayList<Citizen>> userListUpdateObserver = new Observer<ArrayList<Citizen>>() {
        @Override
        public void onChanged(ArrayList<Citizen> userArrayList) {
            myRecyclerAdapter = new MyRecyclerAdapter(requireContext(), userArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setAdapter(myRecyclerAdapter);
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        widgetsHelper.isIconFabVisible(true);
        widgetsHelper.isNavigationIconVisible(false);
        widgetsHelper.isMenuItemVisible(true);
        restoreRecyclerState();
    }

    private void restoreRecyclerState() {
        // Восстанавливаем состояние RecyclerView
        if (bundleRecyclerState != null) {
            Parcelable listState = bundleRecyclerState.getParcelable(KEY_RECYCLER_STATE);
            if (recyclerView.getLayoutManager() != null) {
                recyclerView.getLayoutManager().onRestoreInstanceState(listState);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveRecyclerState();
    }

    private void saveRecyclerState() {
        bundleRecyclerState = new Bundle();
        if (recyclerView.getLayoutManager() != null)  {
            Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
            bundleRecyclerState.putParcelable(KEY_RECYCLER_STATE, listState);
        }
    }
}
