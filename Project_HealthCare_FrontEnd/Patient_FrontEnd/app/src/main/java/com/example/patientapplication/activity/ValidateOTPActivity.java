package com.example.patientapplication.activity;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.patientapplication.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ValidateOTPActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 101;
    private static final String SMS_SENT_ACTION = "SMS_SENT_ACTION";
    private String receivedOTP;
    private String receivedPhoneNumber;

    ImageButton btnBack;
    Button btnResend;
    EditText edtValidateOTP;
    Button btnValidateOTP;

    private final BroadcastReceiver sentSmsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(SMS_SENT_ACTION)) {
                int resultCode = getResultCode();
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    // SMS sent successfully, show a notification in the system's notification dropdown
                    showNotification("SMS Sent", "Your OTP has been sent via SMS.");
                } else {
                    // SMS sending failed
                    Toast.makeText(ValidateOTPActivity.this, "Failed to send SMS", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_otpactivity);

        receivedOTP = getIntent().getStringExtra("OTP");
        receivedPhoneNumber = getIntent().getStringExtra("PHONENUMBER");
        Log.d("OTP", receivedOTP);
        Log.d("PHONENUMBER", receivedPhoneNumber);

        edtValidateOTP = findViewById(R.id.edtValidateOTP);
        btnValidateOTP = findViewById(R.id.btnValidateOTP);
        btnBack = findViewById(R.id.btnBack);
        btnResend = findViewById(R.id.btnResend);

        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSmsPermission();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ValidateOTPActivity.this, SignupActivity.class));
            }
        });

        btnValidateOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOTP = edtValidateOTP.getText().toString();
                if (enteredOTP.equals(receivedOTP)) {
                    // Correct OTP
                    Toast.makeText(getApplicationContext(), "Correct OTP", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ValidateOTPActivity.this, PasswordActivity.class);
                    intent.putExtra("PHONENUMBER", receivedPhoneNumber);
                    startActivity(intent);
                } else {
                    // Wrong OTP
                    Toast.makeText(getApplicationContext(), "Wrong OTP, enter again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerReceiver(sentSmsReceiver, new IntentFilter(SMS_SENT_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(sentSmsReceiver);
    }

    private void requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS}, PERMISSION_REQUEST_CODE);
        } else {
            sendOtp();
        }
    }

    private void sendOtp() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSION_REQUEST_CODE);
            return;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_REQUEST_CODE);
            return;
        }

        String apiUrl = "http://192.168.1.6:8080/send-api";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(apiUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("phoneNumber", receivedPhoneNumber);

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        JSONObject jsonResponse = new JSONObject(response.toString());
                        receivedOTP = jsonResponse.getString("random");

                        sendSMS(receivedPhoneNumber, "Your OTP is: " + receivedOTP);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ValidateOTPActivity.this, "OTP Resent", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Log.e("SendOTP", "Failed to send OTP. Response Code: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("SendOTP", "Exception: " + e.getMessage());
                }
            }
        }).start();
    }

    private void sendSMS(String phoneNumber, String message) {
        PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT_ACTION), 0);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, sentPendingIntent, null);
    }

    private void showNotification(String title, String message) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "otp_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Intent to launch the messaging app with the SMS conversation for the specified phone number
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + receivedPhoneNumber));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);

        notificationManager.notify(0, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendOtp();
            } else {
                Toast.makeText(this, "Permission denied. Cannot send OTP.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
