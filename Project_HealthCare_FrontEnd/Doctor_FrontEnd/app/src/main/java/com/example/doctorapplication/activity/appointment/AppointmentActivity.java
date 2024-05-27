package com.example.doctorapplication.activity.appointment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.applandeo.materialcalendarview.CalendarDay;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener;
import com.example.doctorapplication.R;
import com.example.doctorapplication.model.Appointment;
import com.example.doctorapplication.model.AppointmentResult;
import com.example.doctorapplication.utils.API;
import com.example.doctorapplication.utils.AppUtils;
import com.example.doctorapplication.utils.base.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentActivity extends BaseActivity {

    LinearLayout lnBack;
    CalendarView clAppointDate;
    List<Calendar> calendarDays = new ArrayList<>();
    Map<String, List<Appointment>> groupAppointmentByDate = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);


        lnBack = findViewById(R.id.lnBack);
        clAppointDate = findViewById(R.id.clAppointDate);
        lnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        clAppointDate.setOnCalendarDayClickListener(new OnCalendarDayClickListener() {
            @Override
            public void onClick(@NonNull CalendarDay calendarDay) {
                toast(calendarDay.toString());
                int index = calendarDays.indexOf(calendarDay.getCalendar());
                if (index > -1){
                    Intent intent = new Intent(AppointmentActivity.this, AppointmentResultActivity.class);
                    @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(calendarDays.get(index));
                    intent.putExtra(JSON_DATA, AppUtils.gson().toJson(new AppointmentResult(date, groupAppointmentByDate.get(date))));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AppointmentActivity.this, AppointmentNoResultActivity.class);
                    startActivity(intent);
                }
            }
        });


        getAllAppointment();

    }

    public void getAllAppointment() {
        loadingDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                API.getAppointmentService().getAllAppointment().enqueue(new Callback<List<Appointment>>() {
                    @Override
                    public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                        dismissLoadingDialog();
                        if (response.isSuccessful()) {
                            List<Appointment> appointments = response.body();
                            if (appointments != null && !appointments.isEmpty()){
                                groupAppointmentByDate = appointments.stream().collect(Collectors.groupingBy(Appointment::getAppointmentDate, Collectors.toList()));
                                try {
                                    initView();
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Appointment>> call, Throwable t) {
                        dismissLoadingDialog();
                    }
                });
            }
        }, DELAY_LOAD_DATA);
    }

    @SuppressLint("SimpleDateFormat")
    private void initView() throws ParseException {
        List<String> appointDates = new ArrayList<>(groupAppointmentByDate.keySet());
        for (String appointDate: appointDates) {
            Calendar calendar = Calendar.getInstance();
            if (!AppUtils.isEmpty(appointDate)) {
                calendar.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(appointDate));
            }
            calendarDays.add(calendar);
        }
        clAppointDate.setSelectedDates(calendarDays);
    }
}