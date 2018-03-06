package com.cs2340.noexceptions.homelesshelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    ArrayAdapter<Shelter> shelterView;
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
        ArrayList<Shelter> shelterTest = new ArrayList<>();
        for (String[] shelter : shelters) {
            Shelter currentShelter = new Shelter(shelter[0], shelter[1], shelter[2],
                    shelter[3],shelter[4], shelter[5], shelter[6], shelter[8]);
            shelterMap.put(shelter[1], currentShelter);
            shelterTest.add(currentShelter);
            shelterNames.add(shelter[1]);
        }

        // Create ArrayAdapter for Shelters
        //shelterView = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shelterTest);
        final ListView listView = (ListView) findViewById(R.id.shelterList);
        shelterView = new ShelterAdapter(this, shelterTest);
        listView.setAdapter(shelterView);

        // Search functionality
        EditText inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                shelterView.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Setting up onClickListener to switch to detailed view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object shelterClickedId = listView.getItemAtPosition(i);
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
