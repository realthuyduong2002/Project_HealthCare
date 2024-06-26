package com.example.patientapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment {
    @SerializedName("_id")
    @Expose
    private int AppointmentID;
    @SerializedName("PatientID")
    @Expose
    private int PatientID;
    @SerializedName("AppointmentDate")
    @Expose
    private String AppointmentDate;
    @SerializedName("AppointmentTime")
    @Expose
    private String AppointmentTime;
    @SerializedName("DoctorID")
    @Expose
    private String DoctorID;
    @SerializedName("DoctorName")
    @Expose
    private String DoctorName;
    @SerializedName("Speciality")
    @Expose
    private String Speciality;
    @SerializedName("Symptom")
    @Expose
    private String Symptom;
    @SerializedName("Status")
    @Expose
    private String Status;

    public Appointment(int patientID, String appointmentDate, String appointmentTime, String doctorID, String doctorName, String speciality, String symptom, String status) {
        PatientID = patientID;
        AppointmentDate = appointmentDate;
        AppointmentTime = appointmentTime;
        DoctorID = doctorID;
        DoctorName = doctorName;
        Speciality = speciality;
        Symptom = symptom;
        Status = status;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
    public int getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        AppointmentID = appointmentID;
    }

    public int getPatientID() {
        return PatientID;
    }

    public void setPatientID(int patientID) {
        PatientID = patientID;
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

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }

    public String getSymptom() {
        return Symptom;
    }

    public void setSymptom(String symptom) {
        Symptom = symptom;
    }
}
