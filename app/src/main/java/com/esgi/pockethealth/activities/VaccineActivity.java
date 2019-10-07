package com.esgi.pockethealth.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.esgi.pockethealth.R;
import com.esgi.pockethealth.activities.adapters.VaccineRowAdapter;
import com.esgi.pockethealth.application.BaseActivity;

public class VaccineActivity extends BaseActivity {

    private ListView vaccineListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);


        // change status bar color
        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,
                    R.color.vaccine_main_color_dark));
        }

        VaccineRowAdapter adapter = new VaccineRowAdapter(this, user.getRecalls());

        vaccineListView = findViewById(R.id.vaccine_list);
        vaccineListView.setAdapter(adapter);
        vaccineListView.setBackgroundResource(R.color.vaccine_main_color);

    }

}
