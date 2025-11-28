package com.example.exercise1_team.network_mhuyy.models_mhuyy;

public class ResetPasswordRequest_mhuyy {
    private String resetToken;
    private String newPassword;

    public ResetPasswordRequest_mhuyy(String resetToken, String newPassword) {
        this.resetToken = resetToken;
        this.newPassword = newPassword;
    }

    public String getResetToken() { return resetToken; }
    public String getNewPassword() { return newPassword; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
