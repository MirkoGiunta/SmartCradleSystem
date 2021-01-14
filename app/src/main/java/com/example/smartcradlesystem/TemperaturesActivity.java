package com.example.smartcradlesystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class TemperaturesActivity extends Activity {
    ArrayList<TemperaturesData> temperaturesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatures);

        Button buttonMainActivity = findViewById(R.id.buttonMainActivity);
        buttonMainActivity.setOnClickListener((View v) -> openActivityMainActivity());

        // parse the JSON file
        JSONparserTemperatures(); //Search by Depaartment

        // check that the data has been retrieved properly
        for(int i = 0; i < temperaturesList.size(); i++)
            Log.d("JSON", temperaturesList.get(i).getId());

        RecyclerView recyclerViewTemperaturess = findViewById(R.id.recyclerViewTemperatures);
        RecyclerViewAdapterTemperatures recyclerViewAdaptertemperatures = new RecyclerViewAdapterTemperatures(temperaturesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTemperaturess.setLayoutManager(layoutManager);

        recyclerViewAdaptertemperatures.setOnItemClickListener((TemperaturesData data) ->
                Toast.makeText(TemperaturesActivity.this, "Temperature " + data.getId(), Toast.LENGTH_LONG).show());
        recyclerViewTemperaturess.setAdapter(recyclerViewAdaptertemperatures);
    }

    public void JSONparserTemperatures() {
        Log.d("JSON", loadJSONFromAsset());

        try {
            // create a JSON object
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            //retrieves the "temperatures" array
            JSONArray m_jArry = obj.getJSONArray("structure").getJSONObject(1).getJSONArray("temperatures");
            Log.i("JSONparser", "JSONparser");
            Log.i("JSONparser", m_jArry.toString());

            TemperaturesData temperatures_object;

            // for each item from temperatures array
            for (int i = 0; i < m_jArry.length(); i++) {
                // get the JSON object
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                // initializes the temperatures_object
                temperatures_object = new TemperaturesData(
                        // get each attribute
                        jo_inside.getString("id"),
                        jo_inside.getString("date"),
                        jo_inside.getString("value")
                );

                Log.i("emp.getValue()", temperatures_object.getValue());
                temperaturesList.add(temperatures_object);
                Log.d("temperaturesList", temperaturesList.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("cradleDB.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void openActivityMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    
}
