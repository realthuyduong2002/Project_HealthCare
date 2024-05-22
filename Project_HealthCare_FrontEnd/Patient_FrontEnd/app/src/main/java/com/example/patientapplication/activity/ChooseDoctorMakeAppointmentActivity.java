package com.example.patientapplication.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.patientapplication.R;
import com.example.patientapplication.adapters.DoctorMakeApppointmentAdapter;
import com.example.patientapplication.model.Doctor;
import com.example.patientapplication.services.DoctorService;
import com.example.patientapplication.utils.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseDoctorMakeAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_doctor_make_appointment);
        doctorList();
    }

    public void doctorList() {
        DoctorService doctorService = API.getDoctorService();
        Call<List<Doctor>> call = doctorService.getAllDoctors();

        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                if (response.isSuccessful()) {
                    List<Doctor> doctorList = response.body();
                    if (doctorList != null) {
                        setAdapter(doctorList);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void setAdapter(List<Doctor> doctorList) {
        ArrayList<Doctor> arrayList = new ArrayList<>(doctorList);
        DoctorMakeApppointmentAdapter adapter = new DoctorMakeApppointmentAdapter(this, arrayList);
        ListView listView = findViewById(R.id.lstChooseDoctor);
        listView.setAdapter(adapter);
    }

    public void onDoctorSelected(Doctor doctor) {
        Intent intent = new Intent();
        intent.putExtra("DOCTORNAME", doctor.getDoctorName());
        intent.putExtra("DOCTORID", String.valueOf(doctor.getDoctorID()));
        intent.putExtra("SPECIALTY", doctor.getSpeciality());
        setResult(RESULT_OK, intent);
        finish();
    }
}
