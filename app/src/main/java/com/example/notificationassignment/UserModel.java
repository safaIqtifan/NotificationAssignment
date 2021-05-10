package com.example.notificationassignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("first_name")
    @Expose
    private String firstName;

    @SerializedName("second_name")
    @Expose
    private String secondName;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("Registration Code")
    @Expose
    private String registrationCode;

    @SerializedName("password")
    @Expose
    private String password;


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }
}
