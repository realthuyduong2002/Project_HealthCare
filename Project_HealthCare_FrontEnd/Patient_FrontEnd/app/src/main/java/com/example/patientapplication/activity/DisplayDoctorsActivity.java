// DisplayDoctorsActivity.java

package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patientapplication.R;
import com.example.patientapplication.adapters.DoctorAdapter;
import com.example.patientapplication.model.Doctor;
import com.example.patientapplication.services.DoctorService;
import com.example.patientapplication.utils.API;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayDoctorsActivity extends AppCompatActivity implements DoctorAdapter.OnDoctorClickListener {

    private RecyclerView recyclerView;
    private DoctorAdapter adapter;
    private String specialty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_doctors);

        recyclerView = findViewById(R.id.recyclerViewDoctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        specialty = getIntent().getStringExtra("SPECIALTY");
        Log.d("SPECIALTY", specialty);
        fetchDoctors();
    }

    private void fetchDoctors() {
        DoctorService doctorService = API.getDoctorService();
        Call<List<Doctor>> call = doctorService.findBySpecialty(specialty);
        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                if (response.isSuccessful()) {
                    List<Doctor> doctorList = response.body();
                    if (doctorList != null) {
                        adapter = new DoctorAdapter(doctorList, DisplayDoctorsActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(DisplayDoctorsActivity.this, "Failed to fetch doctors: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                try {
                    throw t;
                } catch (IOException e) {
                    Log.e("FetchDoctors", "Network error: " + e.getMessage());
                } catch (Throwable e) {
                    Log.e("FetchDoctors", "Unexpected error: " + e.getMessage());
                }
                Toast.makeText(DisplayDoctorsActivity.this, "Failed to fetch doctors", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDoctorClick(Doctor doctor) {
        Intent intent = new Intent(DisplayDoctorsActivity.this, AppointmentActivity.class);
        intent.putExtra("SPECIALTY", doctor.getSpeciality());
        intent.putExtra("DOCTORID", doctor.getDoctorID());
        intent.putExtra("DOCTORNAME", doctor.getDoctorName());
        Log.d("SPECIALTY", doctor.getSpeciality());
        Log.d("DOCTORID", String.valueOf(doctor.getDoctorID()));
        setResult(DisplayDoctorsActivity.RESULT_OK, intent);
        startActivity(intent);
    }
}
