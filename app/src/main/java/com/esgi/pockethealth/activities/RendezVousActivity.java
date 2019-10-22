package com.esgi.pockethealth.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.esgi.pockethealth.R;
import com.esgi.pockethealth.activities.adapters.RendezVousRowAdapter;
import com.esgi.pockethealth.application.BaseActivity;

import org.json.JSONObject;

public class RendezVousActivity extends BaseActivity {

    private ListView appointmentListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rendez_vous);

        // change status bar color
        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,
                    R.color.rendez_vous_main_color_dark));
        }


        RendezVousRowAdapter adapter = new RendezVousRowAdapter(this, user.getAppointments());

        appointmentListView = findViewById(R.id.menuList);
        appointmentListView.setAdapter(adapter);
    }
}
