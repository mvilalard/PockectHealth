package com.esgi.pockethealth.models;

import java.io.Serializable;

public class Doctor implements Serializable {
    private int id;
    private UserAccount user_account;
    private String name;
    private String forename;
    private String address;
    private String telephone;
    private String speciality;
    private String number_rpps;

    public Doctor(int id, UserAccount user_account, String name, String forename,
                  String address, String telephone,
                  String speciality, String number_rpps)
    {
        this.id = id;
        this.user_account = user_account;
        this.name = name;
        this.forename = forename;
        this.address = address;
        this.telephone = telephone;
        this.speciality = speciality;
        this.number_rpps = number_rpps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserAccount getUser_account() { return user_account; }

    public void setUser_account(UserAccount user_account) { this.user_account = user_account; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getNumber_rpps() {
        return number_rpps;
    }

    public void setNumber_rpps(String number_rpps) {
        this.number_rpps = number_rpps;
    }
}
