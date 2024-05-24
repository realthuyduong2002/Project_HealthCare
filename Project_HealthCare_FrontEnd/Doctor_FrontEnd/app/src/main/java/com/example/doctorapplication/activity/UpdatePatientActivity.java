package com.example.doctorapplication.activity;

import android.annotation.SuppressLint;
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

    EditText editTextName, editTextDateOfBirth, editTextLocation, editTextGender, editTextPhoneNumber;
    Button buttonUpdate;
    Patient patient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient);

        // Ánh xạ các EditText và Button
        editTextName = findViewById(R.id.editTextName);
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextGender = findViewById(R.id.editTextGender);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        // Lấy thông tin bệnh nhân từ Intent
        patient = getIntent().getParcelableExtra("selectedPatient");
        if (patient != null) {
            // Hiển thị thông tin hiện tại của bệnh nhân
            displayPatientDetails(patient);
        } else {
            Toast.makeText(this, "No patient data received", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Xử lý sự kiện khi nhấn nút cập nhật
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cập nhật thông tin bệnh nhân
                updatePatient();
            }
        });
    }

    // Hiển thị thông tin bệnh nhân hiện tại trên giao diện
    private void displayPatientDetails(Patient patient) {
        editTextName.setText(patient.getPatientName());
        editTextDateOfBirth.setText(patient.getDateOfBirth());
        editTextLocation.setText(patient.getCity());
        editTextGender.setText(patient.getGender());
        editTextPhoneNumber.setText(patient.getPhone());
    }

    // Cập nhật thông tin bệnh nhân
    private void updatePatient() {
        // Lấy thông tin mới từ các EditText
        String name = editTextName.getText().toString();
        String dateOfBirth = editTextDateOfBirth.getText().toString();
        String location = editTextLocation.getText().toString();
        String gender = editTextGender.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();

        // Cập nhật thông tin bệnh nhân
        patient.setPatientName(name);
        patient.setDateOfBirth(dateOfBirth);
        patient.setCity(location);
        patient.setGender(gender);
        patient.setPhone(phoneNumber);

        // Gửi yêu cầu cập nhật thông tin bệnh nhân đến API
        PatientService patientService = API.getPatientService();
        patientService.updatePatient(patient.getId(), patient).enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdatePatientActivity.this, "Patient information updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdatePatientActivity.this, "Failed to update patient information", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(UpdatePatientActivity.this, "Failed to update patient information: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}