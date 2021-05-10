package com.loskon.androidprojectcitizens.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loskon.androidprojectcitizens.model.Citizen;

import java.util.ArrayList;

/**
 * Модель представления
 */

public class CitizenViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Citizen>> citizenLiveData;

    public CitizenViewModel() {
        citizenLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<Citizen>> getCitizens() {
        return citizenLiveData;
    }

    public void setCitizens(ArrayList<Citizen> citizenArrayList) {
        citizenLiveData.setValue(citizenArrayList);
    }
}
