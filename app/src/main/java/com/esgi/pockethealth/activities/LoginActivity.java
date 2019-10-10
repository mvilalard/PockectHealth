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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseActivity {


    static JSONArray res = null;
    static boolean connected = false;

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
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void login() throws JSONException, ParseException {
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



        user = new Patient();

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.33:5000/connection/"+username+"/"+password, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    res = new JSONArray(response);
                    if (res != null && res .length()>0){
                        user.setId(res.getJSONObject(0).getInt("patientID"));
                        progressDialog.setMessage("connected as "+user.getId());
                        populateUser(user.getId());
                        startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(stringRequest);

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

    public void getResponse(final String url){

        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(LoginActivity.this);

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

    public void populateUser(int id) throws JSONException, ParseException {

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.1.33:5000/patient/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject userObj = null;
                try {
                    res = new JSONArray(response);
                    userObj = res.getJSONObject(0);
                    user.setName(userObj.getString("name"));
                    user.setForename(userObj.getString("forename"));
                    user.setBlood_group(userObj.getString("bloodgroup"));
                    user.setSocial_security_number(userObj.getString("social_security_number"));
                    user.setBirthday(format.parse(userObj.getString("birthdate")));

                    Toast.makeText(getApplicationContext(),user.getBirthday().toString(),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue.add(stringRequest);

    }

}