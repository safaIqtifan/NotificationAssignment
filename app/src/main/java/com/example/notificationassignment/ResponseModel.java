package com.example.notificationassignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseModel {

    @SerializedName("status")
    @Expose
    public boolean status;
    @SerializedName("statusCode")
    @Expose
    public int statusCode;
    @SerializedName("msg")
    @Expose
    public String msg;
    @SerializedName("data")
    @Expose
    public UserModel data;

}
