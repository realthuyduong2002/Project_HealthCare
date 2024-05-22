package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.patientapplication.R;
import com.example.patientapplication.model.Appointment;
import com.example.patientapplication.services.AppointmentService;
import com.example.patientapplication.utils.API;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Checkout extends AppCompatActivity {

    TextView tvAppointmentID, tvPatientID, tvAppointmentDate,
            tvAppointmentTime, tvSpeciality, tvSymptom;

    private static final String TAG = "CheckoutActivity";
    private Button btnPaynow;
    private final String SECRET_KEY = "sk_test_51PJEUyP60mBf6lKmX6emXcPejnMT0pvIWB4nvlMWBF6NP27Ny7jaUOHMwSvUlvW3Wep4jxKBtdYwkMS53k98oiWR00wkqxFhJn";
    private final String PUBLIC_KEY = "pk_test_51PJEUyP60mBf6lKmeE1mJwz9jJsOEsVLMhzziJH3B325o7jmCdwiG0e46tD2pPwR8oOWOXRuwXD5zA7K71VBwkj000sFyLQT1H";
    private PaymentSheet paymentSheet;
    private String customerID;
    private String ephemeralKey;
    private String clientSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        btnPaynow = findViewById(R.id.btnPaynow);

        PaymentConfiguration.init(this, PUBLIC_KEY);

        paymentSheet = new PaymentSheet(this, this::onPaymentResult);

        btnPaynow.setOnClickListener(v -> PaymentFlow());

        tvAppointmentID = findViewById(R.id.tvAppointmentID);
        tvPatientID = findViewById(R.id.tvPatientID);
        tvAppointmentDate = findViewById(R.id.tvAppointmentDate);
        tvAppointmentTime = findViewById(R.id.tvAppointmentTime);
        tvSpeciality = findViewById(R.id.tvSpeciality);
        tvSymptom = findViewById(R.id.tvSymptom);

        createCustomer();
        loadLatestAppointment();

    }

    private void loadLatestAppointment() {
        AppointmentService appointmentService = API.getAppointmentService();
        Intent intent = getIntent();
        int patientID = intent.getIntExtra("PATIENTID", -1);
        Log.d("PATIENTID",String.valueOf(patientID));
        if (patientID != -1) {
            appointmentService.getLatestAppointment(patientID).enqueue(new Callback<Appointment>() {
                @Override
                public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Appointment latestAppointment = response.body();
                        tvAppointmentID.setText(String.valueOf(latestAppointment.getAppointmentID()));
                        tvPatientID.setText(String.valueOf(latestAppointment.getPatientID()));
                        tvAppointmentDate.setText(latestAppointment.getAppointmentDate());
                        tvAppointmentTime.setText(latestAppointment.getAppointmentTime());
                        tvSpeciality.setText(latestAppointment.getSpeciality());
                        tvSymptom.setText(latestAppointment.getSymptom());
                    } else {
                        Log.e(TAG, "Failed to retrieve the latest appointment: " + response.message());
                        Toast.makeText(Checkout.this, "Failed to retrieve the latest appointment: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Appointment> call, Throwable t) {
                    Log.e(TAG, "Error fetching the latest appointment: " + t.getMessage());
                    Toast.makeText(Checkout.this, "Error fetching the latest appointment: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.e(TAG, "PatientID not found in intent");
            Toast.makeText(this, "PatientID not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void createCustomer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        customerID = object.getString("id");
                        Toast.makeText(Checkout.this, customerID, Toast.LENGTH_SHORT).show();
                        getEphemeralKey(customerID);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException while creating customer: " + e.getMessage());
                    }
                },
                error -> {
                    // Handle error response
                    Log.e(TAG, "Error creating customer: " + error.getMessage());
                    Toast.makeText(Checkout.this, "Error creating customer: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SECRET_KEY);
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Checkout.this);
        requestQueue.add(stringRequest);
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(this, "Payment success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Checkout.this, BillActivity.class);
            startActivity(intent);
        } else {
            Log.e(TAG, "Payment failed: " + paymentSheetResult.toString());
            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEphemeralKey(String customerID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        ephemeralKey = object.getString("secret");
                        Toast.makeText(this, ephemeralKey, Toast.LENGTH_SHORT).show();
                        getClientSecret(customerID, ephemeralKey);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException while getting ephemeral key: " + e.getMessage());
                    }
                },
                error -> {
                    // Handle error response
                    Log.e(TAG, "Error getting ephemeral key: " + error.getMessage());
                    Toast.makeText(this, "Error getting ephemeral key: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SECRET_KEY);
                header.put("Stripe-Version", "2024-04-10");
                return header;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Checkout.this);
        requestQueue.add(stringRequest);
    }

    private void getClientSecret(String customerID, String ephemeralKey) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                response -> {
                    try {
                        JSONObject object = new JSONObject(response);
                        clientSecret = object.getString("client_secret");
                        Toast.makeText(this, clientSecret, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException while getting client secret: " + e.getMessage());
                    }
                },
                error -> {
                    // Handle error response
                    Log.e(TAG, "Error getting client secret: " + error.getMessage());
                    Toast.makeText(this, "Error getting client secret: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + SECRET_KEY);
                return header;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                params.put("amount", "586");
                params.put("currency", "usd");
                params.put("automatic_payment_methods[enabled]", "true");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Checkout.this);
        requestQueue.add(stringRequest);
    }

    private void PaymentFlow() {
        if (clientSecret != null && customerID != null && ephemeralKey != null) {
            paymentSheet.presentWithPaymentIntent(
                    clientSecret, new PaymentSheet.Configuration("Healthcare", new PaymentSheet.CustomerConfiguration(
                            customerID,
                            ephemeralKey))
            );
        } else {
            Log.e(TAG, "Payment details are not ready");
            Toast.makeText(this, "Payment details are not ready", Toast.LENGTH_SHORT).show();
        }
    }
}
