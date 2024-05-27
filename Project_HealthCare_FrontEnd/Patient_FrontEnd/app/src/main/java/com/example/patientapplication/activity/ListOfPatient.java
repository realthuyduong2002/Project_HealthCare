package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.patientapplication.R;
import com.example.patientapplication.adapters.ListOfPatientAdapter;
import com.example.patientapplication.model.Patient;
import com.example.patientapplication.services.PatientService;
import com.example.patientapplication.utils.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfPatient extends AppCompatActivity {

    ListView lsPatient;
    Button btnCreate;
    LinearLayout bodyLayout;

    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayAdapter<Patient> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_patient);

        lsPatient = findViewById(R.id.lsPaitent);
        btnCreate = findViewById(R.id.btnCreate);
        bodyLayout = findViewById(R.id.body);

        adapter = new ListOfPatientAdapter(this, patients);
        lsPatient.setAdapter(adapter);

        loadPatients();
    }

    private void loadPatients() {
        PatientService patientService = API.getPatientService();
        Call<List<Patient>> call = patientService.getPatients();
        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    patients.addAll(response.body());
                    adapter.notifyDataSetChanged();

                    if (patients.isEmpty()) {
                        bodyLayout.setVisibility(View.VISIBLE);
                        btnCreate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ListOfPatient.this, PatientNewInformation.class);
                                startActivity(intent);
                            }
                        });
                    }
                } else {
                    Toast.makeText(ListOfPatient.this, "Failed to fetch patients: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                Toast.makeText(ListOfPatient.this, "Failed to fetch patients: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
