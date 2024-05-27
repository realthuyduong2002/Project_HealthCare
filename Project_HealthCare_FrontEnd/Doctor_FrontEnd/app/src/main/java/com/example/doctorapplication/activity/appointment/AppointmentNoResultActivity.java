package com.example.doctorapplication.activity.appointment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.example.doctorapplication.R;
import com.example.doctorapplication.model.Appointment;
import com.example.doctorapplication.utils.API;
import com.example.doctorapplication.utils.base.BaseActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentNoResultActivity extends BaseActivity {

    LinearLayout lnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_no_result);


        lnBack = findViewById(R.id.lnBack);
        lnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}