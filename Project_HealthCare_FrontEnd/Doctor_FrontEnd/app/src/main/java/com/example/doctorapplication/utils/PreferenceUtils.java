package com.example.doctorapplication.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.doctorapplication.App;
import com.example.doctorapplication.model.Account;
import com.google.gson.Gson;


public class PreferenceUtils {
    private static final String ACCOUNT = "ACCOUNT";
    private static final String IS_LOGIN = "IS_LOGIN";


    private static SharedPreferences preferences;

    public static synchronized void init() {
        preferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance());
    }


    public static void saveUserInfo(Account userInfo) {
        if (userInfo != null) {
            preferences.edit().putString(ACCOUNT, new Gson().toJson(userInfo)).apply();
        } else {
            preferences.edit().putString(ACCOUNT, "").apply();
        }
    }

    public static Account getUserInfo() {
        String userJson = preferences.getString(ACCOUNT, "");
        if (!userJson.isEmpty()) return new Gson().fromJson(userJson, Account.class);
        return null;
    }



}
