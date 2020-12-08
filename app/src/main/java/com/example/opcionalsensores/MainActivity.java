package com.example.opcionalsensores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private List<Sensor> sensorsArray;
    private List<String> sensorsString = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorsArray = sM.getSensorList(Sensor.TYPE_ALL);

        for(Sensor s : sensorsArray) {
            sensorsString.add(s.getName());
        }

        ArrayAdapter<String> sensorAdapter = new ArrayAdapter<>(getApplicationContext(),
                                                                android.R.layout.simple_spinner_item,
                                                                sensorsString);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(sensorAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        //String item = parent.getItemAtPosition(position).toString();

        intent = new Intent(this, SensorActivity.class);
        intent.putExtra("sensorId", Integer.toString(position));
        startActivity(intent);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // No hacemos nada de momento
    }
}