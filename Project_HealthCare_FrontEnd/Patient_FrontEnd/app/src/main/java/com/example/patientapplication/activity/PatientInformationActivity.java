package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.patientapplication.R;
import com.example.patientapplication.model.Patient;
import com.example.patientapplication.services.PatientService;
import com.example.patientapplication.utils.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientInformationActivity extends AppCompatActivity {
    TextView tvFullName, tvPhoneNumber, tvDateOfBirth, tvEmail, tvCCCD, tvCity, tvDistrict, tvWard;
    RadioButton rdMale, rdFemale;
    Button btnUpdatePatient, btnDeletePatient;

    Patient patient;
    String patientID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_information);

        patientID = getIntent().getStringExtra("PatientID");
        tvFullName = findViewById(R.id.tvFullName);
        tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        tvDateOfBirth = findViewById(R.id.tvDateOfBirth);
        tvEmail = findViewById(R.id.tvEmail);
        tvCCCD = findViewById(R.id.tvCCCD);
        tvCity = findViewById(R.id.tvCity);
        tvDistrict = findViewById(R.id.tvDistrict);
        tvWard = findViewById(R.id.tvWard);

        rdMale = findViewById(R.id.rdMale);
        rdFemale = findViewById(R.id.rdFemale);

        btnUpdatePatient = findViewById(R.id.btnUpdatePatient);
        btnDeletePatient = findViewById(R.id.btnDeletePatient);

        patientDetail();

        btnUpdatePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientInformationActivity.this, UpdatePatientActivity.class);
                startActivity(intent);
            }
        });

        btnDeletePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProfile();
            }
        });
    }

    private void deleteProfile() {
        PatientService patientService = API.getPatientService();
        Integer PatientID = getIntent().getIntExtra("PatientID", Integer.parseInt(patientID));
        Call<Patient> call = patientService.DeletePatientProfile(PatientID);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PatientInformationActivity.this, "Patient delete successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PatientInformationActivity.this, HomepageActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    private void patientDetail() {
        PatientService patientService = API.getPatientService();
        Integer PatientID = getIntent().getIntExtra("PatientID", Integer.parseInt(patientID));
        patientService.GetPatientDetail(PatientID).enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.isSuccessful()) {
                    patient = response.body();
                    if (patient != null) {
                        tvFullName.setText(patient.getPatientName());
                        tvPhoneNumber.setText(patient.getPhone());
                        tvDateOfBirth.setText(patient.getDateOfBirth());
                        tvEmail.setText(patient.getEmail());
                        tvCCCD.setText(patient.getCitizenIdentification());
                        tvCity.setText(patient.getCity());
                        tvDistrict.setText(patient.getDistrict());
                        tvWard.setText(patient.getWard());

                        // Set gender RadioButton based on patient's gender
                        if ("Male".equals(patient.getGender())) {
                            rdMale.setChecked(true);
                        } else if ("Female".equals(patient.getGender())) {
                            rdFemale.setChecked(true);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

            }
        });
    }

}
