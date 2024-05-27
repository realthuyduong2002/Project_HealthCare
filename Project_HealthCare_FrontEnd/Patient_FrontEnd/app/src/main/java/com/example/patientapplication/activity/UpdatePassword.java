package com.example.patientapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.patientapplication.R;
import com.example.patientapplication.model.Account;
import com.example.patientapplication.services.AccountService;
import com.example.patientapplication.utils.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePassword extends AppCompatActivity {

    private static final String TAG = "UpdatePassword";
    private EditText edtPassword, edtConfirmPassword;
    private Button btnRegister;
    private ImageButton btnBack;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        phoneNumber = getIntent().getStringExtra("PhoneNumber");

        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        btnRegister.setOnClickListener(v -> {
            String password = edtPassword.getText().toString().trim();
            String confirmPassword = edtConfirmPassword.getText().toString().trim();

            if (validateInput(password, confirmPassword)) {
                Account account = new Account(phoneNumber, password, false); // Assuming false for the role
                updatePassword(account);
            }
        });
    }

    private boolean validateInput(String password, String confirmPassword) {
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void updatePassword(Account account) {
        AccountService accountService = API.getAccountService();
        Call<Account> call = accountService.changePassword(account.getPhoneNumber(), account);
        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdatePassword.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdatePassword.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UpdatePassword.this, "Failed to update password: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Log.e(TAG, "Error updating password: " + t.getMessage());
                Toast.makeText(UpdatePassword.this, "Error updating password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
