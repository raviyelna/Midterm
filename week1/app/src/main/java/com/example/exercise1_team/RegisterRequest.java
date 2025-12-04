package com.example.exercise1_team;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {

    @SerializedName("account")
    private String account;

    @SerializedName("password")
    private String password;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("imageUrl")
    private String imageUrl;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RegisterRequest(String account, String password, String firstName, String lastName, String imageUrl) {
        this.account = account;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }


    // getters (nếu cần)
}
