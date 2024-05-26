package com.example.doctorapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorInfo {
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
    @SerializedName("DateOfBirth")
    @Expose
    private String DateOfBirth;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("Speciality")
    @Expose
    private String Speciality;
    @SerializedName("WorkingDate")
    @Expose
    private String WorkingDate;
    @SerializedName("WorkingTime")
    @Expose
    private String WorkingTime;


    public DoctorInfo(String doctorName, String gender, String email, String phone, String dateOfBirth, String city, String speciality, String workingDate, String workingTime) {
        DoctorName = doctorName;
        Gender = gender;
        Email = email;
        Phone = phone;
        DateOfBirth = dateOfBirth;
        City = city;
        Speciality = speciality;
        WorkingDate = workingDate;
        WorkingTime = workingTime;
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

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
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