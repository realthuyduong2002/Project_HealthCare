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
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorapplication.R;
import com.example.doctorapplication.model.Patient;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsertPatientActivity extends AppCompatActivity {

    EditText editTextName, editTextDateOfBirth, editTextCity, editTextWard, editTextDistrict, editTextPhoneNumber;
    RadioButton maleRadioButton, femaleRadioButton;
    Button buttonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_patient);

        editTextName = findViewById(R.id.editTextName);
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        editTextCity = findViewById(R.id.editTextCity);
        editTextWard = findViewById(R.id.editTextWard);
        editTextDistrict = findViewById(R.id.editTextDistrict);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        maleRadioButton = findViewById(R.id.maleRadioButton);
        femaleRadioButton = findViewById(R.id.femaleRadioButton);
        buttonCreate = findViewById(R.id.buttonCreate);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    // Get information from input fields
                    String name = editTextName.getText().toString();
                    String dateOfBirth = editTextDateOfBirth.getText().toString();
                    String city = editTextCity.getText().toString();
                    String ward = editTextWard.getText().toString();
                    String district = editTextDistrict.getText().toString();
                    String phoneNumber = editTextPhoneNumber.getText().toString();
                    String gender = "";

                    // Check which RadioButton is selected and assign the corresponding gender
                    if (maleRadioButton.isChecked()) {
                        gender = "Male";
                    } else if (femaleRadioButton.isChecked()) {
                        gender = "Female";
                    } else {
                        // If no RadioButton is selected
                        Toast.makeText(InsertPatientActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Check if at least one important field is entered
                    if (name.isEmpty() && dateOfBirth.isEmpty() && city.isEmpty() && ward.isEmpty() && district.isEmpty() && phoneNumber.isEmpty()) {
                        Toast.makeText(InsertPatientActivity.this, "Please enter at least one piece of information", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Create a Patient object from the entered data
                    Patient newPatient = new Patient();
                    newPatient.setPatientName(name);
                    newPatient.setDateOfBirth(dateOfBirth);
                    newPatient.setCity(city);
                    newPatient.setWard(ward);
                    newPatient.setDistrict(district);
                    newPatient.setGender(gender);
                    newPatient.setPhone(phoneNumber);

                    // Add patient using AsyncTask
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

        private Patient[] patients;
        @Override
        protected Boolean doInBackground(Patient... patients) {
            try {
                URL url = new URL("http://172.30.161.24:8080/api/doctor/patient/addPatient");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("PatientName", patients[0].getPatientName());
                jsonParam.put("DateOfBirth", patients[0].getDateOfBirth());
                jsonParam.put("City", patients[0].getCity());
                jsonParam.put("Ward", patients[0].getWard());
                jsonParam.put("District", patients[0].getDistrict());
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

                // Create Intent to pass data back to PatientDetail
                Intent intent = new Intent(InsertPatientActivity.this, PatientDetail.class);
                // Pass data of the newly added patient to Intent
                Patient newPatient = patients[0]; // Access the first (and only) patient from the array
                intent.putExtra("selectedPatient", newPatient);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(InsertPatientActivity.this, "Failed to add patient", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
