package com.example.doctorapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Patient implements Parcelable {
    @SerializedName("_id")
    private int id;
    @SerializedName("PatientName")
    private String patientName;
    @SerializedName("Phone")
    private String phone;
    @SerializedName("Email")
    private String email;
    @SerializedName("City")
    private String city;
    @SerializedName("District")
    private String district;
    @SerializedName("Ward")
    private String ward;
    @SerializedName("Gender")
    private String gender;
    @SerializedName("DateOfBirth")
    private String dateOfBirth;
    @SerializedName("CitizenIdentification")
    private String citizenIdentification;

    public Patient() {}

    public Patient(int id, String patientName, String phone, String email, String city, String district, String ward, String gender, String dateOfBirth, String citizenIdentification) {
        this.id = id;
        this.patientName = patientName;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.citizenIdentification = citizenIdentification;
    }

    protected Patient(Parcel in) {
        id = in.readInt();
        patientName = in.readString();
        phone = in.readString();
        email = in.readString();
        city = in.readString();
        district = in.readString();
        ward = in.readString();
        gender = in.readString();
        dateOfBirth = in.readString();
        citizenIdentification = in.readString();
    }

    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(patientName);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(city);
        dest.writeString(district);
        dest.writeString(ward);
        dest.writeString(gender);
        dest.writeString(dateOfBirth);
        dest.writeString(citizenIdentification);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCitizenIdentification() {
        return citizenIdentification;
    }

    public void setCitizenIdentification(String citizenIdentification) {
        this.citizenIdentification = citizenIdentification;
    }
}