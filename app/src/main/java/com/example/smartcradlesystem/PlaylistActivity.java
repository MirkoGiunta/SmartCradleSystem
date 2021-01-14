package com.example.smartcradlesystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {
    ArrayList<PlaylistData> playlistList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        Button buttonMainActivity = findViewById(R.id.buttonMainActivity);

        buttonMainActivity.setOnClickListener((View v) -> {
            openActivityMainActivity();
            //startActivity(new Intent(MainActivity.this, DepartmentsActivity.class));
        });

        // parse the JSON file
        JSONparsersounds(); //Search by Depaartment

        // check that the data has been retrieved properly
        for(int i = 0; i < playlistList.size(); i++)
            Log.d("JSON", playlistList.get(i).getId());

        RecyclerView recyclerViewPlaylist = findViewById(R.id.recyclerViewPlaylist);
        RecyclerViewAdapterPlaylist recyclerViewAdapterPlaylist = new RecyclerViewAdapterPlaylist(playlistList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewPlaylist.setLayoutManager(layoutManager);


        recyclerViewAdapterPlaylist.setOnItemClickListener((PlaylistData data) ->
                Toast.makeText(PlaylistActivity.this, "Playlist " + data.getId(), Toast.LENGTH_LONG).show());

        recyclerViewPlaylist.setAdapter(recyclerViewAdapterPlaylist);
    }

    public void JSONparsersounds() {
        Log.d("JSON", loadJSONFromAsset());

        try {
            // create a JSON object
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            //retrieves the "sounds" array
            JSONArray m_jArry = obj.getJSONArray("structure").getJSONObject(3).getJSONArray("playlist");
            Log.i("JSONparser", "JSONparser");
            Log.i("JSONparser", m_jArry.toString());

            PlaylistData playlist_object;

            //Bundle extras = getIntent().getExtras();
            //String filter = extras.getString("filter");

            // for each item from sounds array
            for (int i = 0; i < m_jArry.length(); i++) {
                // get the JSON object
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                // initializes the sounds_object
                playlist_object = new PlaylistData(
                        // get each attribute
                        jo_inside.getString("id"),
                        jo_inside.getString("date"),
                        jo_inside.getString("value")
                );

                Log.i("emp.getValue()", playlist_object.getValue());

                //Filter by department
                /*if (sounds_object.getValue().equals(filter)) {
                    Log.i("EMP.getValue()", sounds_object.getValue());
                    // add the new object to the soundsList
                    soundsList.add(sounds_object);
                }*/

                playlistList.add(playlist_object);
                Log.d("playlistList", playlistList.toString());
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
