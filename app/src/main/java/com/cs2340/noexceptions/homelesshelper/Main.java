package com.cs2340.noexceptions.homelesshelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Map;


public class Main extends AppCompatActivity {
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] userInfo = LoginActivity.currentUser;
        if (userInfo[2].equals("Admin")) {
            user = new Admin(null, null, null);
        } else {
            user = new HomelessPerson(null, null, null);
        }
    }

    public void toHomeScreen(View v) {
        Intent toHome = new Intent(getBaseContext(), Home.class);
        startActivity(toHome);
    }
}
