package com.example.doctorapplication.activity.doctor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.doctorapplication.R;
import com.example.doctorapplication.model.Account;
import com.example.doctorapplication.model.DoctorInfo;
import com.example.doctorapplication.utils.API;
import com.example.doctorapplication.utils.AppUtils;
import com.example.doctorapplication.utils.base.BaseActivity;
import com.example.doctorapplication.utils.PreferenceUtils;
import com.example.doctorapplication.utils.event_bus.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorDetailInfoActivity extends BaseActivity {

    LinearLayout lnBack, lnEdit;
    TextView tvDoctorName, tvPhone, tvDateOfBirth, tvEmail, tvSpeciality, tvCity, tvWorkingDate, tvWorkingTime;
    RadioButton rbMale, rbFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail_info);
        doctorDetail();

        lnBack = findViewById(R.id.lnBack);
        lnEdit = findViewById(R.id.lnEdit);
        tvDoctorName = findViewById(R.id.tvDoctorName);
        tvPhone = findViewById(R.id.tvPhone);
        tvDateOfBirth = findViewById(R.id.tvDateOfBirth);
        tvEmail = findViewById(R.id.tvEmail);
        tvSpeciality = findViewById(R.id.tvSpeciality);
        tvCity = findViewById(R.id.tvCity);
        tvWorkingDate = findViewById(R.id.tvWorkingDate);
        tvWorkingTime = findViewById(R.id.tvWorkingTime);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        lnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailInfoActivity.this, EditDoctorInfoActivity.class));
            }
        });
    }

    public void doctorDetail() {
        loadingDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Account account = PreferenceUtils.getUserInfo();
                API.getDoctorService().getDoctorByID(account.getId()).enqueue(new Callback<DoctorInfo>() {
                    @Override
                    public void onResponse(Call<DoctorInfo> call, Response<DoctorInfo> response) {
                        if (response.isSuccessful()) {
                            DoctorInfo doctorInfo = response.body();
                            if (doctorInfo != null) {
                                tvDoctorName.setText(doctorInfo.getDoctorName());
                                tvPhone.setText(doctorInfo.getPhone());
                                tvDateOfBirth.setText(doctorInfo.getDateOfBirth());
                                tvEmail.setText(doctorInfo.getEmail());
                                tvSpeciality.setText(doctorInfo.getSpeciality());
                                if (doctorInfo.getGender() != null) {
                                    if (doctorInfo.getGender().equals("Male")) {
                                        rbMale.setChecked(true);
                                    } else rbFemale.setChecked(true);
                                } else {
                                    //none check
                                }
                                tvCity.setText(doctorInfo.getCity());
                                tvWorkingDate.setText(AppUtils.formatDate(doctorInfo.getWorkingDate(), "yyyy-MM-dd", "dd/MM/yyyy"));
                                tvWorkingTime.setText(AppUtils.formatDate(doctorInfo.getWorkingTime(), "hh:mm", "hh:mm a"));
                            }
                        }
                        dismissLoadingDialog();
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