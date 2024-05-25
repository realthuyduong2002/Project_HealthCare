package com.example.doctorapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorapplication.R;

public class DoctorPersonalInfoActivity extends AppCompatActivity {

    LinearLayout lnPersonalInformation;
    LinearLayout lnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_personal_info);

        //LinearLayout
        lnPersonalInformation = (LinearLayout) findViewById(R.id.lnPersonalInformation);
        lnBack =  (LinearLayout)findViewById(R.id.lnBack);
        lnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //Intent manage patient
        lnPersonalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorPersonalInfoActivity.this, DoctorDetailInfoActivity.class);
                startActivity(intent);
            }
        });

    }
}