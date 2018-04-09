package com.cs2340.noexceptions.homelesshelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
        EditText shelterName = findViewById(R.id.shelterName);
        shelterName.setText(currentShelter.getName());

        EditText shelterGender = findViewById(R.id.shelterGender);
        shelterGender.setText(currentShelter.getGender());

        EditText shelterCapacity = findViewById(R.id.shelterCapacity);
        shelterCapacity.setText(currentShelter.getCapacity());

        EditText shelterLongitude = findViewById(R.id.shelterLongitude);
        shelterLongitude.setText(currentShelter.getLongitude());

        EditText shelterLatitude = findViewById(R.id.shelterLatitude);
        shelterLatitude.setText(currentShelter.getLatitude());

        EditText shelterAddress = findViewById(R.id.shelterAddress);
        shelterAddress.setText(currentShelter.getAddress());

        EditText shelterPhone = findViewById(R.id.shelterPhone);
        shelterPhone.setText(currentShelter.getTelephoneNumber());
    }

    private void setUpOnClicks() {
        ImageView shelters = findViewById(R.id.shelterList);
        shelters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getBaseContext(), Main.class);
                startActivity(toMain);
            }
        });

        ImageView profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProfile = new Intent(getBaseContext(), Profile.class);
                startActivity(toProfile);
            }
        });

        ImageView map = findViewById(R.id.shelterMap);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getBaseContext(), Main.class);
                startActivity(toMain);
            }
        });

        ImageView settings = findViewById(R.id.userSettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getBaseContext(), Main.class);
                startActivity(toMain);
            }
        });

        reserve = findViewById(R.id.reserveButton);
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
                    if(!currentHomeless.reserveVacancy(currentShelter)
                            && !currentHomeless.getReserved()) {
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
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference ref = database.child("users").child("User")
                            .child(currentUser.userName);
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
            getSupportActionBar().setTitle(currentShelterName);
        }
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("shelters");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shelter s = snapshot.getValue(Shelter.class);
                    if ((s != null) && (s.getName().equals(currentShelterName))) {
                        s.updateAllNeeded();
                        currentShelter = s;
                        setUpEditTexts(currentShelter);
                        setUpOnClicks();
                        checkUserReserved();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
