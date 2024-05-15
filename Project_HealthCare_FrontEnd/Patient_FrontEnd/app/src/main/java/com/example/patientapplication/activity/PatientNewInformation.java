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

public class PatientNewInformation extends AppCompatActivity {

    EditText edtPatientName, edtPhonenumber, edtDateOfBirth,
            edtEmail, edtCCCD, edtCity, edtDistrict, edtWard;
    Button btnCreateProfile;

    RadioButton rdMale, rdFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_new_information);

        edtPatientName = findViewById(R.id.edtPatientName);
        edtPhonenumber = findViewById(R.id.edtPhonenumber);
        edtDateOfBirth = findViewById(R.id.edtDateOfBirth);
        edtEmail = findViewById(R.id.edtEmail);
        edtCCCD = findViewById(R.id.edtCCCD);
        edtCity = findViewById(R.id.edtCity);
        edtDistrict = findViewById(R.id.edtDistrict);
        edtWard = findViewById(R.id.edtWard);

        btnCreateProfile = findViewById(R.id.btnCreateProfile);

        rdMale = findViewById(R.id.rdMale);
        rdFemale = findViewById(R.id.rdFemale);

        btnCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String PatientName = edtPatientName.getText().toString();
                String PhoneNumber = edtPhonenumber.getText().toString();
                String DateOfBirth = edtDateOfBirth.getText().toString();
                String Email = edtEmail.getText().toString();
                String CCCD = edtCCCD.getText().toString();
                String City = edtCity.getText().toString();
                String District = edtDistrict.getText().toString();
                String Ward = edtWard.getText().toString();

                String Gender = rdMale.isChecked() ? "Male" : "Female";

                Patient patient = new Patient(PatientName, PhoneNumber, Email, City, District, Ward, Gender, DateOfBirth, CCCD);

                addNewProfile(patient);
            }
        });
    }

    private void addNewProfile(Patient patient) {
        PatientService patientService = API.getPatientService();
        Call<Patient> call = patientService.NewProfile(patient);
        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PatientNewInformation.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PatientNewInformation.this, PatientInformationActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(PatientNewInformation.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
