package com.example.opcionalsensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private TextView xValueField, yValueField, zValueField;
    private TextView yLabel, zLabel;
    private Sensor sensor;
    private List<String> sensorsArray;
    private int sensorId;
    private boolean first_Iter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        SensorManager sM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sM.getSensorList(Sensor.TYPE_ALL);

        // 1. Get the sensor
        Intent intent = getIntent();
        sensorId = Integer.parseInt(intent.getStringExtra("sensorId"));
        Sensor s = sensors.get(sensorId);
        setTitle(s.getName());

        // 2. Get fields
        xValueField = findViewById(R.id.xValue);
        yValueField = findViewById(R.id.yValue);
        zValueField = findViewById(R.id.zValue);
        yLabel = findViewById(R.id.yLabel);
        zLabel = findViewById(R.id.zLabel);

        // Register listener
        sM.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int n = event.values.length;

        if(first_Iter) {
            disableFields(n);
            first_Iter = false;
        }

        if (n >= 3) {
            zValueField.setText(Float.toString(event.values[2]));
        } if(n >= 2) {
            yValueField.setText(Float.toString(event.values[1]));
        }
        xValueField.setText(Float.toString(event.values[0]));
    }

    private void disableFields(int nF) {
        if(nF <= 2) {
            TextView zLabel = findViewById(R.id.zLabel);
            zLabel.setVisibility(View.INVISIBLE);
            zValueField.setVisibility(View.INVISIBLE);
        } if(nF ==  1) {
            TextView yLabel = findViewById(R.id.yLabel);
            yLabel.setVisibility(View.INVISIBLE);
            yValueField.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nothing to be done by the moment
    }
}