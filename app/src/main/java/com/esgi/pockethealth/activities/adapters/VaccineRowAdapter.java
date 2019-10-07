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
import com.esgi.pockethealth.models.Recall;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VaccineRowAdapter extends BaseAdapter {

    private Context mContext;
    private List<Recall> recalls;

    public VaccineRowAdapter(Context context, List<Recall> recalls) {
        mContext = context;
        this.recalls = recalls;

    }

    public int getCount() {
        return recalls.size();
    }

    public Object getItem(int arg0) {
        return recalls.get(arg0);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        Recall currentRecall = (Recall) getItem(position);
        View row;
        row = inflater.inflate(R.layout.vaccine_row, parent, false);
        TextView vaccine_icon = row.findViewById(R.id.mark);
        TextView vaccine_name = row.findViewById(R.id.vaccine_top);
        TextView vaccine_recall_date = row.findViewById(R.id.vaccine_mid);
        TextView vaccine_next_recall_date = row.findViewById(R.id.vaccine_bottom);

        String date = DateFormat.getDateInstance(DateFormat.SHORT).format(currentRecall.getRecall_date());
        Calendar c = Calendar.getInstance();
        c.setTime(currentRecall.getRecall_date());
        c.add(Calendar.YEAR, currentRecall.getVaccine().getValidity());
        Date nextDate = c.getTime();
        String nextDateValue = DateFormat.getDateInstance(DateFormat.SHORT).format(nextDate);

        vaccine_name.setText("Nom : " + currentRecall.getVaccine().getName());
        vaccine_recall_date.setText("Dernier rappel : " + date);
        vaccine_next_recall_date.setText("Prochain rappel : " + nextDateValue);
        vaccine_icon.setText(currentRecall.getVaccine().getName().substring(0, 1));

        return (row);
    }
}