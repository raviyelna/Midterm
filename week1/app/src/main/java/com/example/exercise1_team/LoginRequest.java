package com.example.exercise1_team;

public class LoginRequest {
    public String account;
    public String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public LoginRequest(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
