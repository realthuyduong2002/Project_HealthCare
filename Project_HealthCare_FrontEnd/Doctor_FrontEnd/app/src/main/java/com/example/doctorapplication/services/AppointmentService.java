package com.example.doctorapplication.services;


import com.example.doctorapplication.model.Appointment;
import com.example.doctorapplication.model.DoctorInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AppointmentService {
    @GET("/api/doctor/appointments")
    Call<List<Appointment>> getAllAppointment();

    @GET("/doctor/appointments/{id}")
    Call<DoctorInfo> getAppointmentByID(@Path("id") String appointmentId);
}