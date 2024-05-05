package com.example.healthcare.utils;

import com.example.healthcare.apiClient.RetrofitClient;
import com.example.healthcare.services.PatientService;

public class API {
    private API(){};

    public static final String API_URL = "http://192.168.1.6:8080/api/";

    public static PatientService getPatientService(){
        return RetrofitClient.getClient(API_URL).create(PatientService.class);
    }
}
