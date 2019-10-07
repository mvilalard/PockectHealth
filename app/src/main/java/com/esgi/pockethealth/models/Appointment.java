package com.esgi.pockethealth.models;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {
    private int id;
    private Doctor doctor;
    private Date creation_date;
    private int duration;

    public Appointment(int id, Doctor doctor, Date creation_date, int duration) {
        this.id = id;
        this.doctor = doctor;
        this.creation_date = creation_date;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
