package com.cs2340.noexceptions.homelesshelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class RegistrationActivity extends AppCompatActivity {
    private EditText usernameView;
    private EditText passwordView;
    private Spinner userTypeView;
    private EditText nameView;
    private static DatabaseReference database;
    private static boolean userExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registration");

        database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = database.child("users");


        nameView = (EditText) findViewById(R.id.name);
        usernameView = (EditText) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.password);
        userTypeView = (Spinner) findViewById(R.id.userType);
        userTypeView.setPrompt("User Type");












        ArrayAdapter<String> adapterClass = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, new String[]{"Admin", "User"});
        adapterClass.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeView.setAdapter(adapterClass);

        Button registrationButton = (Button) findViewById(R.id.registrationButton);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toHome = new Intent(getBaseContext(), Home.class);
                startActivity(toHome);
            }

        });

    }

    private void attemptRegistration() {
        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();
        String name = nameView.getText().toString();
        String userType = (String) userTypeView.getSelectedItem();
        boolean userExists = userExists(username, userType);
        if (!userExists) {
            addUser(username, password, name, userType);
            Toast success = Toast.makeText(getApplicationContext(), "Successfully registered! \n Press back to Login", Toast.LENGTH_SHORT);
            success.setGravity(Gravity.TOP,0,300);
            success.getView().setBackgroundColor(Color.parseColor("#55A8D2"));
            success.show();

        } else {
            usernameView.setError("Username already in use");
            View focusView = usernameView;
            focusView.requestFocus();
        }
    }

    protected static boolean userExists(String user, final String userType) {
        DatabaseReference ref = database.child("users").child(userType);
        final String userName = user;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(userName)) {
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
        DatabaseReference ref = database.child("users").child(userType);
        Map<String, Object> userMap = new HashMap<>();
        Admin a;
        HomelessPerson hp;
        if (userType.toLowerCase().equals("admin")) {
            a = new Admin(name, true, "", user, pass);
            userMap.put(user, a);
        } else {
            hp = new HomelessPerson(name, true, "", user, pass, 0, "", false);
            userMap.put(user, hp);
        }
        ref.updateChildren(userMap);

    }




}
