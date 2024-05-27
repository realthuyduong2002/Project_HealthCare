package com.example.doctorapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("PatientID")
    @Expose
    private String PatientID;
    @SerializedName("PatientName")
    @Expose
    private String PatientName;
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
    @SerializedName("PaymentMethod")
    @Expose
    private String PaymentMethod;
    @SerializedName("Symptom")
    @Expose
    private String Symptom;
    @SerializedName("Status")
    @Expose
    private String Status;


    public Appointment(String patientID, String patientName, String appointmentDate, String appointmentTime, String doctorID, String doctorName, String speciality, String symptom, String status) {
        PatientID = patientID;
        PatientName = patientName;
        AppointmentDate = appointmentDate;
        AppointmentTime = appointmentTime;
        DoctorID = doctorID;
        DoctorName = doctorName;
        Speciality = speciality;
        Symptom = symptom;
        Status = status;
    }

    public String getAppointmentID() {
        return id;
    }

    public void setAppointmentID(String id) {
        this.id = id;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String patientID) {
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

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}