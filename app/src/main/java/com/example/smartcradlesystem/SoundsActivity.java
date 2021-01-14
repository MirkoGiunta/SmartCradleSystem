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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SoundsActivity extends AppCompatActivity {
    ArrayList<SoundsData> soundsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sounds);

        Button buttonMainActivity = (Button) findViewById(R.id.buttonMainActivity);

        buttonMainActivity.setOnClickListener((View v) -> {
            openActivityMainActivity();
            //startActivity(new Intent(MainActivity.this, DepartmentsActivity.class));
        });

        // Write a message to the database
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Sound");

        myRef.setValue("Hello, World!");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("TAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference tableOneRef = rootRef.child("Sound");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> list = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String pic = ds.child("2020-04-12").getValue(String.class);
                    list.add(pic);
                }
                Log.d("TAG", list.get(0));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        tableOneRef.addListenerForSingleValueEvent(eventListener);*/

        // parse the JSON file
        JSONparsersounds(); //Search by Depaartment

        // check that the data has been retrieved properly
        for(int i = 0; i < soundsList.size(); i++)
            Log.d("JSON", soundsList.get(i).getId());

        RecyclerView recyclerViewsounds = (RecyclerView) findViewById(R.id.recyclerViewSounds);
        RecyclerViewAdapterSounds recyclerViewAdapterSounds = new RecyclerViewAdapterSounds(soundsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewsounds.setLayoutManager(layoutManager);


        recyclerViewAdapterSounds.setOnItemClickListener((SoundsData data) ->
                Toast.makeText(SoundsActivity.this, "Sound " + data.getId(), Toast.LENGTH_LONG).show());

        recyclerViewsounds.setAdapter(recyclerViewAdapterSounds);
    }

    public void JSONparsersounds() {
        Log.d("JSON", loadJSONFromAsset());

        try {
            // create a JSON object
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            //retrieves the "sounds" array
            JSONArray m_jArry = obj.getJSONArray("structure").getJSONObject(2).getJSONArray("sounds");
            Log.i("JSONparser", "JSONparser");
            Log.i("JSONparser", m_jArry.toString());

            SoundsData sounds_object;

            //Bundle extras = getIntent().getExtras();
            //String filter = extras.getString("filter");

            // for each item from sounds array
            for (int i = 0; i < m_jArry.length(); i++) {
                // get the JSON object
                JSONObject jo_inside = m_jArry.getJSONObject(i);

                // initializes the sounds_object
                sounds_object = new SoundsData(
                        // get each attribute
                        jo_inside.getString("id"),
                        jo_inside.getString("date"),
                        jo_inside.getString("value")
                );

                Log.i("emp.getValue()", sounds_object.getValue());

                //Filter by department
                /*if (sounds_object.getValue().equals(filter)) {
                    Log.i("EMP.getValue()", sounds_object.getValue());
                    // add the new object to the soundsList
                    soundsList.add(sounds_object);
                }*/

                soundsList.add(sounds_object);
                Log.d("soundsList", soundsList.toString());
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
