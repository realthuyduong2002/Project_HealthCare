package com.example.healthcare;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout firstlayoutrow, secondlayoutrow, firstlayoutsecondrow, secondlayoutsecondrow, firstlayoutthirdrow, secondlayouthirdtrow;
    ImageButton imageButtonPersonalInformation, imageAppointment, imageButtonPatientInformation, imagePatientHealthRecord, imageButtonPatientPrescriptionInformation, imageManageTherapeutic;
    Button ButtonPersonalInformation, ButtonAppointment, ButtonPatientInformation, PatientHealthRecord, ButtonPatientPrescriptionInformation, ButtonManageTherapeutic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
