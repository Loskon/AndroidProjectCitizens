package com.loskon.androidprojectcitizens.generator;

import static com.loskon.androidprojectcitizens.ui.activity.MainActivity.ARG_EXTRA_CITIZENS;
import static com.loskon.androidprojectcitizens.ui.activity.MainActivity.BROADCAST_ACTION;
import static com.loskon.androidprojectcitizens.ui.activity.MainActivity.ARG_EXTRA_SERIALIZABLE_CITIZENS;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.loskon.androidprojectcitizens.model.Citizen;
import com.loskon.androidprojectcitizens.ui.helper.SharedHelper;

import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Генератор граждан
 */

public class GeneratorTask extends TimerTask {

    private final Context context;

    private final SourceInfoAboutCitizens source = new SourceInfoAboutCitizens();

    private String[] lastNameGen;
    private String[] firstNameGen;
    private String[] workGen;
    private String[] districtNameGen;

    private int ageMinGen, ageMaxGen;
    private int lastNameGenSize;
    private int firstNameGenSize;
    private int workGenSize;
    private int districtNameGenSize;

    public GeneratorTask(Context context) {
        this.context = context;

        initArrayValues();
        initArrayLength();
        initGenSettings();
    }

    private void initArrayValues() {
        lastNameGen = source.getLastNameGen();
        firstNameGen = source.getFirstNameGen();
        workGen = source.getWorkGen();
        districtNameGen = source.getDistrictNameGen();
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
    }

    @Override
    public void run() {
        performGeneration();
    }

    private void performGeneration() {
        Intent intent = new Intent(BROADCAST_ACTION);
        Bundle bundle = new Bundle();
        int rangeGen = getRandomInt(10, 100);

        bundle.putSerializable(ARG_EXTRA_SERIALIZABLE_CITIZENS, getGeneratedList(rangeGen));
        intent.putExtra(ARG_EXTRA_CITIZENS, bundle);

        try {
            context.sendBroadcast(intent);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private ArrayList<Citizen> getGeneratedList(int rangeGen) {
        ArrayList<Citizen> citizens = new ArrayList<>(rangeGen);

        for (int i = 0; i < rangeGen; i++) {
            String lastRandomName = lastNameGen[getRandomInt(0, lastNameGenSize)];
            String firstRandomName = firstNameGen[getRandomInt(0, firstNameGenSize)];
            int ageRandom = getRandomInt(ageMinGen, ageMaxGen);
            String workRandomName = workGen[getRandomInt(0, workGenSize)];
            boolean sexRandom = getRandomBoolean();
            String districtRandomName = districtNameGen[getRandomInt(0, districtNameGenSize)];
            boolean carRandom = getRandomBoolean();

            Citizen citizen = new Citizen();
            citizen.setFirstName(firstRandomName);
            citizen.setLastName(lastRandomName);
            citizen.setMale(sexRandom);
            citizen.setAge(ageRandom);
            citizen.setWorkTitle(workRandomName);
            citizen.setDistrictTitle(districtRandomName);
            citizen.setAvailabilityCar(carRandom);
            citizens.add(citizen);
        }

        return citizens;
    }

    public int getRandomInt(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }
}
