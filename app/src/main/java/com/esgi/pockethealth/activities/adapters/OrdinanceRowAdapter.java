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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrdinanceRowAdapter extends BaseAdapter {

    private Context mContext;
    private List<Ordinance> ordinances;

    public OrdinanceRowAdapter(Context context, List<Ordinance> ordinances) {
        mContext = context;
        this.ordinances = ordinances;

    }

    public int getCount() {
        return ordinances.size();
    }

    public Object getItem(int arg0) {
        return ordinances.get(arg0);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        Ordinance currentOrdinance = (Ordinance) getItem(position);
        View row;
        row = inflater.inflate(R.layout.ordinance_row, parent, false);
        TextView rdv_icon_tv = row.findViewById(R.id.mark);
        TextView rdv_date_tv = row.findViewById(R.id.rdv_top);
        TextView rdv_doctor_tv = row.findViewById(R.id.rdv_bottom);


        String date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(currentOrdinance.getDate_creation());
        Date creationDate = currentOrdinance.getDate_creation();// the date instance
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(creationDate);
        calendar.add(Calendar.MONTH, 1);

        rdv_date_tv.setText("Date : " + date);
        rdv_doctor_tv.setText("Docteur : " + currentOrdinance.getDoctor().getName()+ " "
                + currentOrdinance.getDoctor().getForename());
        rdv_icon_tv.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/"+calendar.get(Calendar.MONTH) );

        return (row);
    }
}