package com.example.exercise1_team;

public class LoginRequest {
    public String account;
    public String password;
    public LoginRequest(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
