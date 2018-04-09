package com.cs2340.noexceptions.homelesshelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * An activity for the profile screen of the app
 */
public class Profile extends AppCompatActivity {
    private static User user;
    private EditText numPeople;
    private HomelessPerson currentHomeless;
    private Button vacate;
    private EditText shelterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpUser();
    }

    private void setUpOnClicks() {
        Button logOut = findViewById(R.id.logoutButton);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toHome = new Intent(getBaseContext(), Home.class);
                startActivity(toHome);
            }
        });
        final ImageView shelters = findViewById(R.id.shelterList);
        shelters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getBaseContext(), Main.class);
                startActivity(toMain);
            }
        });

        ImageView map = findViewById(R.id.shelterMap);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMap = new Intent(getBaseContext(), MapsActivity.class);
                startActivity(toMap);
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
        shelterName = findViewById(R.id.shelterName);
        vacate = findViewById(R.id.cancelReservation);
        vacate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref = database.child("shelters");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Shelter s = snapshot.getValue(Shelter.class);
                            if ((s != null)
                                    && (s.getName().equals(currentHomeless.getReservedShelter()))) {
                                s.updateCapacity(currentHomeless.numPeople * -1);
                                DatabaseReference database = FirebaseDatabase
                                        .getInstance().getReference();
                                DatabaseReference ref = database.child("users").child("User")
                                        .child(currentHomeless.userName);
                                DatabaseReference refShelter = database.child("shelters")
                                        .child(s.getId());
                                Map<String, Object> updateShelterMap = new HashMap<>();
                                updateShelterMap.put("capacity", s.getCapacity());
                                Map<String, Object> updateMap = new HashMap<>();
                                updateMap.put("reserved", false);
                                updateMap.put("reservedShelter", "");
                                currentHomeless.setReserved(false);
                                currentHomeless.setReservedShelter("");
                                ref.updateChildren(updateMap);
                                refShelter.updateChildren(updateShelterMap);
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                if (currentHomeless != null) {
                    shelterName.setText("No Shelter Reserved");
                    vacate.setVisibility(View.GONE);
                }
            }
        });
    }
    private void recordNumPeople() {
        numPeople = findViewById(R.id.numPeople);
        numPeople.setText(Integer.toString(currentHomeless.getNumPeople()));
        Button submit = findViewById(R.id.recordNumPeople);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(numPeople.getText().toString())) {
                    numPeople.setError("Please enter a valid number (Greater than 0");
                } else {
                    int numToRecord = Integer.parseInt(numPeople.getText().toString());
                    if (numToRecord <= 0) {
                        numPeople.setError("Please enter a valid number (Greater than 0)");
                    } else {
                        if (user instanceof HomelessPerson) {
                            HomelessPerson currentUser = (HomelessPerson) user;
                            currentUser.setNumPeople(numToRecord);
                            DatabaseReference database = FirebaseDatabase
                                    .getInstance().getReference();
                            DatabaseReference ref = database.child("users")
                                    .child("User").child(currentUser.getUserName());
                            Map<String, Object> updateMap = new HashMap<>();
                            updateMap.put("numPeople", numToRecord);
                            ref.updateChildren(updateMap);
                        }
                    }
                }
            }
        });
    }
    private void checkReserved() {
        Button vacate = findViewById(R.id.cancelReservation);
        EditText shelterName = findViewById(R.id.shelterName);
        if (currentHomeless != null) {
            if (currentHomeless.getReserved()) {
                shelterName.setText(currentHomeless.getReservedShelter());
                vacate.setVisibility(View.VISIBLE);
            } else {
                vacate.setVisibility(View.GONE);
                shelterName.setText("No shelter reserved");
            }
        }
    }
    private void setUpUser() {
        final String userInfo = LoginActivity.currentUser;
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("TAG", snapshot.toString());
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        Object userNameObj = userSnapshot.child("userName").getValue();
                        if ((userNameObj != null) && (userNameObj.toString().equals(userInfo))) {
                            if ((snapshot.getValue() != null)
                                    && ("Admin".equals(snapshot.getValue().toString()))) {
                                user = userSnapshot.getValue(Admin.class);
                            } else {
                                user = userSnapshot.getValue(HomelessPerson.class);
                                currentHomeless = (HomelessPerson) user;
                            }
                        }
                    }
                }
                checkReserved();
                setUpOnClicks();
                recordNumPeople();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method that will return the current user
     * @return The current user
     */
    public static User getUser() {
        return user;
    }
}