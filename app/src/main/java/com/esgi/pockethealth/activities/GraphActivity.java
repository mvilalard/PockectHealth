package com.esgi.pockethealth.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.esgi.pockethealth.R;
import com.esgi.pockethealth.application.BaseActivity;
import com.esgi.pockethealth.application.IData;
import com.esgi.pockethealth.application.RequestManager;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.List;

public class GraphActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);


        // change status bar color
        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,
                    R.color.personnal_main_color_dark));
        }

        final GraphView weightGraph = (GraphView) findViewById(R.id.weight_graph);
        weightGraph.setTitle("Courbe de poids");
        weightGraph.getGridLabelRenderer().setHorizontalAxisTitle("Age(années)");
        weightGraph.getGridLabelRenderer().setVerticalAxisTitle("Poids(kg)");
        weightGraph.getGridLabelRenderer().setGridColor(Color.RED);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(1);
        weightGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));
        weightGraph.getViewport().setScalable(true);

        final GraphView heightGraph = (GraphView) findViewById(R.id.height_graph);
        heightGraph.setTitle("Courbe de taille");
        heightGraph.getGridLabelRenderer().setHorizontalAxisTitle("Age(années)");
        heightGraph.getGridLabelRenderer().setVerticalAxisTitle("Taille(cm)");
        heightGraph.getGridLabelRenderer().setGridColor(Color.BLUE);

        heightGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));
        heightGraph.getViewport().setScalable(true);


        try {

            final JSONObject[] response = {new JSONObject()};
            Thread postTask = new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = "/android/get/height/";
                    RequestManager.executeHttpsRequest(RequestManager.RequestType.GET, url, null);
                }
            });
            postTask.start();
            while (postTask.isAlive());

            System.out.println(response);

            weightGraph.getViewport().setMaxX(getMaxYear(user.getWeights()) - getMinYear(user.getWeights()));
            weightGraph.getViewport().setMaxY(getMaxWeight(user.getWeights()));

            heightGraph.getViewport().setMaxX(getMaxYear(user.getHeights()) - getMinYear(user.getHeights()));
            heightGraph.getViewport().setMaxY(getMaxHeight(user.getHeights()));

            //Get series
            weightGraph.addSeries(getSerieFromList(user.getWeights()));
            heightGraph.addSeries(getSerieFromList(user.getHeights()));

        }
        catch (IllegalArgumentException e) {
            Toast.makeText(GraphActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private LineGraphSeries<DataPoint> getSerieFromList(List<IData> list){
        DataPoint[] data = new DataPoint[list.size()];
        int minYear = getMinYear(list);

        if (list.isEmpty())
            return new LineGraphSeries<>();
        for (int i=0; i<list.size(); i++){
            IData w = list.get(i);
            data[i] = new DataPoint(w.getDate().getYear()-minYear, w.getData());
        }

        return new LineGraphSeries<>(data);
    }

    private int getMinYear(List<IData> list){
        int minYear = 9999;
        for (IData data: list) {
            if (data.getDate().getYear() < minYear){
                minYear = data.getDate().getYear();
            }
        }
        return minYear;
    }

    private int getMaxYear(List<IData> list){
        int maxYear = 0;
        for (IData data: list) {
            if (data.getDate().getYear() > maxYear){
                maxYear = data.getDate().getYear();
            }
        }
        return maxYear;
    }

    private float getMaxWeight(List<IData> list){
        float maxWeight = 0;
        for (IData data: list) {
            if (data.getData() > maxWeight){
                maxWeight = data.getData();
            }
        }
        return maxWeight;
    }

    private float getMaxHeight(List<IData> list){
        float maxHeight = 0;
        for (IData data: list) {
            if (data.getData() > maxHeight){
                maxHeight = data.getData();
            }
        }
        return maxHeight;
    }
}