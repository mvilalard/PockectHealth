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

import com.esgi.pockethealth.R;
import com.esgi.pockethealth.application.BaseActivity;
import com.esgi.pockethealth.application.IData;
import com.esgi.pockethealth.models.Appointment;
import com.esgi.pockethealth.models.Doctor;
import com.esgi.pockethealth.models.Height;
import com.esgi.pockethealth.models.Medicament;
import com.esgi.pockethealth.models.Ordinance;
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
import com.esgi.pockethealth.models.Prescription;
import com.esgi.pockethealth.models.Recall;
import com.esgi.pockethealth.models.Vaccine;
import com.esgi.pockethealth.models.Weight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class LoginActivity extends BaseActivity {

    static String IP_address = "https://a336c73d.ngrok.io";//"http://192.168.1.33:5000";
    static JSONArray res = null;
    static ArrayList<Doctor> doctors = new ArrayList<>();
    static ArrayList<Vaccine> vaccines = new ArrayList<>();
    static ArrayList<Medicament> medicaments = new ArrayList<>();
    static ArrayList<Prescription> prescriptions = new ArrayList<>();
    static ArrayList<Ordinance> ordinances = new ArrayList<>();

    static int completedTask = 0;
    static int totalCompletedTask = 10;

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

        StringRequest stringRequest = new StringRequest(Request.Method.GET, IP_address+"/connection/"+username+"/"+password, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    res = new JSONArray(response);
                    if (res != null && res .length()>0){
                        user.setId(res.getJSONObject(0).getInt("patientID"));
                        progressDialog.setMessage("Connection...");

                        reset();
                        populateUser(user.getId());
                        populateDoctor();
                        populateVaccine();
                        populateMeds();
                        populateHeights();
                        populateWeights();
                        populateDoctor();
                        populateAppointments();
                        populateOrdinances();
                        populateRecalls();

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

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void populateUser(int id) throws JSONException, ParseException {

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, IP_address+"/patient/"+id, new Response.Listener<String>() {
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
                    if (userObj.getString("organ_donor") == "Y") user.setOrgan_donor(true);
                    else user.setOrgan_donor(false);
                    completeTask();


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

    public void populateHeights()
    {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                IP_address+"/patient/"+user.getId()+"/heights",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    ArrayList<IData> heightsValues = new ArrayList<>();
                    res = new JSONArray(response);
                    for(int i = 0; i < res.length(); i++) {
                        JSONObject currentObj = res.getJSONObject(i);
                        heightsValues.add(new Height(i, currentObj.getLong("Height"),
                                format.parse(currentObj.getString("date"))));
                    }
                    user.setHeights(heightsValues);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                completeTask();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void populateWeights()
    {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                IP_address+"/patient/"+user.getId()+"/weights",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<IData> weightsValues = new ArrayList<>();
                            res = new JSONArray(response);
                            for(int i = 0; i < res.length(); i++) {
                                JSONObject currentObj = res.getJSONObject(i);
                                weightsValues.add(new Weight(i, currentObj.getLong("weight"),
                                        format.parse(currentObj.getString("date"))));
                            }
                            user.setWeights(weightsValues);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        completeTask();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void populateAppointments()
    {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        final ArrayList<Appointment> appointmentsValues = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                IP_address+"/patient/"+user.getId()+"/appointment",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            res = new JSONArray(response);
                            for(int i = 0; i < res.length(); i++) {
                                JSONObject currentObj = res.getJSONObject(i);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date d = sdf.parse(currentObj.getString("date").replace(".000Z", ""));
                                String formattedTime = output.format(d);
                                appointmentsValues.add(new Appointment(i,
                                        getDoctorById(currentObj.getInt("doctorID")),
                                        output.parse(formattedTime),
                                        currentObj.getInt("duration")));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        user.setAppointments(appointmentsValues);
                        completeTask();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void populateRecalls()
    {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        final ArrayList<Recall> recallValues = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                IP_address+"/patient/"+user.getId()+"/recalls",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            res = new JSONArray(response);
                            for(int i = 0; i < res.length(); i++) {
                                JSONObject currentObj = res.getJSONObject(i);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date d = sdf.parse(currentObj.getString("recall_date").replace(".000Z", ""));
                                String formattedTime = output.format(d);
                                recallValues.add(new Recall(i,
                                        getVaccineById(currentObj.getInt("vaccineID")),
                                        output.parse(formattedTime)));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        user.setRecalls(recallValues);
                        completeTask();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }


    public Doctor getDoctorById(final int id)
    {
        for (Doctor doctor : doctors) {
            if (doctor.getId() == id)
                return doctor;
        }
        return null;
    }

    public Vaccine getVaccineById(final int id){
        for (Vaccine vaccine : vaccines){
            if (vaccine.getId() == id)
                return vaccine;
        }
        return null;
    }

    public void populateDoctor(){
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                IP_address+"/doctor/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            res = new JSONArray(response);
                            for(int i = 0; i < res.length(); i++) {
                                JSONObject currentObj = res.getJSONObject(i);
                                Doctor doc = new Doctor();
                                doc.setId(currentObj.getInt("doctorID"));
                                doc.setName(currentObj.getString("name"));
                                doc.setForename(currentObj.getString("forename"));
                                doc.setAddress(currentObj.getString("address"));
                                doc.setTelephone(currentObj.getString("telephone"));
                                doc.setSpeciality(currentObj.getString("speciality"));
                                doc.setNumber_rpps(currentObj.getString("number_rpps"));

                                doctors.add(doc);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        completeTask();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void populateVaccine(){
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                IP_address+"/vaccine/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            res = new JSONArray(response);
                            for(int i = 0; i < res.length(); i++) {
                                JSONObject currentObj = res.getJSONObject(i);
                                Vaccine vac = new Vaccine(
                                        currentObj.getInt("vaccineID"),
                                        currentObj.getString("name"),
                                        currentObj.getString("recommandation"),
                                        currentObj.getInt("validity"));
                                vaccines.add(vac);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        completeTask();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void populateOrdinances(){
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                IP_address+"/patient/"+user.getId()+"/ordinance",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            res = new JSONArray(response);
                            for(int i = 0; i < res.length(); i++) {
                                JSONObject currentObj = res.getJSONObject(i);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date d = sdf.parse(currentObj.getString("date").replace(".000Z", ""));
                                String formattedTime = output.format(d);

                                ordinances.add(new Ordinance(
                                        currentObj.getInt("ordinanceID"),
                                        getDoctorById(currentObj.getInt("doctorID")),
                                        output.parse(formattedTime),
                                        getPrescriptionForOrdinance(currentObj.getInt("ordinanceID"))
                                ));
                            }
                            user.setOrdinances(ordinances);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        completeTask();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public void populateMeds(){
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                IP_address+"/medicament/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            res = new JSONArray(response);
                            for(int i = 0; i < res.length(); i++) {
                                JSONObject currentObj = res.getJSONObject(i);
                                Medicament medicament = new Medicament(
                                        currentObj.getInt("medicamentID"),
                                        currentObj.getString("name"));
                                medicaments.add(medicament);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        completeTask();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }



    public Medicament getMedicamentById(final int id){
        for (Medicament medicament : medicaments){
            if (medicament.getId() == id)
                return medicament;
        }
        return null;
    }


    public ArrayList<Prescription> getPrescriptionForOrdinance(final int ordinanceID){
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        final ArrayList<Prescription> toReturn = new ArrayList();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                IP_address+"/ordinance/"+ordinanceID+"/prescription",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            res = new JSONArray(response);
                            for(int i = 0; i < res.length(); i++) {
                                JSONObject currentObj = res.getJSONObject(i);
                                Prescription prescription = new Prescription(
                                        currentObj.getInt("prescriptionID"),
                                        currentObj.isNull("vaccineID") ?
                                                getMedicamentById(currentObj.getInt("medicamentID")):
                                                getMedicamentById(currentObj.getInt("vaccineID")),
                                        currentObj.getLong("posologie"));
                                toReturn.add(prescription);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
        return toReturn;
    }


    public void completeTask(){
        completedTask++;
        System.out.println("Count : "+completedTask);
        if (completedTask == totalCompletedTask){
            System.out.println("FINISHED :D");
            startActivity(new Intent(getApplicationContext(), MainMenuActivity.class));
        }
    }

    public void reset(){
        doctors = new ArrayList<>();
        vaccines = new ArrayList<>();
        medicaments = new ArrayList<>();
        prescriptions = new ArrayList<>();
        ordinances = new ArrayList<>();

        completedTask = 0;
    }
}
