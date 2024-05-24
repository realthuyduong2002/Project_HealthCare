package com.example.doctorapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorapplication.R;
import com.example.doctorapplication.model.Patient;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsertPatientActivity extends AppCompatActivity {

    EditText editTextName, editTextDateOfBirth, editTextLocation, editTextGender, editTextPhoneNumber;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_patient);

        editTextName = findViewById(R.id.editTextName);
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextGender = findViewById(R.id.editTextGender);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    // Lấy thông tin từ các trường nhập
                    String name = editTextName.getText().toString();
                    String dateOfBirth = editTextDateOfBirth.getText().toString();
                    String location = editTextLocation.getText().toString();
                    String gender = editTextGender.getText().toString();
                    String phoneNumber = editTextPhoneNumber.getText().toString();

                    // Kiểm tra xem ít nhất một trường quan trọng đã được nhập hay không
                    if (name.isEmpty() && dateOfBirth.isEmpty() && location.isEmpty() && gender.isEmpty() && phoneNumber.isEmpty()) {
                        Toast.makeText(InsertPatientActivity.this, "Please enter at least one piece of information", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Tạo một đối tượng Patient từ các dữ liệu đã nhập
                    Patient newPatient = new Patient();
                    newPatient.setPatientName(name);
                    newPatient.setDateOfBirth(dateOfBirth);
                    newPatient.setDistrict(location);
                    newPatient.setGender(gender);
                    newPatient.setPhone(phoneNumber);

                    // Thực hiện thêm bệnh nhân bằng AsyncTask
                    new AddPatientTask().execute(newPatient);
                } else {
                    Toast.makeText(InsertPatientActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    private class AddPatientTask extends AsyncTask<Patient, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Patient... patients) {
            try {
                URL url = new URL("http://192.168.1.4:8080/api/doctor/patient/addPatient");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("PatientName", patients[0].getPatientName());
                jsonParam.put("DateOfBirth", patients[0].getDateOfBirth());
                jsonParam.put("City", patients[0].getCity());
                jsonParam.put("Gender", patients[0].getGender());
                jsonParam.put("Phone", patients[0].getPhone());

                OutputStream os = conn.getOutputStream();
                os.write(jsonParam.toString().getBytes("UTF-8"));
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                return responseCode == HttpURLConnection.HTTP_OK;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(InsertPatientActivity.this, "Patient added successfully", Toast.LENGTH_SHORT).show();

                // Tạo Intent để truyền dữ liệu trở lại DoctorManagePatientList
                Intent intent = new Intent(InsertPatientActivity.this, PatientDetail.class);
                // Truyền dữ liệu của bệnh nhân đã được thêm vào Intent
                Patient[] patients = new Patient[0];
                Patient patient = patients[0];
                intent.putExtra("selectedPatient", (Parcelable) patient);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(InsertPatientActivity.this, "Failed to add patient", Toast.LENGTH_SHORT).show();
            }
        }
    }
}