package com.example.patientapplication.services;

import com.example.patientapplication.model.Appointment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppointmentService {
    @POST("patient/appointment/addAppointment")
    Call<Appointment> addNewAppointment(@Body Appointment appointment);

    @GET("appointments/{PatientID}")
    Call<List<Appointment>> getAllAppointments(@Path("PatientID") int patientID);

    @GET("patient/appointment/{PatientID}/{AppointmentID}")
    Call<List<Appointment>> getAppointmentAccordingToPatientID(
            @Path("PatientID") int patientID,
            @Path("AppointmentID") int appointmentID
    );
}
