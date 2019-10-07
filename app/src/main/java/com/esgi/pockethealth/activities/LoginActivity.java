package com.esgi.pockethealth.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.pockethealth.R;
import com.esgi.pockethealth.application.BaseActivity;
import com.esgi.pockethealth.application.RequestManager;
import com.esgi.pockethealth.models.Patient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        final Button loginButton = (Button) findViewById(R.id.btn_login);

        checkPermissions();

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_NoActionBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authentification...");
        progressDialog.show();

        final EditText usernameText = findViewById(R.id.input_username);
        final String username = usernameText.getText().toString();
        final EditText passwordText = findViewById(R.id.input_password);
        final String password = passwordText.getText().toString();

        // TODO : Login api call to change logged state
        final HashMap<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        final JSONObject[] response = {new JSONObject()};
        Thread postTask = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "/android/login/patient/" + username + "/" + password;
                response[0] = RequestManager.executeHttpsRequest(
                        RequestManager.RequestType.POST, url, null);
            }
        });
        postTask.start();
        while (postTask.isAlive());

        boolean logged = true;

        if(response[0] != null) {
            JSONObject responseObject = response[0];
            try {
                responseObject.getString("error");
            } catch (JSONException e) {
                logged = true;
            }
        }
        if(logged){
            Intent loginIntent = new Intent();

            startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
        } else {
            Toast.makeText(getBaseContext(), "Échec de l'authentification", Toast.LENGTH_LONG).show();
            loginButton.setEnabled(true);
            progressDialog.dismiss();
        }
        return;
    }

    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    1);
        }
        else if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                    1);
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
}