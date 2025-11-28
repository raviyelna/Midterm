package com.example.exercise1_team;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import com.example.exercise1_team.LoginRequest;
import com.example.exercise1_team.LoginResponse;
import com.example.exercise1_team.DefaultResponse;
import com.example.exercise1_team.RegisterRequest;


public interface ApiService {

    @POST("api/auth/login")
    Call<LoginResponse> login(@Body com.example.exercise1_team.LoginRequest req);

    @POST("api/auth/register")
    Call<DefaultResponse> register(@Body RegisterRequest req);


}
