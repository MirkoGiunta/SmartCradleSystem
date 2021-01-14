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

public class TemperaturesRoomActivity extends Activity {
    ArrayList<TemperaturesRoomData> temperaturesRoomList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatures_room);

        Button buttonMainActivity = findViewById(R.id.buttonMainActivity);

        buttonMainActivity.setOnClickListener((View v) -> openActivityMainActivity());

        // parse the JSON file
        JSONparserTemperatures(); //Search by Depaartment

        // check that the data has been retrieved properly
        for(int i = 0; i < temperaturesRoomList.size(); i++)
            Log.d("JSON", temperaturesRoomList.get(i).getId());

        RecyclerView recyclerViewTemperaturesRoom = findViewById(R.id.recyclerViewTemperaturesRoom);
        RecyclerViewAdapterTemperaturesRoom recyclerViewAdapterTemperaturesRoom = new RecyclerViewAdapterTemperaturesRoom(temperaturesRoomList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewTemperaturesRoom.setLayoutManager(layoutManager);

        recyclerViewAdapterTemperaturesRoom.setOnItemClickListener((TemperaturesRoomData data) ->
                Toast.makeText(TemperaturesRoomActivity.this, "Temperature " + data.getId(), Toast.LENGTH_LONG).show());
        recyclerViewTemperaturesRoom.setAdapter(recyclerViewAdapterTemperaturesRoom);
    }

    public void JSONparserTemperatures() {
        Log.d("JSON", loadJSONFromAsset());

        try {
            // create a JSON object
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            //retrieves the "temperatures" array
            JSONArray m_jArry = obj.getJSONArray("structure").getJSONObject(0).getJSONArray("temperaturesRoom");
            Log.i("JSONparser", "JSONparser");
            Log.i("JSONparser", m_jArry.toString());

            TemperaturesRoomData temperaturesRoom_object;

            // for each item from temperatures array
            for (int i = 0; i < m_jArry.length(); i++) {
                // get the JSON object
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                // initializes the temperatures_object
                temperaturesRoom_object = new TemperaturesRoomData(
                        // get each attribute
                        jo_inside.getString("id"),
                        jo_inside.getString("date"),
                        jo_inside.getString("value")
                );

                Log.i("emp.getValue()", temperaturesRoom_object.getValue());
                temperaturesRoomList.add(temperaturesRoom_object);
                Log.d("temperaturesList", temperaturesRoomList.toString());
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
