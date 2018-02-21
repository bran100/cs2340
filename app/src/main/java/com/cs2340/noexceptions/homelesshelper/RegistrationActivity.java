package com.cs2340.noexceptions.homelesshelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private EditText usernameView;
    private EditText passwordView;
    private Spinner userTypeView;
    private EditText nameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        toolbar.setTitle("Registration");


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
        boolean userExists = userExists(username);
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

    protected static boolean userExists(String user) {
        Map<String, String[]> userMap = Home.getUserInfo();
        if (userMap.get(user) == null) {
            return false;
        }
        return true;
    }

    private void addUser(String user, String pass, String name, String userType) {
        Map<String, String[]> userMap = Home.getUserInfo();
        userMap.put(user, new String[] {pass, name, userType});

    }




}
