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

        startService(serviceIntent) ;


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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {

            //Données test
            Doctor doctor1 = new Doctor(0, new UserAccount(0, "salut",
                    "azerty", "doctor@doc.fr"), "James", "Wilson",
                    "12 rue de la bout en train", "0143559918",
                    "Généraliste", "ABC");

            Doctor doctor2 = new Doctor(0, new UserAccount(1, "hello",
                    "uiop", "doctor@doc.fr"), "Gregory", "House",
                    "15 rue des petits moineaux bleu","0689898989",
                    "Vasectomie", "DEF");

            List<IData> weights = new ArrayList<>();
            weights.add(new Weight(0, 10, format.parse("2001-01-12")));
            weights.add(new Weight(1, 12, format.parse("2002-01-12")));
            weights.add(new Weight(2, 14, format.parse("2003-01-12")));
            weights.add(new Weight(3, 18, format.parse("2005-01-12")));
            weights.add(new Weight(4, 24, format.parse("2007-01-12")));
            weights.add(new Weight(5, 33, format.parse("2010-01-12")));
            weights.add(new Weight(6, 48, format.parse("2013-01-12")));
            weights.add(new Weight(7, 53, format.parse("2015-01-12")));
            weights.add(new Weight(8, 55, format.parse("2016-01-12")));
            weights.add(new Weight(9, 56, format.parse("2018-01-12")));

            List<IData> heights = new ArrayList<>();
            heights.add(new Height(0, 75, format.parse("2001-01-12")));
            heights.add(new Height(0, 85, format.parse("2002-01-12")));
            heights.add(new Height(0, 103, format.parse("2005-01-12")));
            heights.add(new Height(0, 122, format.parse("2007-01-12")));
            heights.add(new Height(0, 140, format.parse("2010-01-12")));
            heights.add(new Height(0, 165, format.parse("2015-01-12")));
            heights.add(new Height(0, 166, format.parse("2018-01-12")));


            SimpleDateFormat appointmentFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
            List<Appointment> appointments = new ArrayList<>();
            appointments.add(new Appointment(4, doctor2, appointmentFormat.parse("2019-07-22-06-12"), 30));
            appointments.add(new Appointment(0, doctor1, appointmentFormat.parse("2019-07-22-06-13"), 30));
            appointments.add(new Appointment(1, doctor1, appointmentFormat.parse("2019-02-12-18-30"), 30));
            appointments.add(new Appointment(2, doctor2, appointmentFormat.parse("2019-05-20-18-30"), 30));
            appointments.add(new Appointment(3, doctor1, appointmentFormat.parse("2019-05-22-18-30"), 30));
            appointments.add(new Appointment(5, doctor2, appointmentFormat.parse("2019-08-12-18-30"), 30));
            appointments.add(new Appointment(6, doctor1, appointmentFormat.parse("2019-10-25-18-30"), 30));
            appointments.add(new Appointment(7, doctor1, appointmentFormat.parse("2019-11-30-18-30"), 30));

            Vaccine vaccin1 = new Vaccine(0,"Coqueluche",
                    "Recommandée à l'âge de 2 mois ainsi qu'à l'entourage du" +
                            " nourrisson si leur dernier rappel de la coqueluche " +
                            "date de plus de 10 ans", 10);

            Vaccine vaccin2 = new Vaccine(1,"Diphtérie, tétanos et poliomyélite (DTP)",
                    "Obligatoire dès la naissance.", 10);

            Vaccine vaccin3 = new Vaccine(2,"BCG (tuberculose)",
                    "Recommandée dès la naissance et jusqu'à 15 ans", 5);

            Medicament medicament1 = new Medicament(0, "Paracetamol");
            Medicament medicament2 = new Medicament(1, "Paratetraplegique");

            List<Prescription> prescriptions1 = new ArrayList<>();
            List<Prescription> prescriptions2 = new ArrayList<>();
            prescriptions1.add(new Prescription(0, vaccin1, 3));
            prescriptions1.add(new Prescription(1, medicament1, 4));
            prescriptions1.add(new Prescription(1, vaccin3, (float)0.01));

            prescriptions2.add(new Prescription(1, vaccin2, 25));
            prescriptions2.add(new Prescription(1, medicament2, 5));

            List<Ordinance> ordinances = new ArrayList<>();
            ordinances.add(new Ordinance(0, doctor1, new Date(), prescriptions1));
            ordinances.add(new Ordinance(1, doctor2, new Date(), prescriptions2));

            List<Recall> recalls = new ArrayList<>();
            recalls.add(new Recall(0, vaccin1, new Date()));
            recalls.add(new Recall(1, vaccin2, new Date()));
            recalls.add(new Recall(2, vaccin3, new Date()));

            UserAccount keep = new UserAccount(0, "dopidot", "1234",
                    "dopidot@youtube.com");


            /*user = new Patient(0, "Mickael", "Moreira",
                    format.parse("1996-12-01"), "O+",
                    "1234567891234", true,
                    weights, heights, recalls, appointments, ordinances);*/
            //user.setHeights(heights);
            //user.setWeights(weights);
            //user.setAppointments(appointments);
            user.setOrdinances(ordinances);
            user.setRecalls(recalls);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
