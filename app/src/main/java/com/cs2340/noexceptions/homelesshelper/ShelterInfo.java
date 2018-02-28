package com.cs2340.noexceptions.homelesshelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShelterInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Shelter currentShelter = Main.shelterMap.get(Main.currentShelter);
        getSupportActionBar().setTitle(currentShelter.name);
        setUpEditTexts(currentShelter);

        Button backButton = (Button) findViewById(R.id.shelterListBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getBaseContext(), Main.class);
                startActivity(toMain);
            }

        });



    }

    private void setUpEditTexts(Shelter currentShelter) {
        EditText shelterName = (EditText) findViewById(R.id.shelterName);
        shelterName.setText(currentShelter.name);

        EditText shelterGender = (EditText) findViewById(R.id.shelterGender);
        shelterGender.setText(currentShelter.gender);

        EditText shelterCapacity = (EditText) findViewById(R.id.shelterCapacity);
        shelterCapacity.setText(currentShelter.capacity);

        EditText shelterLongitude = (EditText) findViewById(R.id.shelterLongitude);
        shelterLongitude.setText(currentShelter.longitude);

        EditText shelterLatitude = (EditText) findViewById(R.id.shelterLatitude);
        shelterLatitude.setText(currentShelter.latitude);

        EditText shelterAddress = (EditText) findViewById(R.id.shelterAddress);
        shelterAddress.setText(currentShelter.address);

        EditText shelterPhone = (EditText) findViewById(R.id.shelterPhone);
        shelterPhone.setText(currentShelter.telephoneNumber);
    }

}
