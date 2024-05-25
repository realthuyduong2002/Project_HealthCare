package com.example.doctorapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorapplication.R;

public class AppointmentActivity extends AppCompatActivity {

    LinearLayout lnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        //LinearLayout
        lnBack = (LinearLayout) findViewById(R.id.lnBack);
        lnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        //Intent manage patient

    }
}
