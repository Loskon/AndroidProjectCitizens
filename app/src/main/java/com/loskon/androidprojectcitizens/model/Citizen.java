package com.loskon.androidprojectcitizens.model;

import java.io.Serializable;

/**
 * Model
 */

public class Citizen implements Serializable {

    private String lastName;
    private String firstName;
    private int age;
    private String workTitle;
    private boolean isMale;
    private String districtTitle;
    private boolean hasCar;

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
    }

    public void setMale(boolean isMale) {
        this.isMale = isMale;
    }

    public void setDistrictTitle(String districtTitle) {
        this.districtTitle = districtTitle;
    }

    public void setAvailabilityCar(boolean hasCar) {
        this.hasCar = hasCar;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }

    public int getAge() {
        return age;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public boolean isMale() {
        return isMale;
    }

    public String getDistrictTitle() {
        return districtTitle;
    }

    public boolean hasCar() {
        return hasCar;
    }
}
