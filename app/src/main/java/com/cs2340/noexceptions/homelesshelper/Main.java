package com.cs2340.noexceptions.homelesshelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toHomeScreen(View v) {
        Intent toHome = new Intent(getBaseContext(), Home.class);
        startActivity(toHome);
    }
}
