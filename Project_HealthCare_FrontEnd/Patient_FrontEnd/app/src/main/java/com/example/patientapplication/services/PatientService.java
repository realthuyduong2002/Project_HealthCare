package com.example.patientapplication.services;

import com.example.patientapplication.model.Patient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PatientService {
    @GET("doctor/patients")
    Call<List<Patient>> getPatients();

    @GET("doctor/patient/{PatientID}")
    Call<Patient> GetPatientDetail(@Path("PatientID") Integer patientID);

    @POST("doctor/patient/addPatient")
    Call<Patient> NewProfile(@Body Patient patient);

    @PUT("patient/{PatientID}")
    Call<Patient> UpdatePatientInformation(@Path("PatientID") int PatientID, @Body Patient patient);

    @DELETE("patient/deletePatient/{PatientID}")
    Call<Patient> DeletePatientProfile(@Path("PatientID") int PatientID);
}   
