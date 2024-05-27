package com.example.doctorapplication.model;

public class Account {
    private String id;
    private String PhoneNumber;
    private String Password;
    private boolean Role;
    private String doctorId;

    public Account(String PhoneNumber, String Password, boolean Role) {
        this.PhoneNumber = PhoneNumber;
        this.Password = Password;
        this.Role = Role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isRole() {
        return Role;
    }

    public void setRole(boolean role) {
        Role = role;
    }

    public String getId() {
        return id;
    }
}
