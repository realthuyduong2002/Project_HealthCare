package com.example.patientapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doctor {
    @SerializedName("_id")
    @Expose
    private int DoctorID;
    @SerializedName("DoctorName")
    @Expose
    private String DoctorName;
    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("Email")
    @Expose
    private String Email;
    @SerializedName("Phone")
    @Expose
    private String Phone;
    @SerializedName("Speciality")
    @Expose
    private String Speciality;
    @SerializedName("WorkingDate")
    @Expose
    private String WorkingDate;
    @SerializedName("WorkingTime")
    @Expose
    private String WorkingTime;

    public int getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(int doctorID) {
        DoctorID = doctorID;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }

    public String getWorkingDate() {
        return WorkingDate;
    }

    public void setWorkingDate(String workingDate) {
        WorkingDate = workingDate;
    }

    public String getWorkingTime() {
        return WorkingTime;
    }

    public void setWorkingTime(String workingTime) {
        WorkingTime = workingTime;
    }
}
