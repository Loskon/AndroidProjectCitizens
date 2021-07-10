package com.loskon.androidprojectcitizens.model;

import java.io.Serializable;

/**
 * Model
 */

public class Citizen implements Serializable {

    private String lastName;
    private String firstName;
    private int age;
    private String workName;
    private boolean isMale;
    private String districtName;
    private boolean isThereCar;

    public Citizen() {
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public boolean isThereCar() {
        return isThereCar;
    }

    public void setThereCar(boolean thereCar) {
        this.isThereCar = thereCar;
    }

    public String getFullName() {
        return lastName + " " + firstName;
    }
}
