package com.loskon.androidprojectcitizens.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loskon.androidprojectcitizens.model.Citizen;

import java.util.ArrayList;

/**
 * ViewModel
 */

public class CitizenViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Citizen>> liveData = new MutableLiveData<>();

    public void setCitizens(ArrayList<Citizen> citizenArrayList) {
        liveData.setValue(citizenArrayList);
    }

    public MutableLiveData<ArrayList<Citizen>> getCitizens() {
        return liveData;
    }
}
