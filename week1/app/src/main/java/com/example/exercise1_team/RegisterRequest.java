package com.example.exercise1_team;

public class RegisterRequest {
    String username;
    String password;
    String email; // Thêm các trường cần thiết cho việc đăng ký

    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
