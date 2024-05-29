package com.example.doctorapplication.activity.doctor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doctorapplication.R;
import com.example.doctorapplication.model.Account;
import com.example.doctorapplication.model.DoctorInfo;
import com.example.doctorapplication.utils.API;
import com.example.doctorapplication.utils.base.BaseActivity;
import com.example.doctorapplication.utils.PreferenceUtils;
import com.example.doctorapplication.utils.event_bus.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorPersonalInfoActivity extends BaseActivity {
    LinearLayout lnPersonalInformation;
    LinearLayout lnBack;
    TextView tvDoctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_personal_info);
        //LinearLayout
        lnPersonalInformation = (LinearLayout) findViewById(R.id.lnPersonalInformation);
        lnBack = (LinearLayout) findViewById(R.id.lnBack);
        tvDoctorName = findViewById(R.id.tvDoctorName);
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
        doctorDetail();
    }

    public void doctorDetail() {
        loadingDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Account account = PreferenceUtils.getUserInfo();
                API.getDoctorService().getDoctorByID(account.getDoctorId()).enqueue(new Callback<DoctorInfo>() {
                    @Override
                    public void onResponse(Call<DoctorInfo> call, Response<DoctorInfo> response) {
                        dismissLoadingDialog();
                        if (response.isSuccessful()) {
                            DoctorInfo doctorInfo = response.body();
                            if (doctorInfo != null) {
                                tvDoctorName.setText(doctorInfo.getDoctorName());
                            } else {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DoctorInfo> call, Throwable t) {
                        dismissLoadingDialog();
                    }
                });
            }
        }, DELAY_LOAD_DATA);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @SuppressLint({"RtlHardcoded", "NotifyDataSetChanged"})
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if (event != null && event.getMsg().equals(MessageEvent.UPDATE_DOCTOR_INFO)) {
            doctorDetail();
        }
    }
}