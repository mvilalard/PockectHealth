package com.esgi.pockethealth.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Ordinance implements Serializable {

    private int id;
    private Doctor doctor;
    private Date date_creation;
    private List<Prescription> prescriptions;

    public Ordinance(int id,Doctor doctor, Date date_creation, List<Prescription> prescriptions) {
        this.id = id;
        this.doctor = doctor;
        this.date_creation = date_creation;
        this.prescriptions = prescriptions;
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

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
