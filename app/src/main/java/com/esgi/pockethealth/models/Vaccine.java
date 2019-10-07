package com.esgi.pockethealth.models;

import java.io.Serializable;

public class Vaccine extends Care implements Serializable {
    private String recommandation;
    private int validity;

    public Vaccine(int id, String name, String recommandation, int validity) {
        super(id, name);
        this.recommandation = recommandation;
        this.validity = validity;
    }

    public String getRecommandation() {
        return recommandation;
    }

    public void setRecommandation(String recommandation) {
        this.recommandation = recommandation;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }
}
