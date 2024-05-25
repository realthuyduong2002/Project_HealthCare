package com.example.doctorapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorapplication.R;
import com.example.doctorapplication.model.Patient;
import com.example.doctorapplication.services.PatientService;
import com.example.doctorapplication.apiClient.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePatientActivity extends AppCompatActivity {
    EditText etName, etDob, etPhone, etEmail, etCity, etWard, etDistrict, etCitizenIdentification;
    RadioButton maleRadioButton, femaleRadioButton;
    Button btnUpdate;
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient);

        etName = findViewById(R.id.editName);
        etDob = findViewById(R.id.editDateOfBirth);
        maleRadioButton = findViewById(R.id.maleRadioButton);
        femaleRadioButton = findViewById(R.id.femaleRadioButton);
        etPhone = findViewById(R.id.editPhone);
        etEmail = findViewById(R.id.editEmail);
        etCity = findViewById(R.id.editCity);
        etWard = findViewById(R.id.editWard);
        etDistrict = findViewById(R.id.editDistrict);
        etCitizenIdentification = findViewById(R.id.editCitizenship);
        btnUpdate = findViewById(R.id.updateButton);

        patient = getIntent().getParcelableExtra("selectedPatient");
        if (patient != null) {
            etName.setText(patient.getPatientName());
            etDob.setText(patient.getDateOfBirth());
            if (patient.getGender().equalsIgnoreCase("Male")) {
                maleRadioButton.setChecked(true);
            } else if (patient.getGender().equalsIgnoreCase("Female")) {
                femaleRadioButton.setChecked(true);
            }
            etPhone.setText(patient.getPhone());
            etEmail.setText(patient.getEmail());
            etCity.setText(patient.getCity());
            etWard.setText(patient.getWard());
            etDistrict.setText(patient.getDistrict());
            etCitizenIdentification.setText(patient.getCitizenIdentification());
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String dob = etDob.getText().toString();
                String gender = maleRadioButton.isChecked() ? "Male" : "Female";
                String phone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                String city = etCity.getText().toString();
                String ward = etWard.getText().toString();
                String district = etDistrict.getText().toString();
                String citizenIdentification = etCitizenIdentification.getText().toString();

                Patient updatedPatient = new Patient(
                        patient.getId(),
                        name,
                        phone,
                        email,
                        city,
                        district,
                        ward,
                        gender,
                        dob,
                        citizenIdentification
                );
                updatePatientDetails(updatedPatient);
            }
        });
    }

    private void updatePatientDetails(Patient updatedPatient) {
        String apiUrl = "http://192.168.1.4:8080/api/";
        PatientService patientService = RetrofitClient.getClient(apiUrl).create(PatientService.class);
        Call<Patient> call = patientService.updatePatient(updatedPatient.getId(), updatedPatient);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.isSuccessful()) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updatedPatient", response.body());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    // Display appropriate error message based on the response
                    Toast.makeText(UpdatePatientActivity.this, "Failed to update patient. Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(UpdatePatientActivity.this, "Failed to update patient: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}