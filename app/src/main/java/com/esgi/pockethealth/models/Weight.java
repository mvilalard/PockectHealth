package com.esgi.pockethealth.models;

import com.esgi.pockethealth.application.IData;

import java.io.Serializable;
import java.util.Date;

public class Weight implements Serializable, IData {
    private int id;
    private float weight;
    private Date date;

    public Weight(int id, float weight, Date date) {
        this.id = id;
        this.weight = weight;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public float getData(){
        return weight;
    }
}
