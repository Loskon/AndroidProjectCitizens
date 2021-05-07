package com.loskon.androidprojectcitizens.model;

import java.io.Serializable;

/**
 * Класс модели
 */

public class Citizen implements Serializable {

    private String lastName;
    private String firstName;
    private int ageVal;
    private String workName;
    private boolean isMale;
    private int placeLiving;
    private boolean car;

    public Citizen() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAgeVal() {
        return ageVal;
    }

    public void setAgeVal(int ageVal) {
        this.ageVal = ageVal;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        this.isMale = male;
    }

    public int getPlaceLiving() {
        return placeLiving;
    }

    public void setPlaceLiving(int placeLiving) {
        this.placeLiving = placeLiving;
    }

    public boolean isCar() {
        return car;
    }

    public void setCar(boolean car) {
        this.car = car;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }
}
