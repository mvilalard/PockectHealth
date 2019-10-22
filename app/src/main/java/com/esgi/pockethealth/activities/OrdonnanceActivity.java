package com.esgi.pockethealth.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.esgi.pockethealth.R;
import com.esgi.pockethealth.activities.adapters.OrdinanceRowAdapter;
import com.esgi.pockethealth.activities.adapters.PrescriptionRowAdapter;
import com.esgi.pockethealth.application.BaseActivity;
import com.esgi.pockethealth.models.Ordinance;

public class OrdonnanceActivity extends BaseActivity {

    private ListView ordinanceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ordonnance);

        // change status bar color
        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.ordonnance_main_color_dark));
        }

        final OrdinanceRowAdapter adapter = new OrdinanceRowAdapter(this, user.getOrdinances());

        ordinanceListView = findViewById(R.id.menuList);
        ordinanceListView.setAdapter(adapter);
        ordinanceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showOrdinancePopup(findViewById(android.R.id.content), user.getOrdinances().get(position));
            }
        });

        ordinanceListView.setBackgroundResource(R.color.ordonnance_main_color);


    }

    public void showOrdinancePopup(View view, Ordinance ordinance) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_ordinance, null);
        popupView.setBackgroundColor(Color.WHITE);

        // create the popup window
        int width = view.getWidth() - 50;
        int height = view.getHeight() - 50;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        TextView doctorNameTv = popupWindow.getContentView().findViewById(R.id.doctor_name_label);
        doctorNameTv.setText("Docteur : " + ordinance.getDoctor().getName() + " "
                + ordinance.getDoctor().getForename());

        PrescriptionRowAdapter adapter = new PrescriptionRowAdapter(getApplicationContext(),
                ordinance.getPrescriptions());
        ListView lv = popupView.findViewById(R.id.prescriptionList);
        lv.setAdapter(adapter);

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
