package com.example.patientapplication.services;



import com.example.patientapplication.model.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccountService {
    @POST("register")
    Call<Account> Register(@Body Account account);
    @POST("login")
    Call<Account> Login(@Body Account account);
    @GET("getPhonenumber/{PhoneNumber}")
    Call<Account> getPhoneNumber(@Path("PhoneNumber") String PhoneNumber);

    @PUT("changePassword/{PhoneNumber}")
    Call<Account> changePassword(@Path("PhoneNumber") String PhoneNumber, @Body Account account);
}
