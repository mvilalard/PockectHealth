package com.esgi.pockethealth.models;

import java.io.Serializable;

public class Medicament extends Care implements Serializable {
    public Medicament(int id, String name) {
        super(id, name);
    }
}
