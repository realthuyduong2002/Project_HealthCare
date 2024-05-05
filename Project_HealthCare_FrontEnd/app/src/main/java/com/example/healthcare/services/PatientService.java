package com.example.healthcare.services;

import com.example.healthcare.model.Patient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PatientService {
    //List of patient
    @GET("doctor/patients")
    Call<List<Patient>> getPatients();


    //Patient detail (Information)
    @GET("doctor/patient/{PatientID}")
    Call<Patient> getPatientByID(@Path("PatientID") int patientId);
}
