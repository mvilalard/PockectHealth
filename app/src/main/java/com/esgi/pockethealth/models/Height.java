package com.esgi.pockethealth.models;

import com.esgi.pockethealth.application.IData;

import java.io.Serializable;
import java.util.Date;

public class Height implements Serializable, IData {
    private int id;
    private float height;
    private Date date;

    public Height(int id, float height, Date date) {
        this.id = id;
        this.height = height;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public float getData() {
        return height;
    }
}
