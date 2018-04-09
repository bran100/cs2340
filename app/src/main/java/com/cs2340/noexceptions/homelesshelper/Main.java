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
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


/**
 * The Main activity representing the list of shelters
 */
public class Main extends AppCompatActivity {
    private static String currentShelter;
    private ArrayAdapter<Shelter> shelterView;
    private final ArrayList<Shelter> shelterTest = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference database = db.getReference();
        DatabaseReference ref = database.child("shelters");
        shelterView = new ShelterAdapter(this, shelterTest);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shelter s = snapshot.getValue(Shelter.class);
                    if (s != null) {
                        s.updateAllNeeded();
                    }
                    shelterTest.add(s);
                    shelterView.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Try to fix this.
        try {
            Thread.sleep(100);
        } catch (InterruptedException ie) {
            Log.d("IntEx", "onCreateMain: Interrupt happened while waiting");
        }


        // Create ArrayAdapter for Shelters
        final ListView listView = findViewById(R.id.shelterList);
        listView.setAdapter(shelterView);

        // Search functionality
        EditText inputSearch = findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Filter viewFilter = shelterView.getFilter();
                viewFilter.filter(charSequence);
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
                shelterInfo.putExtra("activity", "main");
                startActivity(shelterInfo);

            }
        });

        ImageView profile = findViewById(R.id.profile);
        profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProfile = new Intent(getBaseContext(), Profile.class);
                startActivity(toProfile);
            }
        });

        ImageView maps = findViewById(R.id.shelterMap);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMaps = new Intent(getBaseContext(), MapsActivity.class);
                startActivity(toMaps);
            }
        });
    }

    /**
     * A method that will return the current shelter that the user selected
     * @return The current shelter's name clicked
     */
    public static String getCurrentShelter() {
        return currentShelter;
    }
}
