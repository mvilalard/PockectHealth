package com.esgi.pockethealth.models;

import java.io.Serializable;

public class Treatment implements Serializable {
    private int id;
    private String classification;

    public Treatment(int id, String classification){
        this.id = id;
        this.classification = classification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
