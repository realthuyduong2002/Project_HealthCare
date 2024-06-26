package com.example.doctorapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorapplication.R;
import com.example.doctorapplication.adapters.DoctorManagePatientAdapter;
import com.example.doctorapplication.model.Patient;
import com.example.doctorapplication.services.PatientService;
import com.example.doctorapplication.utils.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorManagePatientList extends AppCompatActivity {
    private static final int INSERT_PATIENT_REQUEST_CODE = 1;
    ImageView optionsMenu;
    ListView listView;
    DoctorManagePatientAdapter adapter;
    List<Patient> patientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_manage_patient_list);

        optionsMenu = findViewById(R.id.optionsMenu);
        listView = findViewById(R.id.listofPatients);
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        optionsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        patientList();

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            if (adapter != null) {
                Patient selectedPatient = adapter.getItem(position);
                if (selectedPatient != null) {
                    Intent intent = new Intent(DoctorManagePatientList.this, PatientDetail.class);
                    intent.putExtra("selectedPatientId", selectedPatient.getId());
                    startActivity(intent);
                } else {
                    Log.e("DoctorManagePatientList", "selectedPatient is null");
                }
            } else {
                Log.e("DoctorManagePatientList", "adapter is null");
            }
        });
    }

    private void patientList() {
        String apiUrl = "http://192.168.1.4:8080/api/doctor/patients/";
        PatientService patientService = API.getPatientService(apiUrl);
        Call<List<Patient>> call = patientService.getPatients();

        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if (response.isSuccessful()) {
                    List<Patient> patientList = response.body();
                    if (patientList != null) {
                        setAdapter(patientList);
                    } else {
                        Log.e("DoctorManagePatientList", "Patient list is null");
                    }
                } else {
                    Log.e("DoctorManagePatientList", "Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                Log.e("API_FAILURE", "API call failed", t);
            }
        });
    }

    private void setAdapter(List<Patient> patientList) {
        ArrayList<Patient> arrayList = new ArrayList<>(patientList);
        adapter = new DoctorManagePatientAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.insert_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_insert) {
                    // Switch to InsertPatientActivity and wait for the results to return
                    Intent intent = new Intent(DoctorManagePatientList.this, InsertPatientActivity.class);
                    startActivityForResult(intent, INSERT_PATIENT_REQUEST_CODE);
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INSERT_PATIENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                boolean patientAdded = data.getBooleanExtra("patientAdded", false);
                if (patientAdded) {
                    patientList();
                }
            }
        }
    }
}