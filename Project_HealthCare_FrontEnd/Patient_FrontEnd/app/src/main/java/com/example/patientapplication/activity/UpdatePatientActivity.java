package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.patientapplication.R;
import com.example.patientapplication.model.Patient;
import com.example.patientapplication.services.PatientService;
import com.example.patientapplication.utils.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePatientActivity extends AppCompatActivity {

    EditText edtPatientName, edtPhoneNumber, edtDateOfBirth, edtEmail, edtCCCD, edtCity, edtDistrict, edtWard;
    RadioButton rdMale, rdFemale;
    Button btnCreateProfile, btnDeletePatient;
    int PatientID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient);

        PatientID =  Integer.parseInt(getIntent().getStringExtra("PATIENTID"));
        Log.d("PATIENTID", String.valueOf(PatientID));

        // Initialize EditText, RadioButton, and Button
        edtPatientName = findViewById(R.id.edtPatientName);
        edtPhoneNumber = findViewById(R.id.edtPhonenumber);
        edtDateOfBirth = findViewById(R.id.edtDateOfBirth);
        edtEmail = findViewById(R.id.edtEmail);
        edtCCCD = findViewById(R.id.edtCCCD);
        edtCity = findViewById(R.id.edtCity);
        edtDistrict = findViewById(R.id.edtDistrict);
        edtWard = findViewById(R.id.edtWard);
        rdMale = findViewById(R.id.rdMale);
        rdFemale = findViewById(R.id.rdFemale);
        btnCreateProfile = findViewById(R.id.btnCreateProfile);
        btnDeletePatient = findViewById(R.id.btnDeletePatient);


        loadPatientInformation(PatientID);

        // Set onClickListener for the create profile button
        btnCreateProfile.setOnClickListener(v -> {
            // Get data from EditText fields
            String patientName = edtPatientName.getText().toString();
            String phoneNumber = edtPhoneNumber.getText().toString();
            String dateOfBirth = edtDateOfBirth.getText().toString();
            String email = edtEmail.getText().toString();
            String cccd = edtCCCD.getText().toString();
            String city = edtCity.getText().toString();
            String district = edtDistrict.getText().toString();
            String ward = edtWard.getText().toString();
            String gender = rdMale.isChecked() ? "Male" : "Female";

            Patient patient = new Patient(patientName, phoneNumber, email, city, district, ward, gender, dateOfBirth, cccd);

            // Call the update patient information function
            updatePatientInformation(patient);
        });

        // Set onClickListener for the delete patient button
        btnDeletePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProfile();
            }
        });
    }

    private void loadPatientInformation(int patientID) {
        PatientService patientService = API.getPatientService();
        patientService.GetPatientDetail(patientID).enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.isSuccessful()) {
                    Patient patient = response.body();
                    if (patient != null) {
                        // Set the loaded patient information to the EditText fields
                        edtPatientName.setText(patient.getPatientName());
                        edtPhoneNumber.setText(patient.getPhone());
                        edtDateOfBirth.setText(patient.getDateOfBirth());
                        edtEmail.setText(patient.getEmail());
                        edtCCCD.setText(patient.getCitizenIdentification());
                        edtCity.setText(patient.getCity());
                        edtDistrict.setText(patient.getDistrict());
                        edtWard.setText(patient.getWard());

                        // Set gender RadioButton based on patient's gender
                        if ("Male".equals(patient.getGender())) {
                            rdMale.setChecked(true);
                        } else if ("Female".equals(patient.getGender())) {
                            rdFemale.setChecked(true);
                        }
                    }
                } else {
                    Toast.makeText(UpdatePatientActivity.this, "Failed to load patient information: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(UpdatePatientActivity.this, "Failed to load patient information: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    private void updatePatientInformation(Patient patient) {
        PatientService patientService = API.getPatientService();
        int patientID = PatientID;
        Call<Patient> call = patientService.UpdatePatientInformation(patientID, patient);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdatePatientActivity.this, "Patient information updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdatePatientActivity.this, HomepageActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UpdatePatientActivity.this, "Failed to update patient information: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(UpdatePatientActivity.this, "Failed to update patient information: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteProfile() {
        PatientService patientService = API.getPatientService();
        Integer PatientID = getIntent().getIntExtra("PatientID", 1);
        Call<Patient> call = patientService.DeletePatientProfile(PatientID);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdatePatientActivity.this, "Patient delete successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdatePatientActivity.this, HomepageActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
}
