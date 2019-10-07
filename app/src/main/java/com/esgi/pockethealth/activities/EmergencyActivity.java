package com.esgi.pockethealth.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.esgi.pockethealth.R;

public class EmergencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // change status bar color
        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.emergency_main_color_dark));
        }

        setContentView(R.layout.activity_emergency);


        final Button emergency_doctor_button = (Button) findViewById(R.id.emergency_doctor);
        emergency_doctor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction(emergency_doctor_button);
            }
        });

        final Button emergency_proche = (Button) findViewById(R.id.emergency_proche);
        emergency_proche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAction(emergency_proche);
            }
        });

        final Button emergency_15= (Button) findViewById(R.id.emergency_15);
        emergency_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNumber(emergency_15.getText().toString());
            }
        });

        final Button emergency_18= (Button) findViewById(R.id.emergency_18);
        emergency_18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNumber(emergency_18.getText().toString());
            }
        });

        final Button emergency_112= (Button) findViewById(R.id.emergency_112);
        emergency_112.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNumber(emergency_112.getText().toString());
            }
        });

        final Button emergency_pharmacy= (Button) findViewById(R.id.emergency_pharmacy);
        emergency_pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNumber("3237");
            }
        });


        final Button emergency_SOS= (Button) findViewById(R.id.emergency_SOS);
        emergency_SOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callNumber("0147077777");
            }
        });
    }

    private void buttonAction(Button origin) {
        if(checkNumberExistence(origin)) {
            callNumberForButton(origin);
        }
        else {
            createNumber(origin);
        }
    }

    private boolean checkNumberExistence(final Button origin) {
        final SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        String registredNumber = preferences.getString(origin.getText().toString(), "");
        if (registredNumber != null && !registredNumber.isEmpty()) {
            try {
                Integer.parseInt(registredNumber);
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
        return false;
    }

    private void createNumber(final Button origin) {
        final SharedPreferences preferences= getPreferences(MODE_PRIVATE) ;
        final String key = origin.getText().toString();
        String dialogTitle = "Enregistrez un numéro pour " + origin.getText();
        String registredNumber = preferences.getString(key, "");

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(dialogTitle);

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setText(registredNumber);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = input.getText().toString();
                try {
                    Integer.parseInt(value);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(key, value);
                    editor.commit();
                } catch (NumberFormatException ex) {
                    Toast.makeText(EmergencyActivity.this,
                            "Veuillez saisir un numéro de téléphone !",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void callNumberForButton(Button origin) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1);
        } else {
            final SharedPreferences preferences= getPreferences(MODE_PRIVATE) ;
            final String key = origin.getText().toString();
            // String number = preferences.getString(key,"");
            String number = preferences.getString(key,"0750210436");

            Log.d("number", number);
            callNumber(number);
        }
    }

    private void callNumber(String number) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+number));
        startActivity(callIntent);
        Log.d("callIntent", "started");
    }

}
