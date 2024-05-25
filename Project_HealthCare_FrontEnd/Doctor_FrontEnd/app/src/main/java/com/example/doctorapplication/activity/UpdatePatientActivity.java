package com.example.doctorapplication.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.doctorapplication.R;
import com.example.doctorapplication.model.Patient;
import com.example.doctorapplication.services.PatientService;
import com.example.doctorapplication.utils.API;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePatientActivity extends AppCompatActivity {

    EditText etName, etDob, etGender, etPhone, etEmail, etCity, etWard, etDistrict, etCitizenIdentification;
    Button btnUpdate;
    Patient patient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient);

        etName = findViewById(R.id.editName);
        etDob = findViewById(R.id.editDateOfBirth);
        etGender = findViewById(R.id.editGender);
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
            etGender.setText(patient.getGender());
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
                String gender = etGender.getText().toString();
                String phone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                String city = etCity.getText().toString();
                String ward = etWard.getText().toString();
                String district = etDistrict.getText().toString();
                String citizenIdentification = etCitizenIdentification.getText().toString();

                Patient updatedPatient = new Patient(
                        patient.getId(),
                        name,
                        dob,
                        gender,
                        phone,
                        email,
                        city,
                        ward,
                        district,
                        citizenIdentification
                );

                updatePatientDetails(updatedPatient);
            }
        });
    }

    private void updatePatientDetails(Patient updatedPatient) {
        String apiUrl = "http://192.168.1.4:8080/api/doctor/patient/updatePatient/" + updatedPatient.getId();
        PatientService patientService = API.getPatientService(apiUrl);
        Call<Patient> call = patientService.updatePatientById(updatedPatient.getId(), updatedPatient);

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdatePatientActivity.this, "Patient updated successfully", Toast.LENGTH_SHORT).show();
                    // Gửi kết quả trở lại cho PatientDetail và kết thúc hoạt động hiện tại
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("updatedPatient", updatedPatient);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(UpdatePatientActivity.this, "Failed to update patient", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(UpdatePatientActivity.this, "Failed to update patient: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
