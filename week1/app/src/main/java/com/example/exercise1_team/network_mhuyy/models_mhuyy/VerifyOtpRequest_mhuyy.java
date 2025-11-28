package com.example.exercise1_team.network_mhuyy.models_mhuyy;

public class VerifyOtpRequest_mhuyy {
    private String email;
    private String otp;

    public VerifyOtpRequest_mhuyy(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    public String getEmail() { return email; }
    public String getOtp() { return otp; }
    public void setEmail(String email) { this.email = email; }
    public void setOtp(String otp) { this.otp = otp; }
}
