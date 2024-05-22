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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patientapplication.R;
import com.example.patientapplication.adapters.CalendarAdapter;
import com.example.patientapplication.adapters.PatientInformationAdapter;
import com.example.patientapplication.model.Appointment;
import com.example.patientapplication.model.Patient;
import com.example.patientapplication.services.AppointmentService;
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

public class AppointmentActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private static final int REQUEST_CODE_DOCTOR_SELECTION = 1;
    private static final int REQUEST_CODE_SPECIALTY_SELECTION = 2;

    private AutoCompleteTextView autoCompleteTextView, atcTime;
    private ArrayList<Patient> patients = new ArrayList<>();
    private ArrayAdapter<Patient> adapter;
    private ArrayAdapter<String> adapteritem;
    private TextView monthYearTv;
    private RecyclerView calendarRec;
    private LocalDate selectedDate;
    private Button btnChooseDoctor, btnChooseSpecialty, btnFinish;
    private EditText symptom;

    private String[] time = {"07:00:00 - 08:00:00", "08:00:00 - 09:00:00", "09:00:00 - 10:00:00"};

    String PatientID = "";
    String Time = "";
    String Date = "";

    String DoctorID = "";
    String Speacialty = "";
    String Sympton = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        atcTime = findViewById(R.id.atcTime);
        monthYearTv = findViewById(R.id.monthYearTv);
        calendarRec = findViewById(R.id.rclCalendar);
        btnChooseDoctor = findViewById(R.id.btnChooseDoctor);
        btnChooseSpecialty = findViewById(R.id.btnChooseSpecialty);
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
            }
        });

        atcTime.setOnItemClickListener((adapterView, view, position, id) -> {
            String item = adapterView.getItemAtPosition(position).toString();
            Toast.makeText(AppointmentActivity.this,  item, Toast.LENGTH_SHORT).show();
            Time = item;
        });

        selectedDate = LocalDate.now();
        setMonthView();

        btnChooseDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentActivity.this, ChooseDoctorMakeAppointmentActivity.class);
                startActivityForResult(intent, REQUEST_CODE_DOCTOR_SELECTION);
            }
        });


        btnChooseSpecialty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentActivity.this, ChoooseSpecialtyMakeAppointmentActivity.class);
                startActivity(intent);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sympton = symptom.getText().toString();
                Appointment appointment = new Appointment(Integer.parseInt(PatientID), Date, Time, Integer.parseInt(DoctorID), Speacialty, Sympton, "Pending");
                createNewAppointment(appointment);
            }
        });
    }

    private void createNewAppointment(Appointment appointment) {
        AppointmentService appointmentService = API.getAppointmentService();
        Call<Appointment> call = appointmentService.addNewAppointment(appointment);
        call.enqueue(new Callback<Appointment>() {
            @Override
            public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(AppointmentActivity.this, "Create appointment successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AppointmentActivity.this, Checkout.class);
                    intent.putExtra("PATIENTID", Integer.parseInt(PatientID));
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
            String message = dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            Date = message;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ActivityResult", "requestCode: " + requestCode + ", resultCode: " + resultCode + ", data: " + data);
        if (requestCode == REQUEST_CODE_DOCTOR_SELECTION && resultCode == RESULT_OK && data != null) {
            String specialty = data.getStringExtra("SPECIALTY");
            int doctorId = Integer.parseInt(data.getStringExtra("DOCTORID"));
            Toast.makeText(this, "Selected Doctor ID: " + doctorId + ", Specialty: " + specialty, Toast.LENGTH_SHORT).show();
            DoctorID = String.valueOf(doctorId);
            Speacialty = specialty;
        } else if (requestCode == REQUEST_CODE_SPECIALTY_SELECTION && resultCode == RESULT_OK && data != null) {
            String specialty = data.getStringExtra("SPECIALTY");
            Toast.makeText(this, "Selected Specialty: " + specialty, Toast.LENGTH_SHORT).show();
        }
    }
}
