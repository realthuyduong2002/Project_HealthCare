package com.example.doctorapplication.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorapplication.R;
import com.example.doctorapplication.model.Patient;
import com.example.doctorapplication.services.PatientService;
import com.example.doctorapplication.utils.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientDetail extends AppCompatActivity {
    private static final int UPDATE_PATIENT_REQUEST_CODE = 1;
    TextView patientIdTextView, patientNameTextView, dateOfBirthTextView, genderTextView, phoneNumberTextView, emailTextView, citizenshipTextView, cityTextView, wardTextView, districtTextView;
    Patient patient;
    ImageView optionsMenu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        optionsMenu = findViewById(R.id.optionsMenu);
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        optionsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        // Mapping TextViews
        patientNameTextView = findViewById(R.id.PatientName);
        dateOfBirthTextView = findViewById(R.id.DateOfBirth);
        genderTextView = findViewById(R.id.Gender);
        phoneNumberTextView = findViewById(R.id.PhoneNumber);
        emailTextView = findViewById(R.id.Email);
        citizenshipTextView = findViewById(R.id.Citizenship);
        cityTextView = findViewById(R.id.City);
        wardTextView = findViewById(R.id.Ward);
        districtTextView = findViewById(R.id.District);

        // Retrieve the patient object from Intent
        int patientId = getIntent().getIntExtra("selectedPatientId", -1);
        if (patientId != -1) {
            fetchPatientDetail(patientId); // Call method to get patient details based on ID
        } else {
            Log.e("PatientDetail", "Invalid patient ID");
            Toast.makeText(this, "Invalid patient ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.update_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_update) {
                    // Switch to InsertPatientActivity and wait for the results to return
                    Intent intent = new Intent(PatientDetail.this, UpdatePatientActivity.class);
                    intent.putExtra("selectedPatient", patient);  // Pass the patient object to UpdatePatientActivity
                    startActivityForResult(intent, UPDATE_PATIENT_REQUEST_CODE);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void displayPatientDetails(Patient patient) {
        patientNameTextView.setText(patient.getPatientName());
        dateOfBirthTextView.setText(patient.getDateOfBirth());
        genderTextView.setText(patient.getGender());
        phoneNumberTextView.setText(patient.getPhone());
        emailTextView.setText(patient.getEmail());
        citizenshipTextView.setText(patient.getCitizenIdentification());
        cityTextView.setText(patient.getCity());
        wardTextView.setText(patient.getWard());
        districtTextView.setText(patient.getDistrict());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_PATIENT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                // Get updated patient information from UpdatePatientActivity
                Patient updatedPatient = data.getParcelableExtra("updatedPatient");
                // Update displays new patient information
                if (updatedPatient != null) {
                    patient = updatedPatient;
                    displayPatientDetails(updatedPatient);
                }
            }
        }
    }

    private void fetchPatientDetail(int patientId) {
        String apiUrl = "http://192.168.1.4:8080/api/doctor/patient/" + patientId;
        PatientService patientService = API.getPatientService(apiUrl);
        patientService.getPatientByID(patientId).enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.isSuccessful()) {
                    patient = response.body();
                    if (patient != null) {
                        displayPatientDetails(patient);
                    } else {
                        Toast.makeText(PatientDetail.this, "Patient not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PatientDetail.this, "Failed to fetch patient details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(PatientDetail.this, "Failed to fetch patient details: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}