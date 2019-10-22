package com.esgi.pockethealth.models;

import com.esgi.pockethealth.application.IData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Patient implements Serializable {
    private int id;
    private String name;
    private String forename;
    private Date birthday;
    private String blood_group;
    private String social_security_number;
    private boolean organ_donor;
    private List<IData> weights;
    private List<IData> heights;
    private List<Recall> recalls;
    private List<Appointment> appointments;
    private List<Ordinance> ordinances;

    private boolean populated = false;

    public Patient(int id, String name, String forename, Date birthday,
                   String blood_group, String social_security_number, boolean organ_donor,
                   List<IData> weights, List<IData> heights, List<Recall> recalls,
                   List<Appointment> appointments, List<Ordinance> ordinances) {
        this.id = id;
        this.name = name;
        this.forename = forename;
        this.birthday = birthday;
        this.blood_group = blood_group;
        this.social_security_number = social_security_number;
        this.organ_donor = organ_donor;

        this.weights = weights;
        this.heights = heights;
        this.recalls = recalls;
        this.appointments = appointments;
        this.ordinances = ordinances;
    }

    public Patient(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getSocial_security_number() {
        return social_security_number;
    }

    public void setSocial_security_number(String social_security_number) {
        this.social_security_number = social_security_number;
    }

    public boolean isOrgan_donor() {
        return organ_donor;
    }

    public void setOrgan_donor(boolean organ_donor) {
        this.organ_donor = organ_donor;
    }

    public List<IData> getWeights() {
        return weights;
    }

    public void setWeights(List<IData> weights) {
        this.weights = weights;
    }

    public List<IData> getHeights() {
        return heights;
    }

    public void setHeights(List<IData> heights) {
        this.heights = heights;

    }

    public List<Recall> getRecalls() {
        return recalls;
    }

    public void setRecalls(List<Recall> recalls) {
        this.recalls = recalls;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Ordinance> getOrdinances() {
        return ordinances;
    }

    public void setOrdinances(List<Ordinance> ordinances) {
        this.ordinances = ordinances;
    }

    public Appointment getClosestAppointment() {
        final long now = System.currentTimeMillis();

        List<Date> dates = new ArrayList<Date>();

        for (int i = 0; i < appointments.size(); i++) {
            dates.add(appointments.get(i).getCreation_date());
        }

        Date closest = Collections.min(dates, new Comparator<Date>() {
            public int compare(Date d1, Date d2) {
                long diff1 = Math.abs(d1.getTime() - now);
                long diff2 = Math.abs(d2.getTime() - now);
                return Long.compare(diff1, diff2);
            }
        });

        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getCreation_date().equals(closest)) {
                return appointments.get(i);
            }
        }

        return null;
    }

    public boolean isPopulated() {
        return populated;
    }
}
