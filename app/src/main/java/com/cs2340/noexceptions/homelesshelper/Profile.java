package com.cs2340.noexceptions.homelesshelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private final FirebaseDatabase fireBase = FirebaseDatabase.getInstance();

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
        TextView shelters = findViewById(R.id.shelterListText);
        shelters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(getBaseContext(), Main.class);
                startActivity(toMain);
            }
        });

        TextView map = findViewById(R.id.shelterMapText);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMap = new Intent(getBaseContext(), MapsActivity.class);
                startActivity(toMap);
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

        shelterName = findViewById(R.id.shelterName);
        vacate = findViewById(R.id.cancelReservation);
        vacate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference database = fireBase.getReference();
                DatabaseReference ref = database.child("shelters");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Shelter s = snapshot.getValue(Shelter.class);
                            String[] shelterInfo = null;
                            String[] homelessInfo = currentHomeless.getInfo();
                            if (s != null) {
                                shelterInfo = s.getEditInfo();
                            }
                            if ((s != null)
                                    && (shelterInfo[0].equals(homelessInfo[0]))) {

                                s.updateCapacity(Integer.parseInt(homelessInfo[1]) * -1);

                                DatabaseReference database = fireBase.getReference();
                                DatabaseReference refUsers = database.child("users");
                                DatabaseReference refUsers2 = refUsers.child("User");
                                DatabaseReference ref = refUsers2.child(currentHomeless.userName);
                                DatabaseReference refShelters = database.child("shelters");
                                DatabaseReference refShelter = refShelters.child(shelterInfo[8]);

                                Map<String, Object> updateShelterMap = new HashMap<>();
                                updateShelterMap.put("capacity", shelterInfo[2]);
                                Map<String, Object> updateMap = new HashMap<>();
                                updateMap.put("reserved", false);
                                updateMap.put("reservedShelter", "");
                                currentHomeless.homelessInfo();
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
                Editable numPeopleText = numPeople.getText();
                if ("".equals(numPeopleText.toString())) {
                    numPeople.setError("Please enter a valid number (Greater than 0");
                } else {
                    int numToRecord = Integer.parseInt(numPeopleText.toString());
                    if (numToRecord <= 0) {
                        numPeople.setError("Please enter a valid number (Greater than 0)");
                    } else {
                        if (user.getClass() == HomelessPerson.class) {
                            HomelessPerson currentUser = (HomelessPerson) user;
                            currentUser.setNumPeople(numToRecord);
                            DatabaseReference database = fireBase.getReference();
                            DatabaseReference refUsers = database.child("users");
                            DatabaseReference refUsers2 = refUsers.child("User");
                            DatabaseReference ref = refUsers2.child(currentUser.getUserName());
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
        DatabaseReference database = fireBase.getReference();
        DatabaseReference ref = database.child("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        DataSnapshot ref = userSnapshot.child("userName");
                        Object userNameObj = ref.getValue();
                        if ((userNameObj != null) && (snapshot.getValue() != null)) {
                            String userNameString = userNameObj.toString();
                            Object snapshotObj = snapshot.getValue();
                            if ((userNameString.equals(userInfo))) {
                                if (("Admin".equals(snapshotObj.toString()))) {
                                    setUser(userSnapshot.getValue(Admin.class));
                                } else {
                                    setUser(userSnapshot.getValue(HomelessPerson.class));
                                    currentHomeless = (HomelessPerson) user;
                                }
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
    }

    /**
     * A method that will return the current user
     * @return The current user
     */
    public static User getUser() {
        return user;
    }
    private static void setUser(User u) {
        user = u;
    }
}
