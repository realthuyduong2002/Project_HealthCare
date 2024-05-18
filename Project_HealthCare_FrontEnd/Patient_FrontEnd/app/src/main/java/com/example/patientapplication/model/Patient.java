package com.example.patientapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patient {
    @SerializedName("_id")
    @Expose
    private int PatientID;
    @SerializedName("PatientName")
    @Expose
    private String PatientName;
    @SerializedName("Phone")
    @Expose
    private String Phone;
    @SerializedName("Email")
    @Expose
    private String Email;

    public int getPatientID() {
        return PatientID;
    }

    public void setPatientID(int patientID) {
        PatientID = patientID;
    }

    @SerializedName("Gender")
    @Expose
    private String Gender;
    @SerializedName("DateOfBirth")
    @Expose
    private String DateOfBirth;
    @SerializedName("CitizenIdentification")
    @Expose
    private String CitizenIdentification;
    @SerializedName("District")
    @Expose
    private String District;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("Ward")
    @Expose
    private String Ward;

    public Patient(String patientName, String phone, String email, String city, String district, String ward, String gender, String dateOfBirth, String citizenIdentification) {
        PatientName = patientName;
        Phone = phone;
        Email = email;
        City = city;
        District = district;
        Ward = ward;
        Gender = gender;
        DateOfBirth = dateOfBirth;
        CitizenIdentification = citizenIdentification;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getCitizenIdentification() {
        return CitizenIdentification;
    }

    public void setCitizenIdentification(String citizenIdentification) {
        CitizenIdentification = citizenIdentification;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getWard() {
        return Ward;
    }

    public void setWard(String ward) {
        Ward = ward;
    }
    public String toString() {
        return PatientName + "\n" + "ID: " + PatientID;
    }
}
