package com.example.smartcradlesystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<TemperaturesRoomData> temperaturesRoomList = new ArrayList<>();
    ArrayList<TemperaturesData> temperaturesList = new ArrayList<>();
    ArrayList<SoundsData> soundsList = new ArrayList<>();
    ArrayList<PlaylistData> playlistList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonTemperaturesRoom = this.findViewById(R.id.buttonTemperaturesRoom);
        buttonTemperaturesRoom.setOnClickListener((View v) -> {
            openActivityTemperaturesRoom();
        });

        Button buttonTemperatures = this.findViewById(R.id.buttonTemperatures);
        buttonTemperatures.setOnClickListener((View v) -> openActivityTemperatures());

        Button buttonSounds = findViewById(R.id.buttonSounds);
        buttonSounds.setOnClickListener((View v) -> openActivitySounds());

        Button buttonPlaylist = findViewById(R.id.buttonPlaylist);
        buttonPlaylist.setOnClickListener((View v) -> openActivityPlaylist());


        JSONparserTemperaturesRoom();

        // check that the data has been retrieved properly
        for(int i = 0; i < temperaturesRoomList.size(); i++)
            Log.d("JSON", temperaturesRoomList.get(i).getId());

        RecyclerView recyclerViewMainTemperaturesRoom = findViewById(R.id.recyclerViewMainTemperaturesRoom);
        RecyclerViewAdapterMainTempRoom recyclerViewAdapterMainTemperaturesRoom = new RecyclerViewAdapterMainTempRoom(temperaturesRoomList);
        RecyclerView.LayoutManager layoutManagerTempRoom = new LinearLayoutManager(this);
        recyclerViewMainTemperaturesRoom.setLayoutManager(layoutManagerTempRoom);

        recyclerViewAdapterMainTemperaturesRoom.setOnItemClickListener((TemperaturesRoomData data) ->
                Toast.makeText(MainActivity.this, "Sound " + data.getId(), Toast.LENGTH_LONG).show());
        recyclerViewMainTemperaturesRoom.setAdapter(recyclerViewAdapterMainTemperaturesRoom);


        // parse the JSON file
        JSONparserTemperatures();

        // check that the data has been retrieved properly
        for(int i = 0; i < temperaturesList.size(); i++)
            Log.d("JSON", temperaturesList.get(i).getId());

        RecyclerView recyclerViewMainTemperatures = findViewById(R.id.recyclerViewMainTemperatures);
        RecyclerViewAdapterMainTemp recyclerViewAdapterMainTemperatures = new RecyclerViewAdapterMainTemp(temperaturesList);
        RecyclerView.LayoutManager layoutManagerTemp = new LinearLayoutManager(this);
        recyclerViewMainTemperatures.setLayoutManager(layoutManagerTemp);

        recyclerViewAdapterMainTemperatures.setOnItemClickListener((TemperaturesData data) ->
                Toast.makeText(MainActivity.this, "Sound " + data.getId(), Toast.LENGTH_LONG).show());
        recyclerViewMainTemperatures.setAdapter(recyclerViewAdapterMainTemperatures);


        JSONparserSounds();
        //Sounds
        RecyclerView recyclerViewMainSounds = findViewById(R.id.recyclerViewMainSounds);
        RecyclerViewAdapterMainSound recyclerViewAdapterMainSounds = new RecyclerViewAdapterMainSound(soundsList);
        RecyclerView.LayoutManager layoutManagerSounds = new LinearLayoutManager(this);
        recyclerViewMainSounds.setLayoutManager(layoutManagerSounds);

        recyclerViewAdapterMainSounds.setOnItemClickListener((SoundsData data) ->
                Toast.makeText(MainActivity.this, "Sound " + data.getId(), Toast.LENGTH_LONG).show());
        recyclerViewMainSounds.setAdapter(recyclerViewAdapterMainSounds);


        JSONparserPlaylist();
        RecyclerView recyclerViewMainPlaylist = findViewById(R.id.recyclerViewMainPlaylist);
        RecyclerViewAdapterMainPlaylist recyclerViewAdapterMainPlaylist = new RecyclerViewAdapterMainPlaylist(playlistList);
        RecyclerView.LayoutManager layoutManagerPlaylist = new LinearLayoutManager(this);
        recyclerViewMainPlaylist.setLayoutManager(layoutManagerPlaylist);

        recyclerViewAdapterMainPlaylist.setOnItemClickListener((PlaylistData data) ->
                Toast.makeText(MainActivity.this, "Playlist " + data.getId(), Toast.LENGTH_LONG).show());
        recyclerViewMainPlaylist.setAdapter(recyclerViewAdapterMainPlaylist);

    }


    public void JSONparserTemperaturesRoom() {
        Log.d("JSON", loadJSONFromAsset());

        try {
            // create a JSON object
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            //retrieves the "sounds" array
            JSONArray m_jArry = obj.getJSONArray("structure").getJSONObject(0).getJSONArray("temperaturesRoom");
            Log.i("JSONparser", "JSONparser");
            Log.i("JSONparser", m_jArry.toString());

            TemperaturesRoomData temperaturesRoom_object;
            int valueSound2 = 0;
            // for each item from sounds array
            for (int i = 0; i < m_jArry.length(); i++) {
                // get the JSON object
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                int date = jo_inside.getInt("date");

                if (date > valueSound2) {
                    temperaturesRoomList = new ArrayList<>();
                    valueSound2 = date;
                    Log.d("valueSound2: ", String.valueOf(valueSound2));

                    // initializes the sounds_object
                    temperaturesRoom_object = new TemperaturesRoomData(
                            // get each attribute
                            jo_inside.getString("id"),
                            jo_inside.getString("date"),
                            jo_inside.getString("value")
                    );

                    Log.i("emp.getValue()", temperaturesRoom_object.getValue());
                    temperaturesRoomList.add(temperaturesRoom_object);
                    Log.d("temperaturesRoomList", temperaturesRoomList.toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void JSONparserTemperatures() {
        Log.d("JSON", loadJSONFromAsset());

        try {
            // create a JSON object
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            //retrieves the "sounds" array
            JSONArray m_jArry = obj.getJSONArray("structure").getJSONObject(1).getJSONArray("temperatures");
            Log.i("JSONparser", "JSONparser");
            Log.i("JSONparser", m_jArry.toString());

            TemperaturesData temperatures_object;
            int valueSound2 = 0;
            // for each item from sounds array
            for (int i = 0; i < m_jArry.length(); i++) {
                // get the JSON object
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                int date = jo_inside.getInt("date");

                if (date > valueSound2) {
                    temperaturesList = new ArrayList<>();
                    valueSound2 = date;

                    //Log.d("JSON", soundsList.get(i).getId());
                    Log.d("valueSound2: ", String.valueOf(valueSound2));

                    // initializes the sounds_object
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void JSONparserSounds() {
        Log.d("JSON", loadJSONFromAsset());

        try {
            // create a JSON object
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            //retrieves the "sounds" array
            JSONArray m_jArry = obj.getJSONArray("structure").getJSONObject(2).getJSONArray("sounds");
            Log.i("JSONparser", "JSONparser");
            Log.i("JSONparser", m_jArry.toString());

            SoundsData sounds_object;
            int valueSound2 = 0;
            // for each item from sounds array
            for (int i = 0; i < m_jArry.length(); i++) {
                // get the JSON object
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                int date = jo_inside.getInt("date");

                if (date > valueSound2) {
                    soundsList = new ArrayList<>();
                    valueSound2 = date;
                    //Log.d("JSON", soundsList.get(i).getId());
                    Log.d("valueSound2: ", String.valueOf(valueSound2));

                    // initializes the sounds_object
                    sounds_object = new SoundsData(
                            // get each attribute
                            jo_inside.getString("id"),
                            jo_inside.getString("date"),
                            jo_inside.getString("value")
                    );

                    Log.i("emp.getValue()", sounds_object.getValue());
                    soundsList.add(sounds_object);
                    Log.d("soundsList", soundsList.toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void JSONparserPlaylist() {
        Log.d("JSON", loadJSONFromAsset());

        try {
            // create a JSON object
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            //retrieves the "sounds" array
            JSONArray m_jArry = obj.getJSONArray("structure").getJSONObject(3).getJSONArray("playlist");
            Log.i("JSONparser", "JSONparser");
            Log.i("JSONparser", m_jArry.toString());

            PlaylistData playlist_object;
            int valueSound2 = 0;
            // for each item from sounds array
            for (int i = 0; i < m_jArry.length(); i++) {
                // get the JSON object
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                int date = jo_inside.getInt("date");

                if (date > valueSound2) {
                    playlistList = new ArrayList<>();
                    valueSound2 = date;

                    //Log.d("JSON", soundsList.get(i).getId());
                    Log.d("valueSound2: ", String.valueOf(valueSound2));

                    // initializes the sounds_object
                    playlist_object = new PlaylistData(
                            // get each attribute
                            jo_inside.getString("id"),
                            jo_inside.getString("date"),
                            jo_inside.getString("value")
                    );

                    Log.i("emp.getValue()", playlist_object.getValue());
                    playlistList.add(playlist_object);
                    Log.d("playlistList", playlistList.toString());
                }
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

    public void openActivityTemperaturesRoom(){
        Intent intent = new Intent(this, TemperaturesRoomActivity.class);
        startActivity(intent);
    }

    public void openActivityTemperatures(){
        Intent intent = new Intent(this, TemperaturesActivity.class);
        startActivity(intent);
    }

    public void openActivitySounds(){
        Intent intent = new Intent(this, SoundsActivity.class);
        startActivity(intent);
    }

    public void openActivityPlaylist(){
        Intent intent = new Intent(this, PlaylistActivity.class);
        startActivity(intent);
    }
}