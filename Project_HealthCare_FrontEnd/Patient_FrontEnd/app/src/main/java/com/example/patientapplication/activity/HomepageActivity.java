package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.patientapplication.R;

public class HomepageActivity extends AppCompatActivity {

    Button btnNewProfile, btnViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        btnViewProfile = findViewById(R.id.btnViewProfile);
        btnNewProfile = findViewById(R.id.btnNewProfile);
        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, PatientInformationActivity.class);
                startActivity(intent);
            }
        });
        btnNewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, PatientNewInformation.class);
                startActivity(intent);
            }
        });
    }
}