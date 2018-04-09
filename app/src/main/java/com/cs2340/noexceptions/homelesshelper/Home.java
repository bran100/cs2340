package com.cs2340.noexceptions.homelesshelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;


/**
 * The home screen activity
 */
public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    /**
     * Transitions the app from the Home activity to the login screen
     * @param v A view
     */
    public void toLoginScreen(View v) {
        Intent toLogin = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(toLogin);
    }

    /**
     * Transitions the app from the home screen to the registration screen
     * @param v A view
     */
    public void toRegistrationScreen(View v) {
        Intent toRegistration = new Intent(getBaseContext(), RegistrationActivity.class);
        startActivity(toRegistration);
    }
}
