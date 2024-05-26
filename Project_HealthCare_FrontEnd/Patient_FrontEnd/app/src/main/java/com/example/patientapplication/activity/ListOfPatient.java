package com.example.patientapplication.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayAdapter<Patient> adapter;
    ListView lsPaitent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_patient);
        lsPaitent = findViewById(R.id.lsPaitent);

        adapter = new ListOfPatientAdapter(this, patients);
        lsPaitent.setAdapter(adapter);

        loadPatient();

    }

    private void loadPatient() {
        PatientService patientService = API.getPatientService();
        Call<List<Patient>> call = patientService.getPatients();
        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if(response.isSuccessful() && response.body() != null)
                {
                    patients.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(ListOfPatient.this, "Failed to fetch patients: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {

            }
        });
    }
}