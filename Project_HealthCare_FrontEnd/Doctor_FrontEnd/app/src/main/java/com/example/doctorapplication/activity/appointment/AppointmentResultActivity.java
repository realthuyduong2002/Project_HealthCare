package com.example.doctorapplication.activity.appointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorapplication.R;
import com.example.doctorapplication.adapters.AppointmentAdapter;
import com.example.doctorapplication.model.Appointment;
import com.example.doctorapplication.model.AppointmentResult;
import com.example.doctorapplication.utils.AppUtils;
import com.example.doctorapplication.utils.base.BaseActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AppointmentResultActivity extends BaseActivity {
    LinearLayout lnBack;
    TextView tvAppointmentDate;
    RecyclerView rvAppointHour;
    AppointmentAdapter adapter;
    List<Appointment> appointments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_result);

        lnBack = findViewById(R.id.lnBack);
        tvAppointmentDate = findViewById(R.id.tvAppointmentDate);
        rvAppointHour = findViewById(R.id.rvAppointHour);

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
            appointments = null;
        } else {
            AppointmentResult appointmentResult = new Gson().fromJson(data, AppointmentResult.class);
            appointments = appointmentResult.getAppointments();
            tvAppointmentDate.setText(appointmentResult.getAppointDate());
        }
    }

    private void initView() {
        setUpAdapter();
    }

    private void setUpAdapter() {
        adapter = new AppointmentAdapter(AppointmentResultActivity.this, appointments, new AppointmentAdapter.OnClick() {
            @Override
            public void onClickItem(Appointment appointment) {
                Intent intent = new Intent(AppointmentResultActivity.this, AppointmentDetailActivity.class);
                intent.putExtra(JSON_DATA, AppUtils.gson().toJson(appointment));
                startActivity(intent);
            }
        });
        rvAppointHour.setAdapter(adapter);
    }
}