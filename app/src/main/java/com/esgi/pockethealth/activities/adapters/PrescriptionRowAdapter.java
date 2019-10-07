package com.esgi.pockethealth.activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.esgi.pockethealth.R;
import com.esgi.pockethealth.models.Ordinance;
import com.esgi.pockethealth.models.Prescription;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionRowAdapter extends BaseAdapter {

    private Context mContext;
    private List<Prescription> prescriptions;

    public PrescriptionRowAdapter(Context context, List<Prescription> prescriptions) {
        mContext = context;
        this.prescriptions = prescriptions;

    }

    public int getCount() {
        return prescriptions.size();
    }

    public Object getItem(int arg0) {
        return prescriptions.get(arg0);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        Prescription currentPrescription = (Prescription) getItem(position);
        View row;
        row = inflater.inflate(R.layout.prescription_row, parent, false);
        //ImageView rdv_icon_iv = row.findViewById(R.id.rdv_left_icon);
        TextView care_name = row.findViewById(R.id.prescription_top);
        TextView  posologie = row.findViewById(R.id.prescription_bottom);
        care_name.setText("Traitement : "+currentPrescription.getCare().getName());
        posologie.setText("Posologie : "+Float.toString(currentPrescription.getPosologie()));
        //rdv_icon_iv.setImageResource(R.drawable.ordonnance);

        return (row);
    }
}