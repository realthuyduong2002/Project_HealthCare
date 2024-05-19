package com.example.patientapplication.services;

import com.example.patientapplication.model.Doctor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DoctorService {
    @GET("doctors")
    Call<List<Doctor>> getAllDoctors();
}
