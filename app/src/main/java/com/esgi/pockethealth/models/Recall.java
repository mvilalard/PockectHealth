package com.esgi.pockethealth.models;

import java.io.Serializable;
import java.util.Date;

public class Recall implements Serializable {
    private int id;
    private Vaccine vaccine;
    private Date recall_date;

    public Recall(int id, Vaccine vaccine, Date recall_date) {
        this.id = id;
        this.vaccine = vaccine;
        this.recall_date = recall_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public Date getRecall_date() {
        return recall_date;
    }

    public void setRecall_date(Date recall_date) {
        this.recall_date = recall_date;
    }
}
