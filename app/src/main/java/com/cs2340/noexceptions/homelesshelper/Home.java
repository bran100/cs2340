package com.cs2340.noexceptions.homelesshelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


/**
 * The home screen activity
 */
public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        onClicks();
    }

    private void onClicks() {
        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(toLogin);
            }
        });

        Button registrationButton = findViewById(R.id.register_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegistration = new Intent(getBaseContext(), RegistrationActivity.class);
                startActivity(toRegistration);
            }
        });


    }
}
