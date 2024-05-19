package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patientapplication.R;
import com.example.patientapplication.adapters.CalendarAdapter;
import com.example.patientapplication.adapters.PatientInformationAdapter;
import com.example.patientapplication.model.Patient;
import com.example.patientapplication.services.PatientService;
import com.example.patientapplication.utils.API;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    AutoCompleteTextView autoCompleteTextView, atcTime;
    ArrayList<Patient> patients = new ArrayList<>();
    ArrayAdapter<Patient> adapter;
    ArrayAdapter<String> adapteritem;
    private TextView monthYearTv;
    private RecyclerView calendarRec;
    private LocalDate selectedDate;
    Button btnChooseDoctor;

    String[] time = {"07:00:00 - 08:00:00", "08:00:00 - 09:00:00", "09:00:00 - 10:00:00"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        atcTime = findViewById(R.id.atcTime);
        monthYearTv = findViewById(R.id.monthYearTv);
        calendarRec = findViewById(R.id.rclCalendar);
        btnChooseDoctor = findViewById(R.id.btnChooseDoctor);

        adapter = new PatientInformationAdapter(this, patients);
        autoCompleteTextView.setAdapter(adapter);
        adapteritem = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, time);
        atcTime.setAdapter(adapteritem);

        patientList();

        autoCompleteTextView.setOnItemClickListener((adapterView, view, position, l) -> {
            Patient patient = adapter.getItem(position);
            if (patient != null) {
                Toast.makeText(AppointmentActivity.this, "Patient ID: " + patient.getPatientID() + ", Patient Name: " + patient.getPatientName(), Toast.LENGTH_SHORT).show();
            }
        });

        atcTime.setOnItemClickListener((adapterView, view, position, id) -> {
            String item = adapterView.getItemAtPosition(position).toString();
            Toast.makeText(AppointmentActivity.this, "Selected time: " + item, Toast.LENGTH_SHORT).show();
        });

        selectedDate = LocalDate.now();
        setMonthView();

        btnChooseDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentActivity.this, ChooseDoctorMakeAppointmentActivity.class);
                startActivity(intent);
            }
        });
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
                        patients.addAll(patientList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                Toast.makeText(AppointmentActivity.this, "Failed to fetch patients", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setMonthView() {
        monthYearTv.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRec.setLayoutManager(layoutManager);
        calendarRec.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previous(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void next(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
