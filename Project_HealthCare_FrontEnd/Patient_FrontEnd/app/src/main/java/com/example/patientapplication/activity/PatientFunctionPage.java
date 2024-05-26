package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.patientapplication.R;

public class PatientFunctionPage extends AppCompatActivity {

    LinearLayout viewprofile;
    ImageView ivviewprofile, btnviewprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_function_page);

        viewprofile = findViewById(R.id.viewprofile);
        ivviewprofile = findViewById(R.id.ivviewprofile);
        btnviewprofile = findViewById(R.id.btnviewprofile);

        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientFunctionPage.this, PatientInformationActivity.class));
            }
        });
        ivviewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientFunctionPage.this, PatientInformationActivity.class));
            }
        });
        btnviewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientFunctionPage.this, PatientInformationActivity.class));
            }
        });
    }
}