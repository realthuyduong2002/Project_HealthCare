package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.patientapplication.R;

public class ChoooseSpecialtyMakeAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooose_specialty_make_appointment);

        Button buttonBone = findViewById(R.id.buttonBone);
        Button buttonHeart = findViewById(R.id.buttonHeart);

        buttonBone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDoctorsBySpecialty("Bone");
            }
        });

        buttonHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDoctorsBySpecialty("Heart");
            }
        });
    }

    private void fetchDoctorsBySpecialty(String specialty) {
        Intent intent = new Intent(ChoooseSpecialtyMakeAppointmentActivity.this, DisplayDoctorsActivity.class);
        intent.putExtra("SPECIALTY", specialty);
        Log.d("SPECIALTY",specialty);
        startActivity(intent);
    }
}
