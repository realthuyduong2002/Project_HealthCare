package com.example.patientapplication.services;

import com.example.patientapplication.model.Bill;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BillService {
    @POST("patient/bill/addBill")
    Call<Bill> addNewBill(@Body Bill bill);

    @GET("patient/bill/{PatientID}")
    Call<Bill> getLatestBill(@Path("PatientID") int PatientID);
}
