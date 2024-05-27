package com.example.doctorapplication.utils;

import com.example.doctorapplication.activity.appointment.AppointmentActivity;
import com.example.doctorapplication.apiClient.RetrofitClient;
import com.example.doctorapplication.services.AccountService;
import com.example.doctorapplication.services.AppointmentService;
import com.example.doctorapplication.services.DoctorService;
import com.example.doctorapplication.services.PatientService;

public class API {
    private API() {
    }

    public static final String API_URL = "http://192.168.1.4:8080/api/";

    public static PatientService getPatientService(String apiUrl) {
        return RetrofitClient.getClient(API_URL).create(PatientService.class);
    }

    public static AccountService getAccountService() {
        return RetrofitClient.getClient(API_URL).create(AccountService.class);
    }

    public static PatientService getPatientService() {
        return null;
    }

    public static DoctorService getDoctorService()
    {
        return RetrofitClient.getClient(API_URL).create(DoctorService.class);
    }

    public static AppointmentService getAppointmentService()
    {
        return RetrofitClient.getClient(API_URL).create(AppointmentService.class);
    }
}