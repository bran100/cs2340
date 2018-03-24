package com.cs2340.noexceptions.homelesshelper;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ShelterInfo extends AppCompatActivity {
    private Shelter currentShelter;
    private Button reserve;
    private User currentUser = Profile.getUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        currentShelter = Main.shelterMap.get(Main.currentShelter);
        getSupportActionBar().setTitle(currentShelter.getName());
        setUpEditTexts(currentShelter);
        setUpOnClicks();
        checkUserReserved();





    }

    private void setUpEditTexts(Shelter currentShelter) {
        EditText shelterName = (EditText) findViewById(R.id.shelterName);
        shelterName.setText(currentShelter.getName());

        EditText shelterGender = (EditText) findViewById(R.id.shelterGender);
        shelterGender.setText(currentShelter.getGender());

        EditText shelterCapacity = (EditText) findViewById(R.id.shelterCapacity);
        shelterCapacity.setText(currentShelter.getCapacity());

        EditText shelterLongitude = (EditText) findViewById(R.id.shelterLongitude);
        shelterLongitude.setText(currentShelter.getLongitude());

        EditText shelterLatitude = (EditText) findViewById(R.id.shelterLatitude);
        shelterLatitude.setText(currentShelter.getLatitude());

        EditText shelterAddress = (EditText) findViewById(R.id.shelterAddress);
        shelterAddress.setText(currentShelter.getAddress());

        EditText shelterPhone = (EditText) findViewById(R.id.shelterPhone);
        shelterPhone.setText(currentShelter.getTelephoneNumber());
    }

    private void setUpOnClicks() {
        ImageView shelters = (ImageView) findViewById(R.id.shelterList);
        shelters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getBaseContext(), Main.class);
                startActivity(toMain);
            }
        });

        ImageView profile = (ImageView) findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProfile = new Intent(getBaseContext(), Profile.class);
                startActivity(toProfile);
            }
        });

        ImageView map = (ImageView) findViewById(R.id.shelterMap);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getBaseContext(), Main.class);
                startActivity(toMain);
            }
        });

        ImageView settings = (ImageView) findViewById(R.id.userSettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getBaseContext(), Main.class);
                startActivity(toMain);
            }
        });

        reserve = (Button) findViewById(R.id.reserveButton);
        if (currentUser instanceof HomelessPerson) {
            HomelessPerson currentHomeless = (HomelessPerson) currentUser;
            if (currentHomeless.getReserved()) {
                reserve.setVisibility(View.GONE);
            } else {
                reserve.setVisibility(View.VISIBLE);
            }
        }
        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser instanceof HomelessPerson) {
                    HomelessPerson currentHomeless = (HomelessPerson) currentUser;
                    if(!currentHomeless.reserveVacancy(currentShelter) && !currentHomeless.getReserved()) {
                        Toast fail = Toast.makeText(getApplicationContext(), "Cannot reserve, capacity has been exceeded", Toast.LENGTH_SHORT);
                        fail.setGravity(Gravity.TOP,0,300);
                        fail.show();
                    } else {
                        EditText shelterCapacity = (EditText) findViewById(R.id.shelterCapacity);
                        shelterCapacity.setText(currentShelter.getCapacity());
                        reserve.setVisibility(View.GONE);
                    }
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference ref = database.child("users").child("User").child(currentUser.userName);
                    Map<String,Object> updateMap = new HashMap<>();
                    updateMap.put("reserved", true);
                    updateMap.put("reservedShelter", currentShelter.getName());
                    ref.updateChildren(updateMap);
                }
            }
        });
    }
    private  void checkUserReserved() {
        User currentUser = Profile.getUser();
        if (currentUser instanceof HomelessPerson) {
            HomelessPerson currentHomeless = (HomelessPerson) currentUser;
            if (currentHomeless.getReserved()) {
                Button reserve = (Button) findViewById(R.id.reserveButton);
                reserve.setVisibility(View.INVISIBLE);
            }
        }
    }
}
