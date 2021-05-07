package com.loskon.androidprojectcitizens.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loskon.androidprojectcitizens.model.Citizen;

import java.util.ArrayList;

/**
 *
 */

public class CitizenViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Citizen>> citizenLiveData;
    private ArrayList<Citizen> citizenArrayList;

    public CitizenViewModel() {
        citizenLiveData = new MutableLiveData<>();
        init();
    }

    public MutableLiveData<ArrayList<Citizen>> getCitizens() {
        return citizenLiveData;
    }

    public void init(){
        populateList();
        citizenLiveData.setValue(citizenArrayList);
    }

    public void populateList(){
        citizenArrayList = new ArrayList<>();

        build("Яна", "Муравьёва", false, 25, "ПАО «Северо-Западное пароходство»", false);
        build("Иван", "Суханов", true, 28, "ЗАО «Гражданские самолеты Сухого»", false);
        build("Елена", "Вакульская", false, 19, "Softline", true);
        build("Мария", "Булгарина", false, 49, "ГБОУ \"Лицей №1535\"", false);
        build("Артем", "Лодыгин", true, 22, "SAP СНГ", true);

        build("Марк", "Годунов", true, 30, "Часовня", true);
        build("Екатерина", "Холщевникова", false, 23, "Металлсервис", false);
        build("Марат", "Ершов", true, 21, "ГК «Дамате»", false);
        build("Любовь", "Акинфова", false, 67, "Останкинский МПЗ", false);
        build("Валерия", "Ахматова", false, 42, "Медквадрат", false);
    }

    private void build(String s, String s2, boolean b, int i, String s3, boolean b2) {
        Citizen user = new Citizen();
        user.setFirstName(s);
        user.setLastName(s2);
        user.setMale(b);
        user.setAgeVal(i);
        user.setWorkName(s3);
        user.setCar(b2);
        citizenArrayList.add(user);
    }
}
