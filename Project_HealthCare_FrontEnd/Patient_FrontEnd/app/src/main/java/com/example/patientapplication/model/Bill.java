package com.example.patientapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serial;

public class Bill {
    @SerializedName("_id")
    @Expose
    private int BillID;

    @SerializedName("PatientID")
    @Expose
    private int PatientID;

    @SerializedName("PatientName")
    @Expose
    private String PatientName;

    @SerializedName("AppointmentDate")
    @Expose
    private String AppointmentDate;

    @SerializedName("AppointmentTime")
    @Expose
    private String AppointmentTime;

    @SerializedName("AppointmentID")
    @Expose
    private int AppointmentID;

    @SerializedName("TotalCost")
    @Expose
    private float TotalCost;


    @SerializedName("DateCreate")
    @Expose
    private String DateCreate;

    public Bill(int patientID, String patientName, String appointmentDate, String appointmentTime, int appointmentID, float totalCost, String dateCreate) {
        PatientID = patientID;
        PatientName = patientName;
        AppointmentDate = appointmentDate;
        AppointmentTime = appointmentTime;
        AppointmentID = appointmentID;
        TotalCost = totalCost;
        DateCreate = dateCreate;
    }

    public int getBillID() {
        return BillID;
    }

    public void setBillID(int billID) {
        BillID = billID;
    }

    public int getPatientID() {
        return PatientID;
    }

    public void setPatientID(int patientID) {
        PatientID = patientID;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        AppointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return AppointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        AppointmentTime = appointmentTime;
    }

    public int getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        AppointmentID = appointmentID;
    }

    public float getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(float totalCost) {
        TotalCost = totalCost;
    }

    public String getDateCreate() {
        return DateCreate;
    }

    public void setDateCreate(String dateCreate) {
        DateCreate = dateCreate;
    }
}
