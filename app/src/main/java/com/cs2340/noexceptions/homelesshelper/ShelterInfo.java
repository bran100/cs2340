package com.cs2340.noexceptions.homelesshelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;

/**
 * The shelter information screen of the app
 */
public class ShelterInfo extends AppCompatActivity {
    private Shelter currentShelter;
    private String currentShelterName;
    private Button reserve;
    private final User currentUser = Profile.getUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getCurrentShelterDB();





    }

    private void setUpEditTexts(Shelter currentShelter) {
        String[] editTextsInfo = currentShelter.getEditInfo();
        EditText shelterName = findViewById(R.id.shelterName);
        shelterName.setText(editTextsInfo[0]);

        EditText shelterGender = findViewById(R.id.shelterGender);
        shelterGender.setText(editTextsInfo[1]);

        EditText shelterCapacity = findViewById(R.id.shelterCapacity);
        shelterCapacity.setText(editTextsInfo[2]);

        EditText shelterLongitude = findViewById(R.id.shelterLongitude);
        shelterLongitude.setText(editTextsInfo[3]);

        EditText shelterLatitude = findViewById(R.id.shelterLatitude);
        shelterLatitude.setText(editTextsInfo[4]);

        EditText shelterAddress = findViewById(R.id.shelterAddress);
        shelterAddress.setText(editTextsInfo[5]);

        EditText shelterPhone = findViewById(R.id.shelterPhone);
        shelterPhone.setText(editTextsInfo[6]);
    }

    private void setUpOnClicks() {
        TextView shelters = findViewById(R.id.shelterText);
        shelters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getBaseContext(), Main.class);
                startActivity(toMain);
            }
        });

        TextView profile = findViewById(R.id.profileText);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProfile = new Intent(getBaseContext(), Profile.class);
                startActivity(toProfile);
            }
        });

        TextView map = findViewById(R.id.shelterMapText);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getBaseContext(), Main.class);
                startActivity(toMain);
            }
        });

        TextView settings = findViewById(R.id.userSettingsText);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getBaseContext(), Main.class);
                startActivity(toMain);
            }
        });

        reserve = findViewById(R.id.reserveButton);
        if (currentUser.getClass() == HomelessPerson.class) {
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
                if (currentUser.getClass() == HomelessPerson.class) {
                    HomelessPerson currentHomeless = (HomelessPerson) currentUser;
                    boolean[] boolInfo = currentHomeless.shelterInfoBool(currentShelter);

                    if(!boolInfo[0] && !boolInfo[1]) {
                        Toast fail = Toast.makeText(getApplicationContext(),
                                "Cannot reserve, capacity has been exceeded",
                                Toast.LENGTH_SHORT);
                        fail.setGravity(Gravity.TOP,0,300);
                        fail.show();
                    } else {
                        EditText shelterCapacity = findViewById(R.id.shelterCapacity);
                        shelterCapacity.setText(currentShelter.getCapacity());
                        reserve.setVisibility(View.GONE);
                    }

                    FirebaseDatabase fireBase = FirebaseDatabase.getInstance();
                    DatabaseReference database = fireBase.getReference();
                    DatabaseReference refUsers = database.child("users");
                    DatabaseReference refUser = refUsers.child("User");
                    DatabaseReference ref = refUser.child(currentUser.userName);

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
        if (currentUser.getClass() == HomelessPerson.class) {
            HomelessPerson currentHomeless = (HomelessPerson) currentUser;
            if (currentHomeless.getReserved()) {
                Button reserve = findViewById(R.id.reserveButton);
                reserve.setVisibility(View.INVISIBLE);
            }
        }
    }
    private void getCurrentShelterDB() {
        Intent intent = getIntent();
        String activity = intent.getStringExtra("activity");
        if ("main".equals(activity)) {
            currentShelterName = Main.getCurrentShelter();
        } else {
            currentShelterName = MapsActivity.getCurrentShelter();
        }
        if (getSupportActionBar() != null) {
            ActionBar sab = getSupportActionBar();
            sab.setTitle(currentShelterName);
        }
        FirebaseDatabase fireBase = FirebaseDatabase.getInstance();
        DatabaseReference database = fireBase.getReference();
        DatabaseReference ref = database.child("shelters");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shelter s = snapshot.getValue(Shelter.class);
                    if (s != null ) {
                        String name = s.getName();
                        if (name.equals(currentShelterName)) {
                            s.updateAllNeeded();
                            currentShelter = s;
                            setUpEditTexts(currentShelter);
                            setUpOnClicks();
                            checkUserReserved();
                            break;
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
