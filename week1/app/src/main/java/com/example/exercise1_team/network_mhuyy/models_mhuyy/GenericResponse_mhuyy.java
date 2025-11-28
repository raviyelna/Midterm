package com.example.exercise1_team.network_mhuyy.models_mhuyy;


import com.google.gson.annotations.SerializedName;
public class GenericResponse_mhuyy {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    // getters / setters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public void setSuccess(boolean success) { this.success = success; }
    public void setMessage(String message) { this.message = message; }
}
