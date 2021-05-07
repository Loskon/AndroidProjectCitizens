package com.loskon.androidprojectcitizens;

import android.content.Context;

import com.loskon.androidprojectcitizens.model.Citizen;
import com.loskon.androidprojectcitizens.ui.helper.SharedHelper;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Генератор граждан
 */

public class CitizenGenerator {

    private static final String[] firstNameGen = {"Яна","Иван","Елена","Мария","Артем",
            "Марк", "Екатерина", "Марат", "Любовь", "Валерия"};

    private static final String[] lastNameGen = {"Муравьёва","Суханов","Вакульская","Булгарина","Лодыгин",
            "Годунов", "Холщевникова", "Ершов", "Акинфова", "Ахматова"};

    private static int ageValGenMin;
    private static int ageValGenMax;

    private static final String[] workGen = {"ПАО «Северо-Западное пароходство»","ЗАО «Гражданские самолеты Сухого»","Softline","ГБОУ \"Лицей №1535\"","SAP СНГ",
            "Часовня", "Металлсервис", "ГК «Дамате»", "Останкинский МПЗ", "Медквадрат"};

    private static boolean isMaleGen;

    private static final String[] placeLiving = {"Дзержинский","Индустриальный","Кировский","Ленинский","Мотовилихинский",
            "Орджоникидзевский", " Свердловский", "поселок Новые Ляды"};

    private static boolean carGen;

    private static int period;

    private static ArrayList<Citizen> citizenArrayListGen;
    private static int rangeGen;

    private static Context context;

    public static void initialize(Context context) {
        CitizenGenerator.context = context;
        initializeVar();
        initializeTimer();
        startTimer();
    }

    private static void initializeVar() {
        ageValGenMin = SharedHelper.getValRangeMin(context);
        ageValGenMax = SharedHelper.getValRangeMax(context);
        period = SharedHelper.getValPeriod(context);
    }

    private static Timer timer;
    private  static TimerTask timerTask;

    private static void initializeTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }
        };
    }

    private static void TimerMethod() {
        rangeGen = getRandomInt(10, 100);
    }

    public static int getRandomInt(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private static void startTimer() {
        timer.schedule(timerTask, 0, 1000);
    }

    public static void cancelTimer() {
        timer.cancel();
    }

    public static int getPeriod() {
        return rangeGen;
    }


}
