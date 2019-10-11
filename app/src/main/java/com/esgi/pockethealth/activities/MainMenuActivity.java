package com.esgi.pockethealth.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.esgi.pockethealth.R;
import com.esgi.pockethealth.application.BaseActivity;
import com.esgi.pockethealth.application.IData;
import com.esgi.pockethealth.application.NotificationService;
import com.esgi.pockethealth.models.Appointment;
import com.esgi.pockethealth.models.Doctor;
import com.esgi.pockethealth.models.Height;
import com.esgi.pockethealth.models.Medicament;
import com.esgi.pockethealth.models.Ordinance;
import com.esgi.pockethealth.models.Patient;
import com.esgi.pockethealth.models.Prescription;
import com.esgi.pockethealth.models.Recall;
import com.esgi.pockethealth.models.UserAccount;
import com.esgi.pockethealth.models.Vaccine;
import com.esgi.pockethealth.models.Weight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.lang.Object;

public class MainMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);



        createUser(); // A VIRER QUAND ON AURA UN USER DE LA BDD
        Intent serviceIntent = new Intent( this, NotificationService.class );
        serviceIntent.putExtra("user", this.user);

        //startService(serviceIntent) ;


        final ImageButton rendezVousButton = (ImageButton) findViewById(R.id.rendez_vous_button);
        rendezVousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RendezVousActivity.class));
            }
        });

        final ImageButton ordonnanceButton = (ImageButton) findViewById(R.id.ordonnance_button);
        ordonnanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OrdonnanceActivity.class));
            }
        });

        final ImageButton graphButton = (ImageButton) findViewById(R.id.graph_button);
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GraphActivity.class));
            }
        });

        final ImageButton vaccineHistoryButton = (ImageButton) findViewById(R.id.vaccine_history_button);
        vaccineHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), VaccineActivity.class));
            }
        });

        ImageButton emergency_button = (ImageButton) findViewById(R.id.emergency_button);
        emergency_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EmergencyActivity.class));
            }
        });

        // change status bar color
        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.main_menu_main_color_dark));
        }
    }

    public void createUser(){

    }
}
