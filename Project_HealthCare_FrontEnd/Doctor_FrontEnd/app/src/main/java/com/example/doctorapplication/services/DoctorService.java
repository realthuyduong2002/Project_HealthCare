package com.example.doctorapplication.services;


import com.example.doctorapplication.model.DoctorInfo;
import com.example.doctorapplication.model.Patient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DoctorService {
    //doctor info
    @GET("doctor/{id}")
    Call<DoctorInfo> getDoctorByID(@Path("id") String doctorID);

    @PUT("doctor/updatedoctor/{id}")
    Call<DoctorInfo> updateDoctor(@Path("id") String doctorID, @Body DoctorInfo doctorInfo);
}