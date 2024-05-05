package com.example.healthcare.activity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;
import com.example.healthcare.adapters.DoctorManagePatientAdapter;
import com.example.healthcare.model.Patient;
import com.example.healthcare.services.PatientService;
import com.example.healthcare.utils.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorManagePatientList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_manage_patient_list);
        patientList();
    }

    public void patientList() {
        // Retrieve list of patients using PatientService
        PatientService patientService = API.getPatientService();
        Call<List<Patient>> call = patientService.getPatients();

        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if (response.isSuccessful()) {
                    List<Patient> patientList = response.body();
                    if (patientList != null) {
                        setAdapter(patientList);
                    }
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void setAdapter(List<Patient> patientList) {
        // Convert List<Patient> to ArrayList<Patient>
        ArrayList<Patient> arrayList = new ArrayList<>(patientList);

        DoctorManagePatientAdapter adapter = new DoctorManagePatientAdapter(this, arrayList);
        ListView listView = findViewById(R.id.listofPatients);
        listView.setAdapter(adapter);
    }
}
