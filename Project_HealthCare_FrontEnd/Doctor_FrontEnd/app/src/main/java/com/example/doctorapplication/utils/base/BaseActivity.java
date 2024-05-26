package com.example.doctorapplication.utils.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.doctorapplication.R;
import com.example.doctorapplication.activity.LoginActivity;
import com.example.doctorapplication.utils.PreferenceUtils;
import com.example.doctorapplication.utils.dialog.ProgressDialog;


public abstract class BaseActivity extends AppCompatActivity {
    public static final int DELAY_LOG_OUT = 1000;
    public static final int DELAY_LOAD_DATA = 1000;

    public static final String JSON_DATA = "JSON_DATA";

    protected ProgressDialog progressDialog;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.color_0D6EFD));
    }


    public void loadingDialog() {
        dismissLoadingDialog();
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show(false);
        }
    }

    public void dismissLoadingDialog() {
        if (progressDialog != null) {
            progressDialog.hideProgress();
        }
    }


    public void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }


    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
            View current = getCurrentFocus();
            if (current != null) current.clearFocus();
        }
    }

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        }
    }


    public void logout() {
        loadingDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //logout
                PreferenceUtils.saveUserInfo(null);
                goToLogin();
                dismissLoadingDialog();
            }
        }, DELAY_LOG_OUT);
    }

    private void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
