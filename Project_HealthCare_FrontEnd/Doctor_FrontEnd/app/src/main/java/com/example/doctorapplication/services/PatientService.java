package com.example.doctorapplication.services;

import com.example.doctorapplication.model.Patient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PatientService {
    // List of patients
    @GET("doctor/patients")
    Call<List<Patient>> getPatients();

    // Patient detail (Information)
    @GET("doctor/patient/{id}")
    Call<Patient> getPatientByID(@Path("id") int patientId);

    // Update patient
    @PUT("doctor/patient/updatePatient/{id}")
    Call<Patient> updatePatient(@Path("id") int id, @Body Patient updatedPatient);
}
