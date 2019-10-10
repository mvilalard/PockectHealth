package com.esgi.pockethealth.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esgi.pockethealth.R;
import com.esgi.pockethealth.application.BaseActivity;
import com.esgi.pockethealth.application.RequestManager;
import com.esgi.pockethealth.models.Patient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseActivity {


    static JSONArray res = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void login() throws JSONException {
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

        /*RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);

        String url = "http://192.168.1.33:5000/connection/"+username+"/"+password;
        StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray obj = new JSONArray(response);
                    //TESTER SI RESPONSE EST NULL//
                    progressDialog.setMessage("connected as "+obj.getJSONObject(0).getInt("patientID"));
                    startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.setMessage("not connected");
            }
        });

        ExampleRequestQueue.add(ExampleStringRequest);*/
        getResponse("http://192.168.1.33:5000/connection/"+username+"/"+password);
        if (res != null){
            int id = res.getJSONObject(0).getInt("patientID");
            progressDialog.setMessage("connected as "+id);
            populateUser(id);
        }


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

    public void getResponse(String url){

        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);

        StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    res = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        ExampleRequestQueue.add(ExampleStringRequest);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void populateUser(int id){
        user = new Patient();
        user.setId(id);

        
    }
}