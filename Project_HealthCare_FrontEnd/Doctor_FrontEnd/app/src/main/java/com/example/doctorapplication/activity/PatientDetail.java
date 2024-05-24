package com.example.doctorapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorapplication.R;
import com.example.doctorapplication.activity.UpdatePatientActivity;
import com.example.doctorapplication.model.Patient;
import com.example.doctorapplication.services.PatientService;
import com.example.doctorapplication.utils.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientDetail extends AppCompatActivity {

    TextView PatientName, DateOfBirth, Address, Gender, PhoneNumber;
    Patient patient;
    int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);

        // Ánh xạ các TextView
        PatientName = findViewById(R.id.PatientName);
        DateOfBirth = findViewById(R.id.DateOfBirth);
        Address = findViewById(R.id.Address);
        Gender = findViewById(R.id.Gender);
        PhoneNumber = findViewById(R.id.PhoneNumber);

        // Lấy thông tin bệnh nhân từ Intent
        patient = getIntent().getParcelableExtra("selectedPatient");
        if (patient != null) {
            displayPatientDetails(patient);
            patientId = patient.getId(); // Giả sử Id của bệnh nhân được truyền trong đối tượng Patient
        } else {
            patientId = getIntent().getIntExtra("PatientID", -1);
            if (patientId != -1) {
                fetchPatientDetail(patientId);
            } else {
                Toast.makeText(this, "Invalid patient ID", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Hiển thị thông tin bệnh nhân
    private void displayPatientDetails(Patient patient) {
        PatientName.setText(patient.getPatientName());
        DateOfBirth.setText(patient.getDateOfBirth());
        Address.setText(patient.getCity());
        Gender.setText(patient.getGender());
        PhoneNumber.setText(patient.getPhone());
    }

    // Lấy thông tin bệnh nhân từ API
    private void fetchPatientDetail(int patientId) {
        PatientService patientService = API.getPatientService();
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

    // Tạo menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_menu, menu);
        return true;
    }

    // Xử lý sự kiện khi chọn một item trong menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_update) {
            // Mở giao diện cập nhật thông tin bệnh nhân
            Intent intent = new Intent(PatientDetail.this, UpdatePatientActivity.class);
            intent.putExtra("selectedPatient", (Parcelable) patient);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
