package com.example.doctorapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppointmentResult {
    String appointDate;
    List<Appointment> appointments;


    public AppointmentResult(String appointDate, List<Appointment> appointments) {
        this.appointDate = appointDate;
        this.appointments = appointments;
    }

    public String getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}