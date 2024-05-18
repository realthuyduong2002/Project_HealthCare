package com.example.patientapplication.utils;


import com.example.patientapplication.apiClient.RetrofitClient;
import com.example.patientapplication.services.AccountService;
import com.example.patientapplication.services.PatientService;

public class API {
    private API(){};

    public static final String API_URL = "http://192.168.1.9:8080/api/";

    public static AccountService getAccountService()
    {
        return RetrofitClient.getClient(API_URL).create(AccountService.class);
    }
    public static PatientService getPatientService()
    {
        return RetrofitClient.getClient(API_URL).create(PatientService.class);
    }
}
