package com.example.doctorapplication.activity.doctor;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctorapplication.R;
import com.example.doctorapplication.model.Account;
import com.example.doctorapplication.model.DoctorInfo;
import com.example.doctorapplication.utils.API;
import com.example.doctorapplication.utils.AppUtils;
import com.example.doctorapplication.utils.base.BaseActivity;
import com.example.doctorapplication.utils.PreferenceUtils;
import com.example.doctorapplication.utils.event_bus.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDoctorInfoActivity extends BaseActivity {

    DoctorInfo doctorInfo = null;
    LinearLayout lnBack, lnSaveEdit;
    TextView tvWorkingDate, tvWorkingTime;
    EditText edtDoctorName, edtPhoneNumber, edtDateOfBirth, edtEmail, edtSpeciality, edtCity;
    RadioButton rbMale, rbFemale;
    FrameLayout frWorkingDate, frWorkingTime;
    View viewDateOfBirth;
    Calendar calendar = Calendar.getInstance();

    Account account;

    String workingTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor_info);

        account = PreferenceUtils.getUserInfo();

        doctorDetail();

        lnBack = findViewById(R.id.lnBack);
        lnSaveEdit = findViewById(R.id.lnSaveEdit);
        edtDoctorName = findViewById(R.id.edtDoctorName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtDateOfBirth = findViewById(R.id.edtDateOfBirth);
        edtEmail = findViewById(R.id.edtEmail);
        edtSpeciality = findViewById(R.id.edtSpeciality);
        edtCity = findViewById(R.id.edtCity);
        tvWorkingDate = findViewById(R.id.tvWorkingDate);
        tvWorkingTime = findViewById(R.id.tvWorkingTime);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        viewDateOfBirth = findViewById(R.id.viewDateOfBirth);
        frWorkingDate = findViewById(R.id.frWorkingDate);
        frWorkingTime = findViewById(R.id.frWorkingTime);

        lnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                dialogDatePicker(edtDateOfBirth);
            }
        });

        frWorkingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                dialogDatePicker(tvWorkingDate);
            }
        });

        frWorkingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                dialogTimePicker(tvWorkingTime);
            }
        });

        lnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDoctorInfo();
            }
        });
    }

    public void doctorDetail() {
        loadingDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                API.getDoctorService().getDoctorByID(account.getId()).enqueue(new Callback<DoctorInfo>() {
                    @Override
                    public void onResponse(Call<DoctorInfo> call, Response<DoctorInfo> response) {
                        dismissLoadingDialog();
                        if (response.isSuccessful()) {
                            doctorInfo = response.body();
                            initData();
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

    private void initData() {
        if (doctorInfo != null) {
            edtDoctorName.setText(doctorInfo.getDoctorName());
            edtPhoneNumber.setText(doctorInfo.getPhone());
            edtDateOfBirth.setText(doctorInfo.getDateOfBirth());
            edtEmail.setText(doctorInfo.getEmail());
            edtSpeciality.setText(doctorInfo.getSpeciality());
            if (doctorInfo.getGender() != null) {
                if (doctorInfo.getGender().equals("Male")) {
                    rbMale.setChecked(true);
                } else rbFemale.setChecked(true);
            } else {
                //none check
            }
            edtCity.setText(doctorInfo.getCity());
            tvWorkingDate.setText(AppUtils.formatDate(doctorInfo.getWorkingDate(), "yyyy-MM-dd", "dd/MM/yyyy"));
            tvWorkingTime.setText(AppUtils.formatDate(doctorInfo.getWorkingTime(), "hh:mm", "hh:mm a"));
            workingTime = doctorInfo.getWorkingTime();
        }
    }

    private void dialogDatePicker(TextView view) {
        final int[] day = new int[1];
        final int[] month = new int[1];
        final int[] year = new int[1];
        String dateStr = view.getText().toString().trim();
        if (AppUtils.isEmpty(dateStr)) {
            day[0] = calendar.get(Calendar.DATE);
            month[0] = calendar.get(Calendar.MONTH);
            year[0] = calendar.get(Calendar.YEAR);
        } else {
            String[] dateArray = dateStr.split("/");
            day[0] = Integer.parseInt(dateArray[0]);
            month[0] = Integer.parseInt(dateArray[1]) - 1;
            year[0] = Integer.parseInt(dateArray[2]);
        }
        @SuppressLint("DefaultLocale") DatePickerDialog datePickerDialog = new DatePickerDialog(EditDoctorInfoActivity.this, (view1, yearPicker, monthPicker, dayPicker) -> {
            //get date int
            day[0] = dayPicker;
            month[0] = monthPicker + 1;
            year[0] = yearPicker;
            //get date string
            String dayStr = String.format("%02d", day[0]);
            String monthStr = String.format("%02d", month[0]);
            String yearStr = String.format("%d", year[0]);
            //show to textview
            view.setText(getString(R.string.txt_date_format, dayStr, monthStr, yearStr));
        }, year[0], month[0], day[0]);
        datePickerDialog.show();
    }

    private void dialogTimePicker(TextView view) {
        final int[] hour = new int[1];
        final int[] minute = new int[1];
        String dateStr = view.getText().toString().replace("AM", "").replace("PM", "").trim();
        if (AppUtils.isEmpty(dateStr)) {
            hour[0] = calendar.get(Calendar.HOUR_OF_DAY);
            minute[0] = calendar.get(Calendar.MINUTE);
        } else {
            String[] timeArray = dateStr.split(":");
            hour[0] = Integer.parseInt(timeArray[0]);
            minute[0] = Integer.parseInt(timeArray[1]);
        }
        @SuppressLint("DefaultLocale") TimePickerDialog timePickerDialog = new TimePickerDialog(EditDoctorInfoActivity.this, (view1, hourOfDay, minuteOfDay) -> {
            hour[0] = hourOfDay;
            minute[0] = minuteOfDay;
            //get date string
            String hourStr = String.format("%02d", hour[0]);
            String minuteStr = String.format("%02d", minute[0]);
            //show to textview
            workingTime = getString(R.string.txt_time_format, hourStr, minuteStr);
            view.setText(AppUtils.formatDate(workingTime, "hh:mm", "hh:mm a"));
        }, hour[0], minute[0], true);
        timePickerDialog.show();
    }

    public void updateDoctorInfo() {
        String doctorName = edtDoctorName.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String dateOfBirth = edtDateOfBirth.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String specialty = edtSpeciality.getText().toString().trim();
        String gender = "";
        if (rbMale.isChecked()) {
            gender = "Male";
        } else if (rbFemale.isChecked()){
            gender = "Female";
        }
        String city = edtCity.getText().toString().trim();
        String workingDate = tvWorkingDate.getText().toString().trim();

        if (doctorName.isEmpty()){
            Toast.makeText(EditDoctorInfoActivity.this, "Doctor's name is not empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phoneNumber.isEmpty()){
            Toast.makeText(EditDoctorInfoActivity.this, "Phone number is not empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dateOfBirth.isEmpty()){
            Toast.makeText(EditDoctorInfoActivity.this, "Date of birth is not empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.isEmpty()){
            Toast.makeText(EditDoctorInfoActivity.this, "Email is not empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (specialty.isEmpty()){
            Toast.makeText(EditDoctorInfoActivity.this, "Specialty is not empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (gender.isEmpty()){
            Toast.makeText(EditDoctorInfoActivity.this, "Gender is not empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingDialog();
        doctorInfo.setDoctorName(doctorName);
        doctorInfo.setPhone(phoneNumber);
        doctorInfo.setDateOfBirth(dateOfBirth);
        doctorInfo.setEmail(email);
        doctorInfo.setSpeciality(specialty);
        doctorInfo.setGender(gender);
        doctorInfo.setCity(city);
        doctorInfo.setWorkingDate(AppUtils.formatDate(workingDate, "dd/MM/yyyy","yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        doctorInfo.setWorkingTime(workingTime + ":00");

        API.getDoctorService().updateDoctor(account.getId(), doctorInfo).enqueue(new Callback<DoctorInfo>() {
            @Override
            public void onResponse(Call<DoctorInfo> call, Response<DoctorInfo> response) {
                dismissLoadingDialog();
                if (response.isSuccessful()) {
                    Toast.makeText(EditDoctorInfoActivity.this, "Update doctor's information successfully", Toast.LENGTH_SHORT).show();
                    //RELOAD DOCTOR INFOR
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.UPDATE_DOCTOR_INFO));
                    finish();
                } else {
                    Toast.makeText(EditDoctorInfoActivity.this, "Update doctor's information failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DoctorInfo> call, Throwable t) {
                dismissLoadingDialog();
                Log.e("ERROR: ", t.getMessage());
                Toast.makeText(EditDoctorInfoActivity.this, "Update doctor's information failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}