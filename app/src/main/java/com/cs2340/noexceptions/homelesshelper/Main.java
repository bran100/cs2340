package com.cs2340.noexceptions.homelesshelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main extends AppCompatActivity {
    private User user;
    static Map<String,Shelter> shelterMap;
    static String currentShelter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] userInfo = LoginActivity.currentUser;
        if (userInfo[2].equals("Admin")) {
            user = new Admin(null, null, null);
        } else {
            user = new HomelessPerson(null, null, null);
        }
        InputStream inputStream = getResources().openRawResource(R.raw.homeless_shelter_database);
        ShelterCSVReader csv = new ShelterCSVReader(inputStream);
        List<String[]> shelters = csv.readShelters();
        shelterMap = new HashMap<>();
        List<String> shelterNames = new ArrayList<>();
        for (String[] shelter : shelters) {
            Log.d("TAG", Arrays.toString(shelter));
            Log.d("TAG", shelter[1]);
            for (String i : shelter) {
                Log.d("TAG", i);
            }
            shelterMap.put(shelter[1],new Shelter(shelter[0], shelter[1], shelter[2],
                    shelter[3],shelter[4], shelter[5], shelter[6], shelter[8]));
            shelterNames.add(shelter[1]);
        }

        ArrayAdapter<String> shelterView = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shelterNames);
        final ListView listView = (ListView) findViewById(R.id.shelterList);
        listView.setAdapter(shelterView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object shelterClickedId = listView.getItemAtPosition(i);
                Log.d("TAG", shelterClickedId.toString());
                currentShelter = shelterClickedId.toString();
                Intent shelterInfo = new Intent(getBaseContext(), ShelterInfo.class);
                startActivity(shelterInfo);

            }
        });
    }

    public void toHomeScreen(View v) {
        Intent toHome = new Intent(getBaseContext(), Home.class);
        startActivity(toHome);
    }
}
