package com.example.doctorapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorInfo {
    @SerializedName("_id")
    @Expose
    private String id;

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

    // Constructor đầy đủ với tất cả các trường
    public DoctorInfo(String id, String doctorName, String gender, String email, String phone, String dateOfBirth, String city, String speciality, String workingDate, String workingTime) {
        this.id = id;
        this.DoctorName = doctorName;
        this.Gender = gender;
        this.Email = email;
        this.Phone = phone;
        this.DateOfBirth = dateOfBirth;
        this.City = city;
        this.Speciality = speciality;
        this.WorkingDate = workingDate;
        this.WorkingTime = workingTime;
    }

    // Getter và Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        this.DoctorName = doctorName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.DateOfBirth = dateOfBirth;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        this.Speciality = speciality;
    }

    public String getWorkingDate() {
        return WorkingDate;
    }

    public void setWorkingDate(String workingDate) {
        this.WorkingDate = workingDate;
    }

    public String getWorkingTime() {
        return WorkingTime;
    }

    public void setWorkingTime(String workingTime) {
        this.WorkingTime = workingTime;
    }
}