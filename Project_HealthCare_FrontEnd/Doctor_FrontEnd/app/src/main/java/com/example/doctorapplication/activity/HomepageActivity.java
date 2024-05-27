package com.example.doctorapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorapplication.R;
import com.example.doctorapplication.activity.appointment.AppointmentActivity;
import com.example.doctorapplication.activity.doctor.DoctorPersonalInfoActivity;

public class HomepageActivity extends AppCompatActivity {

    LinearLayout firstlayoutrow, secondlayoutrow, firstlayoutsecondrow,
            secondlayoutsecondrow, firstlayoutthirdrow, secondlayouthirdtrow;
    ImageButton imageButtonPersonalInformation,
            imageButtonPatientInformation, imagePatientHealthRecord,
            imageButtonPatientPrescriptionInformation, imageManageTherapeutic;
    Button ButtonPersonalInformation,
            ButtonPatientInformation, PatientHealthRecord,
            ButtonPatientPrescriptionInformation, ButtonManageTherapeutic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //LinearLayout
        firstlayoutrow = findViewById(R.id.firstlayoutrow);
        secondlayoutrow = findViewById(R.id.secondlayoutrow);
        firstlayoutsecondrow = findViewById(R.id.firstlayoutsecondrow);
        secondlayoutsecondrow = findViewById(R.id.secondlayoutsecondrow);
        firstlayoutthirdrow = findViewById(R.id.firstlayoutthirdrow);
        secondlayouthirdtrow = findViewById(R.id.secondlayouthirdtrow);

        //ImageButton
        imageButtonPersonalInformation = findViewById(R.id.imageButtonPersonalInformation);
        imageButtonPatientInformation = findViewById(R.id.imageButtonPatientInformation);
        imagePatientHealthRecord = findViewById(R.id.imagePatientHealthRecord);
        imageButtonPatientPrescriptionInformation = findViewById(R.id.imageButtonPatientPrescriptionInformation);
        imageManageTherapeutic = findViewById(R.id.imageManageTherapeutic);

        //Button
        ButtonPersonalInformation = findViewById(R.id.ButtonPersonalInformation);
        ButtonPatientInformation = findViewById(R.id.ButtonPatientInformation);
        PatientHealthRecord = findViewById(R.id.PatientHealthRecord);
        ButtonPatientPrescriptionInformation = findViewById(R.id.ButtonPatientPrescriptionInformation);
        ButtonManageTherapeutic = findViewById(R.id.ButtonManageTherapeutic);

        // Intent to manage personal info
        firstlayoutrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, DoctorPersonalInfoActivity.class);
                startActivity(intent);
            }
        });

        imageButtonPersonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, DoctorPersonalInfoActivity.class);
                startActivity(intent);
            }
        });

        ButtonPersonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, DoctorPersonalInfoActivity.class);
                startActivity(intent);
            }
        });

        // Intent to manage appointments
        secondlayoutrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, AppointmentActivity.class);
                startActivity(intent);
            }
        });

        // Intent to manage patient information
        imageButtonPatientInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, DoctorManagePatientList.class);
                startActivity(intent);
            }
        });

        ButtonPatientInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomepageActivity.this, DoctorManagePatientList.class);
                startActivity(intent);
            }
        });
    }
}
