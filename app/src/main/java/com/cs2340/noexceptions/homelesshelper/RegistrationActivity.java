package com.cs2340.noexceptions.homelesshelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * An activity representing the registration screen for the app
 */
public class RegistrationActivity extends AppCompatActivity {
    private EditText usernameView;
    private EditText passwordView;
    private Spinner userTypeView;
    private EditText nameView;
    private final FirebaseDatabase fireBase = FirebaseDatabase.getInstance();
    private DatabaseReference database;
    private boolean userExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            ActionBar sab = getSupportActionBar();
            sab.setTitle("Registration");
        }

        database = fireBase.getReference();


        nameView = findViewById(R.id.name);
        usernameView = findViewById(R.id.username);
        passwordView = findViewById(R.id.password);
        userTypeView = findViewById(R.id.userType);
        userTypeView.setPrompt("User Type");












        ArrayAdapter<String> adapterClass = new ArrayAdapter<>(
                this,android.R.layout.simple_spinner_item, new String[]{"Admin", "User"});
        adapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeView.setAdapter(adapterClass);

        Button registrationButton = findViewById(R.id.registrationButton);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toHome = new Intent(getBaseContext(), Home.class);
                startActivity(toHome);
            }

        });

    }

    private void attemptRegistration() {
        Editable userText = usernameView.getText();
        Editable passText = passwordView.getText();
        Editable nameText = nameView.getText();

        String username = userText.toString();
        String password = passText.toString();
        String name = nameText.toString();
        String userType = (String) userTypeView.getSelectedItem();
        boolean userExists = userExists(username, userType);
        if (!userExists) {
            addUser(username, password, name, userType);
            Toast success = Toast.makeText(getApplicationContext(),
                    "Successfully registered! \n Press back to Login", Toast.LENGTH_SHORT);
            success.setGravity(Gravity.TOP,0,300);
            View v = success.getView();
            v.setBackgroundColor(Color.parseColor("#55A8D2"));
            success.show();

        } else {
            usernameView.setError("Username already in use");
            View focusView = usernameView;
            focusView.requestFocus();
        }
    }

    private boolean userExists(String user, final String userType) {
        DatabaseReference usersRef = database.child("users");
        DatabaseReference ref = usersRef.child(userType);
        final String userName = user;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    if (key.equals(userName)) {
                        userExists = true;
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            Log.d("TAG", "Database get interrupted");
        }
        if (userExists) {
            userExists = false;
            return true;
        }
        return false;
    }

    private void addUser(String user, String pass, String name, String userType) {
        DatabaseReference usersRef = database.child("users");
        DatabaseReference ref = usersRef.child(userType);
        Map<String, Object> userMap = new HashMap<>();
        Admin a;
        HomelessPerson hp;
        if ("admin".equals(userType.toLowerCase())) {
            a = new Admin(name, user, pass);
            userMap.put(user, a);
        } else {
            hp = new HomelessPerson(name,
                    user, pass);
            userMap.put(user, hp);
        }
        ref.updateChildren(userMap);

    }




}
