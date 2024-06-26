package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.patientapplication.R;

public class PatientFunctionPage extends AppCompatActivity {

    LinearLayout viewprofile;
    TextView tvHome, tvAppointment;
    ImageView ivviewprofile, btnviewprofile, ivHome,ivAppointment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_function_page);

        ivHome= findViewById(R.id.ivHome);
        viewprofile = findViewById(R.id.viewprofile);
        ivviewprofile = findViewById(R.id.ivviewprofile);
        btnviewprofile = findViewById(R.id.btnviewprofile);
        ivAppointment = findViewById(R.id.ivAppointment);
        tvAppointment = findViewById(R.id.tvAppointment);
        tvAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientFunctionPage.this, AppointmentActivity.class);
                startActivity(intent);
            }
        });
        ivAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientFunctionPage.this, AppointmentActivity.class);
                startActivity(intent);
            }
        });
        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientFunctionPage.this, ListOfPatient.class));
            }
        });
        ivviewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientFunctionPage.this, ListOfPatient.class));
            }
        });
        btnviewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientFunctionPage.this, ListOfPatient.class));
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientFunctionPage.this, HomepageActivity.class));
            }
        });
    }
}