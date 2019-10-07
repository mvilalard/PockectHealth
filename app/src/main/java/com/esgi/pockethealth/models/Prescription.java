package com.esgi.pockethealth.models;

import java.io.Serializable;

public class Prescription implements Serializable {
    private int id;
    private Care care;
    private float posologie;

    public Prescription(int id, Care care, float posologie) {
        this.id = id;
        this.care = care;
        this.posologie = posologie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Care getCare() {
        return care;
    }

    public void setCare(Care care) {
        this.care = care;
    }

    public float getPosologie() {
        return posologie;
    }

    public void setPosologie(float posologie) {
        this.posologie = posologie;
    }
}
