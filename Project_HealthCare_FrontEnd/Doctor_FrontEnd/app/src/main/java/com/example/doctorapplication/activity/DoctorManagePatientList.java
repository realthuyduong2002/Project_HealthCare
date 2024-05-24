package com.example.doctorapplication.activity;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_manage_patient_list);

        optionsMenu = findViewById(R.id.optionsMenu);
        listView = findViewById(R.id.listofPatients);

        optionsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        patientList();
    }

    private void patientList() {
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
                    // Chuyển sang InsertPatientActivity và chờ kết quả trả về
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
