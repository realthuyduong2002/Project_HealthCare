package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.patientapplication.R;
import com.example.patientapplication.adapters.PatientInformationAdapter;
import com.example.patientapplication.model.Appointment;
import com.example.patientapplication.model.Patient;
import com.example.patientapplication.services.AppointmentService;
import com.example.patientapplication.services.PatientService;
import com.example.patientapplication.utils.API;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_DOCTOR_SELECTION = 1;
    private static final int REQUEST_CODE_SPECIALTY_SELECTION = 2;
    String PatientID = "";
    String Time = "";
    String Date = "";
    String PatientName = "";
    String DoctorID = "";
    String Speacialty = "";
    String Sympton = "";
    private AutoCompleteTextView autoCompleteTextView, atcTime;
    private TextView tvDate;
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayAdapter<Patient> adapter;
    private ArrayAdapter<String> adapteritem;
    private Button btnChooseDoctor, btnFinish;
    private EditText symptom;
    private String[] time = {"07:00:00 - 08:00:00", "08:00:00 - 09:00:00", "09:00:00 - 10:00:00"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        atcTime = findViewById(R.id.atcTime);
        tvDate = findViewById(R.id.tvDate);
        btnChooseDoctor = findViewById(R.id.btnChooseDoctor);
        btnFinish = findViewById(R.id.btnFinish);
        symptom = findViewById(R.id.sympton);

        adapter = new PatientInformationAdapter(this, patients);
        autoCompleteTextView.setAdapter(adapter);
        adapteritem = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, time);
        atcTime.setAdapter(adapteritem);


        loadPatients();

        autoCompleteTextView.setOnItemClickListener((adapterView, view, position, l) -> {
            Patient patient = adapter.getItem(position);
            if (patient != null) {
                Toast.makeText(AppointmentActivity.this, "Patient ID: " + patient.getPatientID() + ", Patient Name: " + patient.getPatientName(), Toast.LENGTH_SHORT).show();
                PatientID = String.valueOf(patient.getPatientID());
                PatientName = patient.getPatientName();
            }
        });

        atcTime.setOnItemClickListener((adapterView, view, position, id) -> {
            String item = adapterView.getItemAtPosition(position).toString();
            Toast.makeText(AppointmentActivity.this, item, Toast.LENGTH_SHORT).show();
            Time = item;
        });

        tvDate.setOnClickListener(v -> showDatePicker());

        btnChooseDoctor.setOnClickListener(v -> {
            Intent intent = new Intent(AppointmentActivity.this, ChooseDoctorMakeAppointmentActivity.class);
            startActivityForResult(intent, REQUEST_CODE_DOCTOR_SELECTION);
        });


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sympton = symptom.getText().toString();
                Appointment appointment = new Appointment(Integer.parseInt(PatientID), Date, Time, DoctorID, Speacialty, Sympton, "Pending");
                Log.d("AppointmentActivity", "PatientID: " + PatientID);
                Log.d("AppointmentActivity", "PatientName: " + PatientName);
                Log.d("AppointmentActivity", "Date: " + Date);
                Log.d("AppointmentActivity", "Time: " + Time);
                Log.d("AppointmentActivity", "DoctorID: " + DoctorID);
                Log.d("AppointmentActivity", "Specialty: " + Speacialty);
                Log.d("AppointmentActivity", "Symptom: " + Sympton);
                createNewAppointment(appointment);
            }
        });
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
                } else {
                    Toast.makeText(AppointmentActivity.this, "Failed to fetch patients: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                Toast.makeText(AppointmentActivity.this, "Failed to fetch patients", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showDatePicker() {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now());

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        builder.setCalendarConstraints(constraintsBuilder.build());

        final MaterialDatePicker<Long> datePicker = builder.build();
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            Date = sdf.format(selection);
            tvDate.setText(Date);
        });
    }

    private void createNewAppointment(Appointment appointment) {
        AppointmentService appointmentService = API.getAppointmentService();
        Call<Appointment> call = appointmentService.addNewAppointment(appointment);
        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Appointment createdAppointment = response.body();
                    Toast.makeText(AppointmentActivity.this, "Create appointment successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AppointmentActivity.this, Checkout.class);
                    intent.putExtra("PATIENTID", String.valueOf(PatientID));
                    intent.putExtra("PATIENTNAME", PatientName);
                    intent.putExtra("APPOINTMENTDAY", Date);
                    intent.putExtra("APPOINTMENTTIME", Time);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Appointment> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(AppointmentActivity.this, "Create unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ActivityResult", "requestCode: " + requestCode + ", resultCode: " + resultCode + ", data: " + data);
        if (requestCode == REQUEST_CODE_DOCTOR_SELECTION && resultCode == RESULT_OK && data != null) {
            String specialty = data.getStringExtra("SPECIALTY");
            String doctorId = data.getStringExtra("DOCTORID");
            Toast.makeText(this, "Selected Doctor ID: " + doctorId + ", Specialty: " + specialty, Toast.LENGTH_SHORT).show();
            DoctorID = doctorId;
            Speacialty = specialty;
        }
    }
}
