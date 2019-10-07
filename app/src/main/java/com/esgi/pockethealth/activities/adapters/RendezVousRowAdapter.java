package com.esgi.pockethealth.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.pockethealth.R;
import com.esgi.pockethealth.models.Appointment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class RendezVousRowAdapter extends BaseAdapter {

    private Context mContext;
    private List<Appointment> appointments;

    public RendezVousRowAdapter(Context context, List<Appointment> appointments) {
        mContext = context;
        this.appointments = appointments;

    }

    public int getCount() {
        return appointments.size();
    }

    public Object getItem(int arg0) {
        return appointments.get(arg0);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        Appointment currentAppointment = (Appointment) getItem(position);
        View row;
        row = inflater.inflate(R.layout.rendez_vous_row, parent, false);
        TextView rdv_icon = row.findViewById(R.id.mark);
        TextView rdv_date_tv = row.findViewById(R.id.rdv_top);
        TextView rdv_doctor_tv = row.findViewById(R.id.rdv_bottom);
        TextView rdv_location_tv = row.findViewById(R.id.rdv_location);

        String date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(currentAppointment.getCreation_date());

        rdv_date_tv.setText("Date  : " + date);
        rdv_doctor_tv.setText("Docteur : " + currentAppointment.getDoctor().getName()+ " "
                + currentAppointment.getDoctor().getForename());
        rdv_location_tv.setText("Lieu : " + currentAppointment.getDoctor().getAddress());
        rdv_icon.setText(currentAppointment.getDoctor().getName().substring(0,1) +
                currentAppointment.getDoctor().getForename().substring(0,1));

        return (row);
    }
}