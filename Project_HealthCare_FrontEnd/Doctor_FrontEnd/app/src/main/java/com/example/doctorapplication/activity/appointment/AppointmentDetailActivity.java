package com.example.doctorapplication.activity.appointment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doctorapplication.R;
import com.example.doctorapplication.model.Appointment;
import com.example.doctorapplication.utils.AppUtils;
import com.example.doctorapplication.utils.base.BaseActivity;
import com.google.gson.Gson;

public class AppointmentDetailActivity extends BaseActivity {
    LinearLayout lnBack;
    TextView tvAppointId, tvPatientId, tvPatientName, tvDoctorId, tvDoctorName, tvSpeciality, tvAppointmentDate, tvAppointmentTime, tvPaymentMethod, tvSymptom;
    Appointment appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);
        lnBack = findViewById(R.id.lnBack);
        tvAppointId = findViewById(R.id.tvAppointId);
        tvPatientId = findViewById(R.id.tvPatientId);
        tvPatientName = findViewById(R.id.tvPatientName);
        tvDoctorId = findViewById(R.id.tvDoctorId);
        tvSpeciality = findViewById(R.id.tvSpeciality);
        tvDoctorName = findViewById(R.id.tvDoctorName);
        tvAppointmentDate = findViewById(R.id.tvAppointmentDate);
        tvAppointmentTime = findViewById(R.id.tvAppointmentTime);
        tvPaymentMethod = findViewById(R.id.tvPaymentMethod);
        tvSymptom = findViewById(R.id.tvSymptom);

        lnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        String data = intent.getStringExtra(JSON_DATA);
        if (data == null || data.isEmpty()) {
            appointment = null;
        } else {
            appointment = new Gson().fromJson(data, Appointment.class);
        }

    }

    private void initView() {
        if (appointment == null) return;
        tvAppointId.setText(appointment.getAppointmentID());
        tvPatientId.setText(appointment.getPatientID());
        tvPatientName.setText(appointment.getPatientName());
        tvDoctorId.setText(appointment.getDoctorID());
        tvSpeciality.setText(appointment.getSpeciality());
        tvDoctorName.setText(appointment.getDoctorName());
        tvAppointmentDate.setText(AppUtils.formatDate(appointment.getAppointmentDate(), "yyyy-MM-dd", "MMMM DD yyyy"));
        tvAppointmentTime.setText(AppUtils.formatDate(appointment.getAppointmentTime(), "hh:mm", "hh:mm a"));
        tvPaymentMethod.setText(appointment.getPaymentMethod());
        tvSymptom.setText(appointment.getSymptom());
    }
}