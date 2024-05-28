package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.patientapplication.R;
import com.example.patientapplication.model.Bill;
import com.example.patientapplication.services.BillService;
import com.example.patientapplication.utils.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillActivity extends AppCompatActivity {
    private static final String TAG = "BillActivity";
    int PatientID = 0;
    TextView tvBillID, tvPatientID, tvPatientName, tvAppointmentDate, tvAppointmentTime, tvAppointmentID, tvTotalCost, tvDateCreate;
    Button btnGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        tvBillID = findViewById(R.id.tvBillID);
        tvPatientID = findViewById(R.id.tvPatientID);
        tvPatientName = findViewById(R.id.tvPatientName);
        tvAppointmentDate = findViewById(R.id.tvAppointmentDate);
        tvAppointmentTime = findViewById(R.id.tvAppointmentTime);
        tvAppointmentID = findViewById(R.id.tvAppointmentID);
        tvTotalCost = findViewById(R.id.tvTotalCost);
        tvDateCreate = findViewById(R.id.tvDateCreate);
        btnGoBack = findViewById(R.id.btnGoBack);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BillActivity.this, HomepageActivity.class));
            }
        });
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("PATIENTID")) {
            String patientIDString = intent.getStringExtra("PATIENTID");

            if (patientIDString != null && !patientIDString.isEmpty()) {
                PatientID = Integer.parseInt(patientIDString);

                loadLatestBill();
            } else {
                Log.e(TAG, "PatientID extra is null or empty");
                Toast.makeText(this, "PatientID extra is null or empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e(TAG, "PatientID extra not found in intent");
            Toast.makeText(this, "PatientID extra not found in intent", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadLatestBill() {
        BillService billService = API.getBillService();
        int patientID = PatientID;
        if (patientID != -1) {
            billService.getLatestBill(PatientID).enqueue(new Callback<Bill>() {
                @Override
                public void onResponse(Call<Bill> call, Response<Bill> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Bill latestBill = response.body();
                        tvBillID.setText(String.valueOf(latestBill.getBillID()));
                        tvPatientID.setText(String.valueOf(latestBill.getPatientID()));
                        tvPatientName.setText(latestBill.getPatientName());
                        tvAppointmentDate.setText(latestBill.getAppointmentDate());
                        tvAppointmentTime.setText(latestBill.getAppointmentTime());
                        tvAppointmentID.setText(String.valueOf(latestBill.getAppointmentID()));
                        tvTotalCost.setText(String.valueOf(latestBill.getTotalCost()));
                        tvDateCreate.setText(latestBill.getDateCreate());
                    } else {
                        Log.e(TAG, "Failed to retrieve the latest appointment: " + response.message());
                        Toast.makeText(BillActivity.this, "Failed to retrieve the latest appointment: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Bill> call, Throwable t) {
                    Log.e(TAG, "Error fetching the latest appointment: " + t.getMessage());
                    Toast.makeText(BillActivity.this, "Error fetching the latest appointment: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.e(TAG, "PatientID not found or invalid");
            Toast.makeText(this, "PatientID not found or invalid", Toast.LENGTH_SHORT).show();
        }
    }
}
