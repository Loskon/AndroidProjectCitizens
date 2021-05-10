package com.loskon.androidprojectcitizens.generator;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.loskon.androidprojectcitizens.model.Citizen;
import com.loskon.androidprojectcitizens.ui.helper.SharedHelper;

import java.util.ArrayList;
import java.util.TimerTask;

import static com.loskon.androidprojectcitizens.ui.helper.ServiceHelper.KEY_EXTRA;
import static com.loskon.androidprojectcitizens.ui.helper.ServiceHelper.KEY_SERIALIZABLE;
import static com.loskon.androidprojectcitizens.ui.helper.ServiceHelper.RESULT_SERVICE_OK;

/**
 * Генератор граждан
 */

public class CitizenGeneratorTask extends TimerTask {

    private static final String TAG = CitizenGeneratorTask.class.getSimpleName();

    private final Context context;
    private final PendingIntent pendingIntent;

    private String[] lastNameGen;
    private String[] firstNameGen;
    private String[] workGen;
    private String[] districtNameGen;

    private int ageMinGen, ageMaxGen;
    private int lastNameGenSize;
    private int firstNameGenSize;
    private int workGenSize;
    private int districtNameGenSize;

    public CitizenGeneratorTask(Context context, PendingIntent pendingIntent) {
        this.context = context;
        this.pendingIntent = pendingIntent;

        initArrayVal();
        initArrayLength();
        initGenSettings();
    }

    private void initArrayVal() {
        SourceCitizenStore citizensStore = new SourceCitizenStore();
        lastNameGen = citizensStore.getLastNameGen();
        firstNameGen = citizensStore.getFirstNameGen();
        workGen = citizensStore.getWorkGen();
        districtNameGen = citizensStore.getDistrictNameGen();
    }

    private void initArrayLength() {
        lastNameGenSize = getArrayLength(lastNameGen.length);
        firstNameGenSize = getArrayLength(firstNameGen.length);
        workGenSize = getArrayLength(workGen.length);
        districtNameGenSize = getArrayLength(districtNameGen.length);
    }

    private int getArrayLength(int length) {
        return length - 1;
    }

    private void initGenSettings() {
        ageMinGen = SharedHelper.getAgeRangeMin(context);
        ageMaxGen = SharedHelper.getAgeRangeMax(context);
        Log.d(TAG, "ageMinGen: " + ageMinGen);
        Log.d(TAG, "ageMaxGen: " + ageMaxGen);
    }

    @Override
    public void run() {
        TimerMethod();
    }

    private void TimerMethod() {
        int rangeGen = getRandomInt(10, 100);

        ArrayList<Citizen> citizens = new ArrayList<>(rangeGen);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        for (int i = 0; i < rangeGen; i++) {
            String lastRandomName = lastNameGen[getRandomInt(0, lastNameGenSize)]; // Фамилия
            String firstRandomName = firstNameGen[getRandomInt(0, firstNameGenSize)]; // имя
            int ageRandom = getRandomInt(ageMinGen, ageMaxGen); // Возраст
            String workRandomName = workGen[getRandomInt(0, workGenSize)]; // Место работы
            boolean sexRandom = getRandomBoolean(); // Пол
            String districtRandomName = districtNameGen[getRandomInt(0, districtNameGenSize)]; // Район проживания
            boolean carRandom = getRandomBoolean(); // Наличие машины

            Citizen citizen = new Citizen();
            citizen.setFirstName(firstRandomName);
            citizen.setLastName(lastRandomName);
            citizen.setMale(sexRandom);
            citizen.setAge(ageRandom);
            citizen.setWorkName(workRandomName);
            citizen.setDistrictName(districtRandomName);
            citizen.setThereCar(carRandom);
            citizens.add(citizen);
        }

        bundle.putSerializable(KEY_SERIALIZABLE, citizens);
        intent.putExtra(KEY_EXTRA, bundle);

        try {
            pendingIntent.send(context, RESULT_SERVICE_OK, intent);
            Log.d(TAG, "Generator is working, rangeGen: " + rangeGen);
        } catch (Exception exception) {
            exception.printStackTrace();
            Log.d(TAG, "Generation error!", exception);
        }
    }

    public int getRandomInt(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }
}
